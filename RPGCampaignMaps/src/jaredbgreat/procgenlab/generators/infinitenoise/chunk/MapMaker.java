/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaredbgreat.procgenlab.generators.infinitenoise.chunk;

import jaredbgreat.procgenlab.generators.infinitenoise.cache.Cache;
import jaredbgreat.procgenlab.generators.infinitenoise.cache.MutableCoords;
import jaredbgreat.procgenlab.generators.infinitenoise.chunk.SpatialNoise.RandomAt;
import static jaredbgreat.procgenlab.generators.infinitenoise.chunk.SpatialNoise.absModulus;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;



/**
 *
 * @author jared
 */
public class MapMaker { 
    final MutableCoords coords;
    // Using division to make the derivation obvious; this should be done
    // at compile time with no loss of performance.
    public static final int CSIZE = 16; // chuck size
    public static final int RSIZE = 4096 / CSIZE; // region / "continent" size
    public static final int RADIUS = RSIZE / 2; // radius for basin effect range
    public static final int SQRADIUS = RADIUS * RADIUS;
    public static final int GENSIZE = 7; // area of chunks to looks at
    public static final int GENHALF1 = GENSIZE  / 2;
    public static final int GENHALF0 = GENHALF1 - 1;
    public static final int GENHALF2 = GENHALF1 + 1;
    public static final int GENSQ = GENSIZE * GENSIZE; // area of chunks to looks at

    private final Cache<Region> regionCache = new Cache(32);
    
    private MutableCoords regionCoords = new MutableCoords(); 
    private MutableCoords chunkCoords = new MutableCoords(); 
    
    final ChunkTile[] premap;
    private BasinNode[] basins;
    private ClimateNode[] temp;
    private ClimateNode[] wet;
    private ClimateNode[] height;
    private TectonicNode[] plates;
    private BiomeBasin[][] subbiomes;
    
    public final SpatialNoise chunkNoise;
    public final SpatialNoise regionNoise;
    public final SpatialNoise biomeNoise;
    
    public final SizeScale sizeScale;
    public final int biomeSize;
    
    
    public MapMaker(int x, int z, long seed, SizeScale scale, int size) {
        Random random = new Random(seed);
        sizeScale = scale;
        biomeSize = size;
        chunkNoise  = new SpatialNoise(random.nextLong(), random.nextLong());
        regionNoise = new SpatialNoise(random.nextLong(), random.nextLong());
        biomeNoise  = new SpatialNoise(random.nextLong(), random.nextLong());
    
        premap = new ChunkTile[RSIZE * RSIZE];
        for(int i = 0; i < RSIZE; i++) 
            for(int j = 0; j < RSIZE; j++) {
                premap[(i * RSIZE) + j] = new ChunkTile(i, j);
            }
        coords = new MutableCoords().init(x, z);
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
    
    
    private Region[] findRegions(int x, int z) {
        Region[] out = new Region[9];
        int index = 0;
        for(int i = -1; i < 2; i++)
            for(int j = -1; j < 2; j++) {
            	regionCoords.init(x + i, z + j);
                //out[index] = regionPool.getEntry(regionCoords);
                out[index] = regionCache.get(regionCoords);
                if(out[index] == null) {
                	out[index] = new Region(x + i, z + j, 
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
    
    
    public void generate() {
        Region[] regions = findRegions(coords.getX(), coords.getZ());
        ArrayList<BasinNode> lBasins = new ArrayList<>();
        ArrayList<ClimateNode> lTemp = new ArrayList<>();
        ArrayList<ClimateNode> lWet = new ArrayList<>();
        for (Region region : regions) {
            lBasins.addAll(Arrays.asList(region.getBasins()));
            lTemp.addAll(Arrays.asList(region.getTemp()));
            lWet.addAll(Arrays.asList(region.getWet()));
        }
        BasinNode[] basinAr = lBasins.toArray(new BasinNode[lBasins.size()]);
        ClimateNode[] tempAr = lTemp.toArray(new ClimateNode[lTemp.size()]);
        ClimateNode[] wetAr = lWet.toArray(new ClimateNode[lWet.size()]);
        SpatialNoise random = chunkNoise;
        for(int i = 0; i < premap.length; i++) {
            premap[i].val = BasinNode.summateEffect(basinAr, premap[i], 1.0);
            edgeFix(premap[i]);
        }
        int[] noisy = refineNoise(random, 4);
        for(int i = 0; i < premap.length; i++) {
            premap[i].rlBiome = 1 - noisy[i];
        }
        double[] doubleNoise;
        doubleNoise = averageNoise(makeDoubleNoise(random, 1));
        for(int i = 0; i < premap.length; i++) {
            premap[i].temp = Math.min(ClimateNode.summateEffect(tempAr, premap[i], 
                    doubleNoise[i], sizeScale.inv), 24);
        }
        doubleNoise = averageNoise(makeDoubleNoise(random, 2));
        for(int i = 0; i < premap.length; i++) {
            premap[i].wet = Math.min(ClimateNode.summateEffect(wetAr, premap[i], 
                    doubleNoise[i], sizeScale.inv), 9);
        }
        for(int i = 0; i < premap.length; i++) {
            
        }
        makeBiomes(random.getRandomAt(0, 0, 3));
        BiomeType.makeBiomes(this, random, regions[4]);
    }
    
    
    public int[] getLandmass() {
        int[] out = new int[premap.length];
        for(int i = 0; i < out.length; i++) {
            out[i] = premap[i].rlBiome;
        }
        return out;
    }
    
    
    public int[] getLandiness() {
        int[] out = new int[premap.length];
        for(int i = 0; i < out.length; i++) {
            out[i] = Math.max(premap[i].val, 0);
        }
        return out;
    }

    public int[] getTemps() {
        int[] out = new int[premap.length];
        for(int i = 0; i < out.length; i++) {
            out[i] = Math.max(premap[i].temp, 0);
        }
        return out;
     }

    public int[] getRain() {
        int[] out = new int[premap.length];
        for(int i = 0; i < out.length; i++) {
            out[i] = Math.max(premap[i].wet, 0);
        }
        return out;
     }

    public int[] getBiomes() {
        int[] out = new int[premap.length];
        for(int i = 0; i < out.length; i++) {
            out[i] = premap[i].biome;
        }
        return out;
     }

    public int[] getBiomes2() {
        int[] out = new int[premap.length];
        for(int i = 0; i < out.length; i++) {
            out[i] = colorBlend(BiomeType.values()[premap[i].rlBiome].color, 
                    premap[i].biome);
        }
        return out;
     }
    
    
    public int[] getFaultlines() {
        int[] out = new int[premap.length];
        for(int i = 0; i < out.length; i++) {
            out[i] = (int) Math.min(Math.max((premap[i].faults * 10)  + 15 
                    - premap[i].val, 0), 10);
        }
        return out;
    }
    
    public ChunkTile getTile(int x, int y) {
        int index = (x * RSIZE) + y;
        if(index >= premap.length) {
            return null;
        } else {
            return premap[(x * RSIZE) + y];
        }
    }
    
    
    protected int[][] makeNoise(SpatialNoise random, int t) {
        int[][] noise = new int[RSIZE + 2][RSIZE + 2];
        for(int i = 0; i < (RSIZE + 2); i++)
            for(int j = 0; j < (RSIZE + 2); j++) {
                noise[i][j] = absModulus(random.intFor(i, j, t), 2);
            }
        return noise;
    }
    
    
    protected int[] refineNoise(SpatialNoise random, int times) {
        int[][] out = makeNoise(random, 1);
        for(int i = times - 2; i > 0; i--) {
            out = refineNoise2(out);
        }
        for(int i = 0; i < sizeScale.log; i++) {
            
        }
        out = refineNoise2(out);
        return refineNoise(out);
    }
    
    
    protected int[] refineNoise(int[][] noise) {
        int[] out = new int[premap.length];
        for(int i = 1; i < (RSIZE + 1); i++) 
            for(int j = 1; j < (RSIZE + 1); j++) {
                out[((j - 1) * RSIZE) + (i - 1)] = refineCell(noise, i, j);
            }
        return out;
    }
    
    
    protected int[][] refineNoise2(int[][] noise) {
        int[][] out = new int[noise.length][noise[0].length];
        for(int i = 1; i < (RSIZE + 1); i++) 
            for(int j = 1; j < (RSIZE + 1); j++) {
                out[i][j] = refineCell(noise, i, j);
            }
        return out;
    }
    
    
    protected int[][] refineNoise3(int[][] noise) {
        int[][] out = new int[noise.length][noise[0].length];
        for(int i = 1; i < (RSIZE + 1); i++) 
            for(int j = 1; j < (RSIZE + 1); j++) {
                out[i][j] = refineCell2(noise, i, j);
            }
        return out;
    }
    
    
    private int[][] upsize(int[][] noise, SpatialNoise random, int t) {
        int[][] out = new int[noise.length * 2][noise[0].length * 2];
        for(int i = 0; i < noise.length; i++)
            for(int j = 0; j < noise[0].length; j++) {
                if(noise[i][j] > -1) {
                    out[i * 2][j * 2]         = noise[i][j];
                    out[i * 2 + 1][j * 2]     = noise[i][j];
                    out[i * 2 + 1][j * 2 + 1] = noise[i][j];
                    out[i * 2][j * 2 + 1]     = noise[i][j];
                } else {
                    out[i * 2][j * 2]         = absModulus(random.intFor(i, j, t), 2);
                    out[i * 2 + 1][j * 2]     = absModulus(random.intFor(i, j, t), 2);
                    out[i * 2 + 1][j * 2 + 1] = absModulus(random.intFor(i, j, t), 2);
                    out[i * 2][j * 2 + 1]     = absModulus(random.intFor(i, j, t), 2);                    
                }
            }        
        for(int i = 1; i < (RSIZE + 1); i++) 
            for(int j = 1; j < (RSIZE + 1); j++) {
                out[i][j] = refineCell2(noise, i, j);
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
        if(sum < premap[((y - 1) * RSIZE) + (x - 1)].val) {
            return 0;
        } else {
            return 1;
        }
    }
    
    
    public int refineCell2(int[][] noise, int x, int y) {
        int sum = 0;
        // Yes, I include the cell itself -- its simpler and works for me
        for(int i = x - 1; i <= x + 1; i++) 
            for(int j = y - 1; j <= y + 1; j++) {
                sum += noise[i][j];
            }
        if(sum < premap[((y - 1) * RSIZE) + (x - 1)].val) {
            return 0;
        } else if(sum > premap[((y - 1) * RSIZE) + (x - 1)].val) {
            return 1;
        } else {
            return - 1;
        }
    }
    
    
    private double[][] makeDoubleNoise(SpatialNoise random, int t) {
        double[][] noise = new double[RSIZE + 4][RSIZE + 4];
        for(int i = 0; i < (RSIZE + 2); i++)
            for(int j = 0; j < (RSIZE + 2); j++) {
                noise[i][j] = (random.doubleFor(i, j, t) / 5) - 0.1;
            }
        return noise;
    }
    
    
    public double[] averageNoise(double[][] noise) {
        double[] out = new double[premap.length];
        // Could be better optimized, but this is a test of the gui and api
        for(int i = 2; i < (RSIZE + 2); i++) 
            for(int j = 2; j < (RSIZE + 2); j++) {
                out[((j - 2) * RSIZE) + (i - 2)] = averageNoise(noise, i, j);
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
    
    
    public void makeBiomes(RandomAt random) {
        int size = biomeSize;
        int across = RSIZE / biomeSize;
        int down = across;
        subbiomes = new BiomeBasin[across][down];
        for(int i = 0; i < across; i++) 
            for(int j = 0; j < down; j++) {
                subbiomes[i][j]
                        = new BiomeBasin((i * size) + random.nextInt(size),
                                    (j * size) + random.nextInt(size),
                                    random.nextInt() | 0xff000000,
                                    1.0 + random.nextDouble());
            }
        for (ChunkTile tile : premap) {
            tile.biome = BiomeBasin.summateEffect(subbiomes, tile, size);
            tile.biomeSeed = tile.biome & 0x7fffffff;
        }
    }
    
    
    private void edgeFix(ChunkTile t) {
        if(t.x < 10) {
            t.val += ((t.x - 10) / 2);
        } else if(t.x >= (RSIZE - 10)) {
            t.val -= ((t.x - RSIZE + 10) / 2);
        }
        if(t.z < 10) {
            t.val += ((t.z - 10) /  2);
        } else if(t.z >= (RSIZE - 10)) {
            t.val -= ((t.z - RSIZE + 10) / 2);
        }
    }
    
    
    private boolean notLand(ChunkTile t) {
        return t.rlBiome == 0;
    }
    
    
    void makeBeach(ChunkTile t, int noise) {
        //System.out.println();
        if(notLand(t) || (t.getX() < 1) || (t.getX() > 254)
                      || (t.getZ() < 1) || (t.getZ() > 254)) return;
        int oceans = 0;
        for(int i = -1; i < 2; i++) 
            for(int j = -1; j < 2; j++) {
                ChunkTile x = premap[((t.getX() + i) * RSIZE) + t.getZ() + j];
                //System.out.println(x.getX() + ", " + x.getZ() + ": " + x.rlBiome);
                if(notLand(x)) {
                    oceans++;
                }
            }
        //System.out.println(oceans);
        if(oceans < 3) return;
        //System.out.println(((t.getBiomeSeed() >> 16) & 1));
        t.beach = noise < (oceans - (2 * Math.max(oceans - 4, 0)) + 3 
                - ((t.getBiomeSeed() >> 16) & 1)
                + ((t.getBiomeSeed() >> 15) & 1));
        //System.out.println();
    }
    
    
    private int colorBlend(int c1, int c2) {
        int r = ((((0x00ff0000 & c1) >> 16) * 4) 
                + ((0x00ff0000 & c2) >> 16)) / 5; 
        int g = ((((0x0000ff00 & c1) >> 8) * 4) 
                + ((0x0000ff00 & c2) >> 8))  / 5; 
        int b = (((0x000000ff & c1) * 4) + (0x000000ff & c2)) / 5; 
        return 0xff000000 + (r << 16) + (g << 8) + b;
    }
    
    
}
