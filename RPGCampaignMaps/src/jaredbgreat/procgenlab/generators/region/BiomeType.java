/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaredbgreat.procgenlab.generators.region;

import java.util.Random;

/**
 *
 * @author jared
 */
public enum BiomeType {
    
    OCEAN (0xff1122ff),
    FROCEAN (0xff2244ff),
    RIVER (0xff1133ff),
    UNKOWN (0xff00ff44),
    SWAMP (0xff228844),
    MOUNTAIN (0xff888888),
    TUNDRA (0xffffffff),
    GRASS (0xff22ff55),
    SAVANNA (0xff88ff44),
    TAIGA (0xff22aa99),
    FOREST (0xff00aa22),
    JUNGLE (0xff22cc44),
    DESERT (0xffaa9900),
    SCRUB (0xff668844);   
    
    public final int color;
    
    BiomeType(int color) {
        this.color = color;
    }
    
    
    public static void makeBiomes(Map map, Random random) {
        int[] noise = refineNoise(map.makeNoise(random), map);
        for(int i = 0; i < map.map.length; i++) {
            findBiome(map.map[i], noise[i]);
        }
    }
    
    
    public static void findBiome(Tile tile, int noise) {
        if(tile.rlBiome == 0) {
            if((tile.temp + noise) == 0) {
                tile.rlBiome = FROCEAN.ordinal();
            }
            return;
        }
        if(tile.temp > 1 && ((tile.wet - tile.val) > noise - 1)) {
            tile.rlBiome = SWAMP.ordinal();
            return;
        }
        switch(tile.temp) {
            case 0:
                tile.rlBiome = TUNDRA.ordinal();
                return;
            case 1: 
                tile.rlBiome = findSubarctic(tile.wet);
                return;
            case 2:
                tile.rlBiome = findTemporate(tile.wet);
                return;
            case 3:
                tile.rlBiome = findSubtropical(tile.wet);
                return;
            case 4:
                tile.rlBiome = findTropical(tile.wet);
                return;
            default:
                return;
        }
    }
    
    
    public static int findSubarctic(int wet) {
        if(wet < 2) return GRASS.ordinal();
        else return TAIGA.ordinal();
    }
    
    
    public static int findTemporate(int wet) {
        if(wet < 1) return SCRUB.ordinal();
        else if(wet < 4) return GRASS.ordinal();
        else return FOREST.ordinal();
    }
    
    
    public static int findSubtropical(int wet) {
        if(wet < 3) return DESERT.ordinal();
        else if(wet < 4) return SCRUB.ordinal();
        else if(wet < 5) return GRASS.ordinal();
        else if(wet < 9) return FOREST.ordinal();
        else return JUNGLE.ordinal();
    }
    
    
    public static int findTropical(int wet) {
        if(wet < 1) return DESERT.ordinal();
        else if(wet < 4) return SAVANNA.ordinal();
        else if(wet < 5) return FOREST.ordinal();
        else return JUNGLE.ordinal();
    }
    
    
    private static int[] refineNoise(int[][] noise, Map map) {
        int[] out = new int[map.map.length];
        // Could be better optimized, but this is a test of the gui and api
        for(int i = 1; i < (map.w + 1); i++) 
            for(int j = 1; j < (map.h + 1); j++) {
                out[((j - 1) * map.w) + (i - 1)] = refineCell(noise, i, j);
            }
        return out;
    }
    
    
    private static int refineCell(int[][] noise, int x, int y) {
        int sum = 0;
        // Yes, I include the cell itself -- its simpler and works for me
        for(int i = x - 1; i <= x + 1; i++) 
            for(int j = y - 1; j <= y + 1; j++) {
                sum += noise[i][j];
            }
        if(sum < 5) {
            return 0;
        } else {
            return 1;
        }
    }
    
}
