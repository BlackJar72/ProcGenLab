package jaredbgreat.procgenlab.generators.scalablenoise.chunk;

import java.util.Random;

/**
 * A gradient noise generator.  This is based on Perlin noise  but 
 * with a few modifications. First, this does not use linear interpolation,
 * but instead scales influence based on Euclidean distance (techincally the
 * square of the distance, because it looked better).  Second, it doesn't 
 * use unit vectors for gradients but instead allows for variable magnitudes 
 * (since when was real terrain limited to consistently have one base slope 
 * everywhere?).  It seems to produce good results and (surprisingly) runs 
 * slightly faster than true Perlin noise.
 * 
 * @author Jared Blackburn
 */
public class HeightNoise {
    int size, interval, currentInterval, regx, regy;
    double[][] field;
    double scale, divisor;
    SpatialNoise random;

    
    public HeightNoise(SpatialNoise random, int size, int interval, 
                double scale, int regx, int regy) {
        this.size = size; 
        this.interval = interval; 
        this.scale = scale;
        this.regx = regx;
        this.regy = regy;
        this.size = size;
        this.random = random;
    }
    
    public double[][] process(int startz)  {
        field = new double[size][size];
        currentInterval = interval;
        divisor = 1.0;
        while(currentInterval > 2) {
            processOne(startz);            
            divisor *= 2;
            currentInterval /= 2;
            startz += 2;
        }
        return field;
    }
    
    
    private void processOne(int startz) {
        int nodesX = size / currentInterval + 1;
        int nodesY = size / currentInterval + 1;
        Vec2D[][] nodes = new Vec2D[nodesX][nodesY];
        for(int i = 0; i < nodesX; i++)
            for(int j = 0; j < nodesY; j++) {
                nodes[i][j] = new Vec2D(random, (regx * nodesX - 1) + i, 
                        (regy * nodesY - 1) + j, startz);
        }
        for(int i = 0; i < size; i++)
            for(int j = 0; j < size; j++) {
                field[i][j] += processPoint(nodes, i, j) * scale;
        }
    }
    
    
    public double processPoint(Vec2D[][] nodes, int x, int y) {
        double out = 0.0;
        
        double ci = (double)currentInterval;        
        double dx = fullFade(x % currentInterval);
        double dy = fullFade(y % currentInterval);
        int    px = (int)(x / currentInterval);
        int    py = (int)(y / currentInterval);        
        
        out += calcLoc(nodes[px][py], 
                    new Vec2D(dx, dy), ci);
        out += calcLoc(nodes[px + 1][py], 
                    new Vec2D((ci - dx), dy), ci);
        out += calcLoc(nodes[px + 1][py + 1], 
                    new Vec2D((ci - dx), (ci - dy)), ci);
        out += calcLoc(nodes[px][py + 1], 
                    new Vec2D(dx, (ci - dy)), ci);        
        
        out /= interval;
        out /= 2.0;
        
        if((out >= 0.99) || (out <= -0.99)) {
            System.out.println(out);
            out = 0.0;
        }
        return out;
    }
    
    
    private double calcLoc(Vec2D from, Vec2D at, double ci) {
        double dx = at.x / ci;
        double dy = at.y / ci;
        double l = (1 - ((dx * dx) + (dy * dy)));
        if(l > 0) {
            return Vec2D.dot(from, at) * l;            
        }        
        return 0.0;
    }
    
    
    private double fade(double in) {
        return in * in * in * (in * (in * 6 - 15) + 10);  
    }
    
    
    private double fullFade(double in) {
        return fade(in / currentInterval) * currentInterval;
    }
    
    
}
