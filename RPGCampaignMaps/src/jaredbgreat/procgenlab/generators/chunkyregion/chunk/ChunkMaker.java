/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaredbgreat.procgenlab.generators.chunkyregion.chunk;

import jaredbgreat.procgenlab.generators.chunkyregion.cache.Cache;
import jaredbgreat.procgenlab.generators.chunkyregion.cache.CachedPool;
import jaredbgreat.procgenlab.generators.chunkyregion.cache.CachedPool.ObjectFactory;
import jaredbgreat.procgenlab.generators.chunkyregion.cache.MutableCoords;
import static jaredbgreat.procgenlab.generators.chunkyregion.chunk.SpatialNoise.absModulus;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author jared
 */
public class ChunkMaker {
    // Using division to make the derivation obvious; this should be done
    // at compile time with no loss of performance.
    public static final int CSIZE = 16; // chuck size
    public static final int RSIZE = 4096 / CSIZE; // region / "continent" size
    public static final int RADIUS = RSIZE / 2; // radius for basin effect range
    public static final int SQRADIUS = RADIUS * RADIUS;
    public static final int BSIZE = 256 / CSIZE; // base size for (sub)biomes
    public static final int GENSIZE = 7; // area of chunks to looks at
    public static final int GENHALF1 = GENSIZE  / 2;
    public static final int GENHALF0 = GENHALF1 - 1;
    public static final int GENHALF2 = GENHALF1 + 1;
    public static final int GENSQ = GENSIZE * GENSIZE; // area of chunks to looks at
    
    public final SpatialNoise chunkNoise;
    public final SpatialNoise regionNoise;
    public final SpatialNoise biomeNoise;
        
//    private volatile ObjectFactory<Region> regionFact = new ObjectFactory() {
//		@Override
//		public Region create() {
//			return new Region();
//		}
//    };
//    private CachedPool<Region> regionPool = new CachedPool<>(regionFact, 144);
    
    private Cache<Region> regionCache = new Cache(144);
    
    private MutableCoords regionCoords = new MutableCoords(); 
    private MutableCoords chunkCoords = new MutableCoords(); 
    
    
    public ChunkMaker(long seed) {
        Random random = new Random(seed);
        Random fuck = new Random(System.nanoTime());
        chunkNoise = new SpatialNoise(random.nextLong(), random.nextLong());
        regionNoise = new SpatialNoise(random.nextLong(), random.nextLong());
        biomeNoise = new SpatialNoise(random.nextLong(), random.nextLong());
    }
    
    /**
     * This will take a set of chunk coordinates and return the region 
     * coordinates as an a length two int array containing the region 
     * coordinates.  Note that this is not the region of a region file 
     * but a region representing a continent of sorts for generation -- this
     * is a much bigger area.
     * 
     * @param x
     * @param z
     * @return 
     */
    private int[] findRegion(int x, int z) {
        int[] out = new int[2];
        out[0] = x / RSIZE;
        out[1] = z / RSIZE;
        return out;
    }
    
    
    /**
     * Returns the distance between the point represented by the two 
     * set of coordinates.
     * 
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return 
     */
    private int getSqIntDistance(int x1, int y1, int x2, int y2) {
        return ((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2));
    }
    
    
    /**
     * Returns true if the two points are within a distance less than half 
     * the distance across a continental region, false if they are farther
     * apart.  This is for determining if something (usually an attraction / 
     * influence basin) is within range of a chunk.
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return 
     */
    private boolean inRange(int x1, int y1, int x2, int y2) {
        return (getSqIntDistance(x1, y1, x2, y2) < SQRADIUS);
    }
    
    
    private Region[] findRegions(int x, int z) {
        Region[] out = new Region[9];
        int[] coords = findRegion(x, z);
        int index = 0;
        for(int i = -1; i < 2; i++)
            for(int j = -1; j < 2; j++) {
            	regionCoords.init(coords[0] + i, coords[1] + j);
                //out[index] = regionPool.getEntry(regionCoords);
                out[index] = regionCache.get(regionCoords);
                if(out[index] == null) {
                	out[index] = new Region(coords[0] + i, coords[1] + j, 
                                regionNoise);
                        regionCache.add(out[index]);
                } else {
                        out[index].use();
                	//out[index].init(coords[0] + i, coords[1] + j, regionNoise);
                	//regionPool.add(out[index], regionCoords);
                }
                index++;
            }
        return out;
    }
    
    
    public ChunkTile[] makeChunk(int x, int z) {
        Region[] regions = findRegions(x, z);
        ArrayList<BasinNode> basins = new ArrayList<>();
        ArrayList<ClimateNode> temp = new ArrayList<>();
        ArrayList<ClimateNode> wet = new ArrayList<>();
        for (Region region : regions) {
            basins.addAll(Arrays.asList(region.basins));
            temp.addAll(Arrays.asList(region.temp));
            wet.addAll(Arrays.asList(region.wet));
        }
        BasinNode[] basinAr = basins.toArray(new BasinNode[basins.size()]);
        ClimateNode[] tempAr = temp.toArray(new ClimateNode[temp.size()]);
        ClimateNode[] wetAr = wet.toArray(new ClimateNode[wet.size()]);
        ChunkTile[] map = new ChunkTile[GENSQ];
        for(int i = 0; i < GENSIZE; i++)
            for(int j = 0; j < GENSIZE; j++) {                
                map[(j * GENSIZE) + i] = new ChunkTile(x + i - 4, z + j -4);
            }
        double[] tempNoise = averageNoise(makeDoubleNoise(x, z, 0));
        double[] wetNoise = averageNoise(makeDoubleNoise(x, z, 1));
        for(int i = 0; i < map.length; i++) {
            map[i].val = BasinNode.summateEffect(basinAr, map[i]);            
            map[i].temp = Math.min(ClimateNode.summateTemp(tempAr, map[i], 
                    tempNoise[i]), 24);
            map[i].wet = Math.min(ClimateNode.summateWet(wetAr, map[i], 
                    wetNoise[i]), 9);
        }        
        int[] landNoise = refineNoise(makeNoise(x, z, 2), map, 4);
        for(int i = 0; i < map.length; i++) {
            map[i].rlBiome = 1 - landNoise[i];
        }        
        //RiverBorderBasin[] rivers = MakeRiverMap(regions[4]);
        //MakeRivers(rivers, regions[4], map);
        BiomeType.makeBiomes(map, this, chunkNoise);        
        makeBiomes(x, z, map);
        return map;
    }
    
    
    protected int[][] makeNoise(int x, int z, int t) {
        int[][] noise = new int[GENSIZE + 2][GENSIZE + 2];
        for(int i = 1; i < (GENSIZE + 1); i++)
            for(int j = 0; j < (GENSIZE + 2); j++) {
                noise[i][j] = absModulus(chunkNoise.intFor(x + i - 4, z + j - 4, 
                        t), 2);
            }
        return noise;
    }
    
    
    protected int[] refineNoise(int[][] noise, ChunkTile[] map, int times) {
        int[][] out = noise;
        for(int i = times; i > 0; i--) {
            out = refineNoise2(out, map);
        }
        return refineNoise(out, map);
    }
    
    
    protected int[] refineNoise(int[][] noise, ChunkTile[] map) {
        int[] out = new int[GENSQ];
        // Could be better optimized, but this is a test of the gui and api
        for(int i = 1; i < (GENSIZE + 1); i++) 
            for(int j = 1; j < (GENSIZE + 1); j++) {
                out[((j - 1) * GENSIZE) + (i - 1)] = refineCell(noise, map, i, j);
            }
        return out;
    }
    
    
    protected int[][] refineNoise2(int[][] noise, ChunkTile[] map) {
        int[][] out = new int[noise.length][noise[0].length];
        // Could be better optimized, but this is a test of the gui and api
        for(int i = 1; i < (GENSIZE + 1); i++) 
            for(int j = 1; j < (GENSIZE + 1); j++) {
                out[i][j] = refineCell(noise, map, i, j);
            }
        return out;
    }
    
    
    public int refineCell(int[][] noise, ChunkTile[] map, int x, int y) {
        int sum = 0;
        // Yes, I include the cell itself -- its simpler and works for me
        for(int i = x - 1; i <= x + 1; i++) 
            for(int j = y - 1; j <= y + 1; j++) {
                sum += noise[i][j];
            }
        if(sum < map[((y -1) * GENSIZE) + (x - 1)].val) {
            return 0;
        } else {
            return 1;
        }
    }
    
    
    private double[][] makeDoubleNoise(int x, int z, int t) {
        double[][] noise = new double[GENSIZE + 4][GENSIZE + 4];
        for(int i = 0; i < (GENSIZE + 2); i++)
            for(int j = 0; j < (GENSIZE + 2); j++) {
                noise[i][j] = (chunkNoise.doubleFor(x + i - 3, z + j - 3, t) / 5) - 0.1;
            }
        return noise;
    }
    
    
    public double[] averageNoise(double[][] noise) {
        double[] out = new double[GENSQ];
        // Could be better optimized, but this is a test of the gui and api
        for(int i = 2; i < (GENSIZE + 2); i++) 
            for(int j = 2; j < (GENSIZE + 2); j++) {
                out[((j - 2) * GENSIZE) + (i - 2)] = averageNoise(noise, i, j);
            }
        return out;
    }
    
    
    public double averageNoise(double[][] noise, int x, int y) {
        double sum = 0;
        // Yes, I include the cell itself -- its simpler and works for me
        for(int i = x - 2; i <= x + 2; i++) 
            for(int j = y - 2; j <= y + 2; j++) {
                sum += noise[i][j];
            }
        return sum / 9;
    }
    
    
    public void makeBiomes(int x, int z, ChunkTile[] map) {
        BiomeBasin[][] subBiomes = new BiomeBasin[5][5];
        int bx = x / BSIZE;
        int bz = z / BSIZE;
        int xcoord, zcoord;
        for(int i = 0; i < subBiomes.length; i++) 
            for(int j = 0; j < subBiomes[i].length; j++) {
                xcoord = bx + i - 2;
                zcoord = bz + j - 2;
                subBiomes[i][j]                        
                    = new BiomeBasin((xcoord * BSIZE) 
                            + SpatialNoise.absModulus(biomeNoise.intFor(xcoord, 
                                    zcoord, 0), BSIZE),
                        (zcoord * BSIZE) 
                            + SpatialNoise.absModulus(biomeNoise.intFor(xcoord, 
                                    zcoord, 1), BSIZE),
                        biomeNoise.intFor(xcoord, zcoord, 2),
                        1.0 + biomeNoise.doubleFor(xcoord, zcoord, 3));
                if(subBiomes[i][j].value == 0) {
                    System.out.println(
                          xcoord + ", "
                        + zcoord + ", "
                        + (subBiomes[i][j].x - (xcoord * BSIZE)) + ", " 
                        + (subBiomes[i][j].z - (zcoord * BSIZE)) + ", "
                        + subBiomes[i][j].value + ", "
                        + subBiomes[i][j].strength);
                }
            }
        for (ChunkTile tile : map) {
            tile.biome = BiomeBasin.summateEffect(subBiomes, tile, BSIZE);
        }
    }
    
    
    public void cleanCaches() {
        regionCache.cleanup();
    }
    
    
}
