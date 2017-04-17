package jaredbgreat.procgenlab.generators.test;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
*/

import java.util.Random;

/**
 *
 * @author Jared Blackburn
 */
public class Caves {
    protected final int w, h;
    protected final Random random;
    
    
    public Caves(int width, int height, Random random) {
        w = width;
        h = height;
        this.random = random;
    }
    
    
    public int[] generate() {
        return refineNoise(makeNoise());
    }
    
    
    protected int[][] makeNoise() {
        int[][] noise = new int[w + 2][h + 2];
        for(int i = 0; i < (w + 2); i++)
            for(int j = 0; j < (h + 2); j++) {
                noise[i][j] = random.nextInt(2);
            }
        return noise;
    }
    
    
    protected int[] refineNoise(int[][] noise) {
        // Could be better optimized, but this is a test of the gui and api
        int[] out = new int[w * h];
        for(int i = 1; i < (w + 1); i++) 
            for(int j = 1; j < (h + 1); j++) {
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
