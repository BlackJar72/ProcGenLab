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
import jaredbgreat.procgenlab.generators.scalablenoise.chunk.MapMaker;
import jaredbgreat.procgenlab.generators.scalablenoise.chunk.BiomeType;
import jaredbgreat.procgenlab.generators.scalablenoise.chunk.SizeScale;
import java.util.StringTokenizer;

/**
 *
 * @author jared
 */
public class ScalableNoise implements IGenerator {
    private static final int BIOME_SIZE = 16;
    private int biomeSize = BIOME_SIZE;
    private final SizeScale.Wrapper sizeScale;
    MapMaker map;

    public ScalableNoise() {
        this.sizeScale = new SizeScale.Wrapper();
    }
    
    @Override
    public void generate(Long seed) {
        map = new MapMaker(0, 0, seed, sizeScale.value, biomeSize);
        map.generate();
    }

    @Override
    public int[][] getData() {
        int[][] out = new int[7][];
        out[0] = map.getLandmass();
        out[1] = map.getLandiness();
        out[2] = map.getTemps();
        out[3] = map.getRain();
        out[4] = map.getBiomes();
        out[5] = map.getBiomes2();
        out[6] = map.getFaultlines();
        return out;
    }

    @Override
    public String getParameters() {        
        return "MULTI" + SRS + "Size Scale" + SRS + "x1" + SUS + "x2" 
                + SUS  + "x4" + SGS 
                + "INT" + SRS + "Biome Size" + SRS + BIOME_SIZE
                + SFS;
    }

    @Override
    public void setParameters(String param) {
        StringTokenizer l1 = new StringTokenizer(param, SGS + SFS);
        while(l1.hasMoreTokens()) {
            StringTokenizer tokens = new StringTokenizer(l1.nextToken(), SRS);
            String typename = tokens.nextToken();
            if(typename.equalsIgnoreCase("multi")) {
                if(tokens.nextToken().equalsIgnoreCase("Size Scale")) {
                    sizeScale.set(tokens.nextToken().toUpperCase());
                }
            } else if(typename.equalsIgnoreCase("int")) {
                if(tokens.nextToken().equalsIgnoreCase("Biome Size")) {
                    biomeSize = Integer.valueOf(tokens.nextToken());
                }
            }
        }
    }
    

    @Override
    public IPalette[] getColorPaletes() {
        IPalette[] out = new IPalette[7];
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
        out[6] = new ContinuousPalette();
        ((ContinuousPalette)out[6]).setPalette(0, 255, 0xffffffff, 0xff000000);
        return out;
    }

    @Override
    public String getName() {
        return "Scalable Regions";
    }

    @Override
    public String[] getLayerNames() {
        return new String[]{"Landmass", "Landiness", "Temperature", "Wetness", 
                            "Biomal Areas", "Biomes", "Plates / Faults"};
    }

    @Override
    public int getNumLayers() {
        String[] layers = getLayerNames();
        return layers.length;
    }

    @Override
    public int getWidth() {
        return MapMaker.RSIZE * sizeScale.value.whole;
    }

    @Override
    public int getHeight() {
        return MapMaker.RSIZE * sizeScale.value.whole;
    }
    
}
