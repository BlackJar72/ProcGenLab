package jaredbgreat.procgenlab.generators.test;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
*/

import jaredbgreat.procgenlab.api.util.SpatialNoise;
import java.util.Random;
import static jaredbgreat.procgenlab.generators.Test.absModulus;

/**
 *
 * @author Jared Blackburn
 */
public class Caves2 extends Caves {

    public Caves2(int width, int height, SpatialNoise random) {
        super(width, height, random);
    }
    
    
    public int[] generate(int depth) {
        return refineNoise((partialGeneration(depth)));
    }
    
    
    private int[][] partialGeneration(int depth) {
        if(depth == 0) {
            return expandScale(partiallyRefineNoise(makeNoise()), depth);
        } else {
            return expandScale(partiallyRefineNoise(new 
                    Caves2((w / 2) + 2, (h / 2) + 2, random)
                    .partialGeneration(depth - 1)), depth);
        }
    }
    
    
    protected int[][] partiallyRefineNoise(int[][] noise) {
        // Could be better optimized, but this is a test of the gui and api
        int[][] out = new int[w][h];
        for(int i = 0; i < w; i++) 
            for(int j = 0; j < h; j++) {
                out[i][j] = partiallyRefineCell(noise, i + 1, j + 1);
            }
        return out;
    }
    
    
    protected int[][] expandScale(int[][] map, int depth) {
        // Making some assumptions about input since I know I won't violate them
        int[][] out = new int[map.length * 2][map[0].length * 2];
        for(int i = 0; i < map.length; i++)
            for(int j = 0; j < map[0].length; j++) {
                if((map[i][j] == 0) || (map[i][j] == 1)) {
                    out[2 * i][2 * j]               = map[i][j];
                    out[(2 * i) + 1][2 * j]         = map[i][j];
                    out[2 * i][(2 * j) + 1]         = map[i][j];
                    out[(2 * i) + 1][(2 * j) + 1]   = map[i][j];
                } else {                    
                    out[2 * i][2 * j]               
                            = absModulus(random.intFor(2 * i, 2 * j, depth), 2);
                    out[(2 * i) + 1][2 * j]         
                            = absModulus(random.intFor((2 *  i) + 1, 2 * j, depth), 2);
                    out[2 * i][(2 * j) + 1]         
                            = absModulus(random.intFor(2 * i, (2 * j) + 1, depth), 2);
                    out[(2 * i) + 1][(2 * j) + 1]   
                            = absModulus(random.intFor((2 * i) + 1, (2 * j) + 1, depth), 2);
                }
            }
        return out;
    }
    
    
    public int partiallyRefineCell(int[][] noise, int x, int y) {
        int sum = 0;
        // Yes, I include the cell itself -- its simpler and works for me
        for(int i = x - 1; i <= x + 1; i++) 
            for(int j = y - 1; j <= y + 1; j++) {
                sum += noise[i][j];
            }
        if(sum < 4) return 0;
        if(sum > 5) return 1;
        return -1; // place holder for new randomness
    }
    
    
}
