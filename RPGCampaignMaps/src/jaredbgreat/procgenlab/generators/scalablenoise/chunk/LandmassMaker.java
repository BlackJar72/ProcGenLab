package jaredbgreat.procgenlab.generators.scalablenoise.chunk;

/**
 *
 * @author jared
 */
public class LandmassMaker {
    SpatialNoise random;
    int regx, regy, width, mwidth;
    SizeScale scale;
    double currentScale;
    int[][] current, next, tmp;
    BasinNode[] basins;
    ChunkTile[][] tmpChunks;
    
    
    LandmassMaker(int rx, int ry, SpatialNoise rand, 
                BasinNode[] basinAr, SizeScale sc, int startW) {
        random = rand;
        regx = rx;
        regy = ry;
        scale = sc;
        width = startW;
        mwidth = width + 2;
        currentScale = 1.0;
        basins = basinAr;
        current = new int[mwidth][mwidth];
        next    = new int[mwidth][mwidth];
    }
    
    
    public int[][] generate() {
        makeTmpChunks();
        makeInitialNoise();
        caTransform();
        caTransform();
        caTransform();
        for(int i = 0; i < scale.log; i++) {
            enlargeNoise();
            caTransform();            
        }
        caTransform();
        return current;
    }
    
    
    private void makeTmpChunks() {
        tmpChunks = new ChunkTile[width][width];
        for(int i = 0; i < width; i++)
            for(int j = 0; j < width; j++) {
                tmpChunks[i][j] = new ChunkTile(i + (width * regx), 
                        j + (width * regy));
                tmpChunks[i][j].val = BasinNode
                        .summateEffect(basins, tmpChunks[i][j], currentScale);
                edgeFix(tmpChunks[i][j]);
            }        
    }
    
    
    void makeInitialNoise() {
        for(int i = 0; i < current.length; i++)
            for(int j = 0; j < current.length; j++) {
                current[i][j] = random
                        .intFor((regx * width) + i + 1, 
                                (regy * width) + j + 1, 0)  & 0x1;
            }
    }
    
    
    void enlargeNoise() {
        width *= 2;
        mwidth = width + 2;
        currentScale /= 2;
        next = new int[mwidth][mwidth];
        for(int i = 0; i < mwidth; i++) {
            for(int j = 0; j < mwidth; j++) {
                // This should work as long as all widths are even (as they are)
                next[i][j] = 1 - current[(i + 1) /  2][(j + 1) / 2];
                //System.out.print(next[i][j]);
            }
            //System.out.println();
        }
        current = next;
        current = new int[mwidth][mwidth];
        makeTmpChunks();
    }
    
    
    void caTransform() {
        for(int i = mwidth - 2; i > 0; i--) 
            for(int j = mwidth - 2; j > 0; j--) {
                next[i][j] = caCell(i, j);
            }
        tmp = current;
        current = next;
        next = tmp;       
    }
    
    
    int caCell(int x, int y) {
        int count = current[x-1][y-1] + current[x][y-1] + current[x+1][y-1] +
                    current[x-1][y]   + current[x][y]   + current[x+1][y] +
                    current[x-1][y+1] + current[x][y+1] + current[x+1][y+1];
        if(count < tmpChunks[x - 1][y - 1].val) return 0;
        return 1;
    }
    
    
    private void edgeFix(ChunkTile t) {
        if(t.x < 10) {
            t.val += ((t.x - 10) / 2);
        } else if(t.x >= (width - 10)) {
            t.val -= ((t.x - width + 10) / 2);
        }
        if(t.z < 10) {
            t.val += ((t.z - 10) /  2);
        } else if(t.z >= (width - 10)) {
            t.val -= ((t.z - width + 10) / 2);
        }
    }
    
    
    public ChunkTile[] makePremap() {
        int[][] made = generate();        
        ChunkTile[] out = new ChunkTile[width * width];        
        for(int i = 0; i < width; i++)
            for(int j = 0; j < width; j++) {
                out[(i * width) + j] = tmpChunks[i][j];
                out[(i * width) + j].rlBiome = 1 - made[i + 1][j + 1];
            }
        return out;
    }
    
    
    
    
    
    
    
    
    
    
}
