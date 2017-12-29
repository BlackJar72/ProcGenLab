/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaredbgreat.procgenlab.generators;

import static jaredbgreat.procgenlab.api.Delims.SFS;
import static jaredbgreat.procgenlab.api.Delims.SGS;
import static jaredbgreat.procgenlab.api.Delims.SRS;
import static jaredbgreat.procgenlab.api.Delims.SUS;
import jaredbgreat.procgenlab.api.IGenerator;
import jaredbgreat.procgenlab.api.IPalette;
import jaredbgreat.procgenlab.api.palettes.ContinuousPalette;
import jaredbgreat.procgenlab.api.palettes.DiscretePalette;
import jaredbgreat.procgenlab.api.palettes.LiteralPalette;
import jaredbgreat.procgenlab.generators.chunkyregion.chunk.*;
import jaredbgreat.procgenlab.generators.chunkyregion.chunk.Region;
import java.util.StringTokenizer;

/**
 *
 * @author jared
 */
public class ZChunkyRegion implements IGenerator {
    ChunkMaker maker;
    ChunkTile[] map;
    int size;    
    
    int numZeros = 0, numNums = 0;
    private static volatile long timestamp;
    private static final Runtime runtime = Runtime.getRuntime();
    
    
    @Override
    public void generate(Long seed) {
        numZeros = 0;
        numNums = 0;
        maker = new ChunkMaker(seed);
        size = ChunkMaker.RSIZE * ChunkMaker.RSIZE;
        int startx = -ChunkMaker.RADIUS;
        int startz = -ChunkMaker.RADIUS;
        int endx = startx + ChunkMaker.RSIZE;
        int endz = startz + ChunkMaker.RSIZE;
        int arEnd = 0;
        map = new ChunkTile[size];
        timestamp = System.currentTimeMillis();
        for(int i = startx; i < endx; i ++) 
            for(int j = startz; j < endz; j++) {
                //profileOut();
                map[arEnd++] = maker.makeChunk(i, j)[25];
                numNums++;
                if(map[arEnd - 1].getBiome() == 0) {
                    numZeros++;
                }
            }
    }
    
    
    private void profileOut() {
        long t = System.currentTimeMillis() - timestamp;
        if((t > 250) || (t < 0)) {            
            timestamp = t;            
            maker.cleanCaches();
            // Total Memory Usage:
            System.out.println("Total memory usage: ");
            System.out.println("          USED MEMORY: " 
                    + ((runtime.totalMemory() - runtime.freeMemory()) / 1048576) 
                    + " MB");
            // Object in Memory  
            System.out.println("     " + BasinNode.num + " Basin nodes"); 
            System.out.println("     " + BiomeBasin.num + " BiomeBasins");
            System.out.println("     " + ChunkTile.num + " ChunkTiles");
            System.out.println("     " + Region.num + " Regions");
            System.out.println("     " + SpatialNoise.num + " Spatial noise generators");
            System.out.println();
        }
    }
    
    
    private void printMemory() {
            // Total Memory Usage:
            System.out.println("Total memory usage: ");
            System.out.println("          Free Memory: " 
                    + (runtime.freeMemory() / 1048576) + " MB");
            System.out.println("     Allocated Memory: " 
                    + (runtime.maxMemory() / 1048576) + " MB");
            System.out.println("       Maximum Memory: " 
                    + (runtime.totalMemory() / 1048576) + " MB");
            System.out.println("          USED MEMORY: " 
                    + ((runtime.totalMemory() - runtime.freeMemory()) / 1048576) 
                    + " MB");
            System.out.println();
    }
    

    @Override
    public int[][] getData() {
        
        System.out.println();
        System.out.println(numZeros + " zeros");
        System.out.println(((((double)numZeros)  / ((double)numNums)) * 100) + " percent zeros");
        System.out.println((1.0 / ((double)0x7fffffff)) + " percent zeros ");
        System.out.println();
        
        int[][] out = new int[6][];
        out[0] = getLandmass(map);
        out[1] = getLandiness(map);
        out[2] = getTemps(map);
        out[3] = getRain(map);
        out[4] = getBiomes(map);
        out[5] = getBiomes2(map);
        return out;
    }

    @Override
    public String getParameters() {
        return "";
    }

    @Override
    public void setParameters(String param) {
        // Will need expanding with further options
    }

    @Override
    public IPalette[] getColorPaletes() {
        IPalette[] out = new IPalette[6];
        out[0] = new DiscretePalette();
        int[] biomeColors = new int[BiomeType.values().length];
        for(int i = 0; i < biomeColors.length; i++) {
            biomeColors[i] = BiomeType.values()[i].color;
        }
        ((DiscretePalette)out[0]).setPalette(biomeColors);
        out[1] = new ContinuousPalette();
        ((ContinuousPalette)out[1]).setPalette(0, 10, 0xff000000, 0xffffffff);
        out[2] = new ContinuousPalette();
        ((ContinuousPalette)out[2]).setPalette(0, 24, 0xff0022ff, 0xffff8800);
        out[3] = new ContinuousPalette();
        ((ContinuousPalette)out[3]).setPalette(0, 9, 0xffff8800, 0xff00ff44);
        out[4] = new LiteralPalette();
        out[5] = new LiteralPalette();
        return out;
    }

    @Override
    public String getName() {
        return "Chunky Region Maker";
    }

    @Override
    public String[] getLayerNames() {
        return new String[]{"Landmass", "Landiness", "Temperature", "Wetness", 
                            "Biomal Areas", "Biomes"};
    }

    @Override
    public int getNumLayers() {
        String[] layers = getLayerNames();
        return layers.length;
    }

    @Override
    public int getWidth() {
        return ChunkMaker.RSIZE;
    }

    @Override
    public int getHeight() {
        return ChunkMaker.RSIZE;
    }
    
    private int colorBlend(int c1, int c2) {
        int r = ((((0x00ff0000 & c1) >> 16) * 4) + ((0x00ff0000 & c2) >> 16)) / 5; 
        int g = ((((0x0000ff00 & c1) >> 8) * 4) + ((0x0000ff00 & c2) >> 8))  / 5; 
        int b = (((0x000000ff & c1) * 4) + (0x000000ff & c2)) / 5; 
        return 0xff000000 + (r << 16) + (g << 8) + b;
    }

    private int[] getLandmass(ChunkTile[] map) {
        int[] out = new int[map.length];
        for(int i = 0; i < out.length; i++) {
            out[i] = map[i].getRlBiome();
        }
        return out;
    }

    private int[] getTemps(ChunkTile[] map) {
        int[] out = new int[map.length];
        for(int i = 0; i < out.length; i++) {
            out[i] = Math.max(map[i].getTemp(), 0);
        }
        return out;
     }

    private int[] getLandiness(ChunkTile[] map) {
        int[] out = new int[map.length];
        for(int i = 0; i < out.length; i++) {
            out[i] = Math.max(map[i].getVal(), 0);
        }
        return out;
    }

    private int[] getRain(ChunkTile[] map) {
        int[] out = new int[map.length];
        for(int i = 0; i < out.length; i++) {
            out[i] = Math.max(map[i].getWet(), 0);
        }
        return out;
     }

    private int[] getBiomes(ChunkTile[] map) {
        int[] out = new int[map.length];
        for(int i = 0; i < out.length; i++) {
            out[i] = map[i].getBiome() | 0xff000000;
        }
        return out;
     }

    private int[] getBiomes2(ChunkTile[] map) {
        int[] out = new int[map.length];
        for(int i = 0; i < out.length; i++) {
            out[i] = colorBlend(BiomeType.values()[map[i].getRlBiome()].color, 
                    map[i].getBiome());
        }
        return out;
     }
    
}
