package jaredbgreat.procgenlab.generators.infinitenoise.chunk;

/**
 *
 * @author jared
 */
public class LandmassMaker {
    SpatialNoise random;
    int regx, regy, width, mwidth, rt;
    SizeScale scale;
    int[][] current;
    BasinNode[] basins;
    
    
    LandmassMaker(int rx, int ry, SpatialNoise rand, 
                BasinNode[] basins, SizeScale sc, int startW) {
        random = rand;
        regx = rx;
        regy = ry;
        scale = sc;
        width = startW;
        mwidth = width + 2;
        rt = 0;
        current = new int[mwidth][mwidth];
    }
    
    
    public int[][] generate() {
        makeInitialNoise();
        // TODO: Generate
        return current;
    }
    
    
    void makeInitialNoise() {
        for(int i = 0; i < current.length; i++)
            for(int j = 0; j < current.length; j++) {
                current[i][j] = random
                        .intFor((regx * width) + i + 1, 
                                (regy * width) + j + 1, 0);
            }
    }
    
    
    void enlageNoise() {
        width *= 2;
        mwidth = width + 2;
        int[][] next = new int[mwidth][mwidth];
        for(int i = 0; i < mwidth; i++)
            for(int j = 0; j < mwidth; j++) {
                // This should work as long as all widths are even (as they are)
                next[i][j] = current[(i + 1) /  2][(j + 1) / 2];
            }
        current = next;
    }
    
    
    void caTransform() {
        
    }
    
    
    int caCell(int x, int y) {
        int count = current[x-1][y-1] + current[x][y-1] + current[x+1][y-1] +
                    current[x-1][y]   + current[x][y]   + current[x+1][y] +
                    current[x-1][y+1] + current[x][y+1] + current[x+1][y+1];
        
        return count; // FIXME: Absolutely not, this is a stand-in!
    }
    
    
    
    
    
    
    
    
    
    
}
