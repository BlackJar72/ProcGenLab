/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaredbgreat.procgenlab.generators.scalablenoise.chunk;

/**
 *
 * @author jared
 */
public enum BiomeType {
    
    OCEAN (0xff1122ff),
    DOCEAN (0xff1111dd),
    FROCEAN (0xff2244ff),
    RIVER (0xff1133ff),
    UNKOWN (0xff00ff44),
    SWAMP (0xff228844),
    MOUNTAIN (0xff888888),
    TUNDRA (0xffffffff),
    CGRASS (0xff22d477),
    GRASS (0xff22d455),
    SGRASS (0xff44d455),
    SAVANNA (0xff88ff44),
    TAIGA (0xff22aa99),
    FOREST (0xff00aa22),
    PARK (0xff10c040),
    SFOREST (0xff00aa22),
    TFOREST (0xff00aa22),
    JUNGLE (0xff22ff44),
    DESERT (0xffaa9900),
    SCRUB (0xff668844),
    ALPINE (0xff776688), 
    ALPINE2 (0xffaaaacc),  
    HILLY (0xff556666),  
    BEACH (0xffaaff88);
    
    public final int color;
    
    BiomeType(int color) {
        this.color = color;
    }
    
    private static final BiomeType[] table = {
    	//Arctic
    	TUNDRA, TUNDRA,  TUNDRA,  TUNDRA,  TUNDRA,  TUNDRA, TUNDRA, TUNDRA, TUNDRA, TUNDRA,
    	TUNDRA, TUNDRA,  TUNDRA,  TUNDRA,  TUNDRA,  TUNDRA, TUNDRA, TUNDRA, TUNDRA, TUNDRA,
    	TUNDRA, TUNDRA,  TUNDRA,  TUNDRA,  TUNDRA,  TUNDRA, TUNDRA, TUNDRA, TUNDRA, TUNDRA,
    	TUNDRA, TUNDRA,  TUNDRA,  TUNDRA,  TUNDRA,  TUNDRA, TUNDRA, TUNDRA, TUNDRA, TUNDRA,
    	TUNDRA, TUNDRA,  TUNDRA,  TUNDRA,  TUNDRA,  TUNDRA, TUNDRA, TUNDRA, TUNDRA, TUNDRA,
    	//Sub-Arctic
    	CGRASS,  CGRASS,   TAIGA,   TAIGA,   TAIGA,   TAIGA,  TAIGA,  TAIGA,  TAIGA,  TAIGA,
    	CGRASS,  CGRASS,   TAIGA,   TAIGA,   TAIGA,   TAIGA,  TAIGA,  TAIGA,  TAIGA,  TAIGA,
    	CGRASS,  CGRASS,   TAIGA,   TAIGA,   TAIGA,   TAIGA,  TAIGA,  TAIGA,  TAIGA,  TAIGA,
    	CGRASS,  CGRASS,   TAIGA,   TAIGA,   TAIGA,   TAIGA,  TAIGA,  TAIGA,  TAIGA,  TAIGA,
    	CGRASS,  CGRASS,   TAIGA,   TAIGA,   TAIGA,   TAIGA,  TAIGA,  TAIGA,  TAIGA,  TAIGA,
    	//Temperate
    	GRASS,  GRASS,   GRASS,   GRASS,   PARK,  FOREST, FOREST, FOREST, FOREST, FOREST,
    	SCRUB,  GRASS,   GRASS,   GRASS,   PARK,  FOREST, FOREST, FOREST, FOREST, FOREST,
    	SCRUB,  GRASS,   GRASS,   GRASS,   PARK,  FOREST, FOREST, FOREST, FOREST, FOREST,
    	DESERT, SCRUB,   GRASS,   GRASS,   PARK,  FOREST, FOREST, FOREST, FOREST, FOREST,
    	DESERT, DESERT,  SCRUB,   GRASS,   PARK,  FOREST, FOREST, FOREST, FOREST, FOREST,
    	//Sub-Tropical
    	DESERT, DESERT,  DESERT,  SCRUB,   SGRASS,   SFOREST, SFOREST, SFOREST, SFOREST, SFOREST,
    	DESERT, DESERT,  DESERT,  SCRUB,   SGRASS,   SFOREST, SFOREST, SFOREST, SFOREST, JUNGLE,
    	DESERT, DESERT,  DESERT,  SCRUB,   SGRASS,   SFOREST, SFOREST, SFOREST, SFOREST, JUNGLE,
    	DESERT, DESERT,  DESERT,  SCRUB,   SGRASS,   SFOREST, SFOREST, SFOREST, JUNGLE,  JUNGLE,
    	DESERT, DESERT,  DESERT,  SCRUB,   SGRASS,   SFOREST, SFOREST, JUNGLE,  JUNGLE,  JUNGLE,
    	//Tropical
    	DESERT, DESERT,  SAVANNA, SAVANNA, TFOREST, TFOREST, JUNGLE, JUNGLE, JUNGLE, JUNGLE,
    	DESERT, SAVANNA, SAVANNA, SAVANNA, TFOREST, JUNGLE,  JUNGLE, JUNGLE, JUNGLE, JUNGLE,
    	DESERT, SAVANNA, SAVANNA, SAVANNA, TFOREST, JUNGLE,  JUNGLE, JUNGLE, JUNGLE, JUNGLE,
    	DESERT, SAVANNA, SAVANNA, SAVANNA, TFOREST, JUNGLE,  JUNGLE, JUNGLE, JUNGLE, JUNGLE,
    	DESERT, SAVANNA, SAVANNA, SAVANNA, TFOREST, JUNGLE,  JUNGLE, JUNGLE, JUNGLE, JUNGLE
    };
    
    
    public static void makeBiomes(MapMaker map, SpatialNoise random, 
            Region region, SizeScale sc) {
        int[] ice   = refineNoise10(map.makeNoise(random, 5), map);
        int[] cn    = refineNoise10(map.makeNoise(random, 6), map);
        for(int i = 0; i < map.premap.length; i++) {
            map.makeBeach(map.premap[i], cn[i]);            
            findBiome(map.premap[i], ice[i], cn[i]);
        }
        RiverMaker rm = new RiverMaker(map, random.longFor(0, 0, 16), 
                region, sc);
        rm.build();
    }
    
    
    public static void findBiome(ChunkTile tile, int ice, int cn) {
        if(tile.river != 0) {
            tile.rlBiome = RIVER.ordinal();
            System.out.println("Found a River!!!");
            return;
        }
        if(tile.rlBiome == 0) {
            if(((ice / 2) - tile.temp) > -2) {
                tile.rlBiome = FROCEAN.ordinal();
            } else if(tile.height < 0.2) {
                tile.rlBiome = DOCEAN.ordinal();
            }
            return;
        }
        if(tile.temp > 7 && ((tile.wet - tile.val - tile.height) > 0)) {
            if((tile.getBiomeSeed() & 0x1) == 1) {
                tile.rlBiome = SWAMP.ordinal();
                tile.nextBiomeSeed();
                return;
            }
            tile.nextBiomeSeed();
        }
        if(tile.isIsBeach()) {
            tile.rlBiome = BEACH.ordinal();
            return;
        }
        int mval = (int)(tile.faults * 10)  + 15 - tile.val - tile.noiseVal;
        /*if(mval < 10) {
            if(mval < 10) {
                tile.mountain = true;
                if(mval < 7) {                    
                    tile.rlBiome = ALPINE2.ordinal();
                } else {
                    tile.rlBiome = ALPINE.ordinal();
                }
                return;
            } else {
                tile.rlBiome = HILLY.ordinal();
                tile.hilly = true;
                return;
            }
        }*/
        findLandBiome(tile);
    }
    
    
    public static void findLandBiome(ChunkTile chunk) {
    	chunk.rlBiome = table[(chunk.temp * 10) + chunk.wet].ordinal();
    }
    
    
    private static int[] refineNoise(int[][] noise, MapMaker map) {
        int[] out = new int[map.premap.length];
        // Could be better optimized, but this is a test of the gui and api
        for(int i = 1; i < (map.RSIZE * map.sizeScale.whole + 1); i++) 
            for(int j = 1; j < (map.RSIZE * map.sizeScale.whole + 1); j++) {
                out[((j - 1) * map.RSIZE * map.sizeScale.whole) + (i - 1)] = refineCell(noise, i, j);
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
        return sum / 5;
    }
    
    
    private static int[] refineNoise10(int[][] noise, MapMaker map) {
        int[] out = new int[map.premap.length];
        // Could be better optimized, but this is a test of the gui and api
        for(int i = 1; i < (map.RSIZE * map.sizeScale.whole + 1); i++) 
            for(int j = 1; j < (map.RSIZE * map.sizeScale.whole + 1); j++) {
                out[((j - 1) * map.RSIZE * map.sizeScale.whole) + (i - 1)] = refineCell10(noise, i, j);
            }
        return out;
    }
    
    
    private static int refineCell10(int[][] noise, int x, int y) {
        int sum = 0;
        // Yes, I include the cell itself -- its simpler and works for me
        for(int i = x - 1; i <= x + 1; i++) 
            for(int j = y - 1; j <= y + 1; j++) {
                sum += noise[i][j];
            }
        return sum;
    }
    
}
