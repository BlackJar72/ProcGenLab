package jaredbgreat.procgenlab.generators.scalablenoise.chunk;

import java.util.Random;

/**
 * A very simple, scaled down 2D vector class, used by HeightNoise to 
 * represent gradients.
 * 
 * @author Jared Blackburn
 */
public class Vec2D {
    //private static final double SQR2   = Math.sqrt(2.0);
    //private static final double ISQR2  = 1 / SQR2;
    //private static final double F      = ISQR2 * 2;
    private static final double P2     = Math.PI * 2.0;
    double x, y;
    
    public Vec2D(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public Vec2D(Random rand, double a, double b) {
        x = (rand.nextDouble() * a) + b;
        y = (rand.nextDouble() * a) + b;
    }
    
    public Vec2D(Random rand, double s) {
        double r = rand.nextDouble() * P2;
        x = s * Math.sin(r);
        y = s * Math.cos(r);
    }
    
    public Vec2D(Random rand) {
        x = rand.nextDouble() * 2 - 1;
        y = rand.nextDouble() * 2 - 1;
    }
    
    public static double dot(Vec2D a, Vec2D b) {
        return (a.x * b.x) + (a.y * b.y);
    }
    
}
