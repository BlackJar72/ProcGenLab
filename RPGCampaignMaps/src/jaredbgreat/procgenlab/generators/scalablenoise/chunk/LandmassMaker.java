package jaredbgreat.procgenlab.generators.scalablenoise.chunk;

/**
 *
 * @author jared
 */
public class LandmassMaker {
    SpatialNoise random;
    int regx, regy, size;
    SizeScale scale;
    double currentScale;
    double[][] current;
    BasinNode[] basins;
    ChunkTile[][] tmpChunks;
    
    
    LandmassMaker(int rx, int ry, SpatialNoise rand, 
                BasinNode[] basinAr, SizeScale sc, int startW) {
        random = rand;
        size = startW * sc.whole;
        regx = rx;
        regy = ry;
        scale = sc;
        currentScale = 1.0;
        basins = basinAr;
        current = new double[size][size];
    }
    
    
    public ChunkTile[] generate() {
        ChunkTile[] out = new ChunkTile[size * size];
        for(int i = 0; i < size; i++) 
            for(int j = 0; j < size; j++) {
                out[(i * size) + j] 
                        = new ChunkTile(i /*+ (size * regx)*/, j /*+ (size * regy)*/);
        }        
        HeightNoise heightmaker 
                = new HeightNoise(random, size, 16 * scale.whole, 1.0, regx, regy);
        double[][] heights = heightmaker.process(0);
        for(int i = 0; i < size; i++)
            for(int j = 0; j < size; j++) {
                current[i][j] 
                        = edgeFix(out[(i * size) + j], 
                                BasinNode.summateEffect(basins, out[(i * size) + j], 
                                scale.inv));
                out[(i * size) + j].val = (int)current[i][j];
                current[i][j] /= 10.0;
                current[i][j] = ((current[i][j] + (heights[i][j] / 2.0) + 0.5) * current[i][j]) 
                        + heights[i][j];
            }
        
        for(int i = 0; i < size; i++)
            for(int j = 0; j < size; j++) {
                if(current[i][j] > 0.6) {
                    out[(i * size) + j].rlBiome = 1;
                } else {
                    out[(i * size) + j].rlBiome = 0;                    
                }
            }
        
        return out;
    }
    
    
    private double edgeFix(ChunkTile t, double val) {
        if(t.x < (10 * scale.whole)) {
            val += ((t.x - (10 * scale.whole)) / (2 * scale.whole));
        } else if(t.x >= (size - (10 * scale.whole))) {
            val -= ((t.x - size + (10 * scale.whole)) / (2 * scale.whole));
        }
        if(t.z < (10 * scale.whole)) {
            val += ((t.z - (10 * scale.whole)) /  (2 * scale.whole));
        } else if(t.z >= (size - (10 * scale.whole))) {
            val -= ((t.z - size + (10 * scale.whole)) / (2 * scale.whole));
        }
        return val;
    }
    
    
    
    
    
    
    
    
    
    
}
