package jaredbgreat.procgenlab.generators.test;

import java.util.Random;

/**
 *
 * @author jared
 */
public class Caves {
    private int w, h;
    private Random random;
    
    
    public Caves(int width, int height, Random random) {
        w = width;
        h = height;
        this.random = random;
    }
    
    
    public int[] Generate() {
        int[][] noise = new int[w + 2][h + 2];
        for(int i = 0; i < w; i++)
            for(int j = 0; j < h; j++) {
                noise[i][j] = random.nextInt(2);
            }
        int[] out = new int[w * h];
        // Could be better optimized, but this is a test of the gui and api
        for(int i = 1; i < (w - 1); i++) 
            for(int j = 1; j < (h - 1); j++) {
                out[((j - 1) * w) + (i - 1)] = refineCell(noise, i, j);
            }
        return out;
    }
    
    
    public int refineCell(int[][] noise, int x, int y) {
        int sum = 0;
        // Yes, I include the cell itself -- its simpler and works for me
        for(int i = x - 1; i <= x + 1; i++) 
            for(int j = y - 1; j <= y + 1; j++) {
                sum += noise[i][j];
            }
        return sum / 5; // 0 if sum < 5; 1 if sum >= 5 and sum < 10; sum is 0 to 9
    }
    
}
