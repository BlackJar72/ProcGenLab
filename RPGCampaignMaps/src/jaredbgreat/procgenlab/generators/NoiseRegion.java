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
import jaredbgreat.procgenlab.generators.noiseregion.BiomeType;
import jaredbgreat.procgenlab.generators.noiseregion.Map;
import jaredbgreat.procgenlab.generators.noiseregion.Size;
import java.util.StringTokenizer;

/**
 *
 * @author jared
 */
public class NoiseRegion implements IGenerator {
    Map map;
    
    @Override
    public void generate(Long seed) {
        map = new Map(Size.setting.size, Size.setting.size);
        map.generate(seed);
    }

    @Override
    public int[][] getData() {
        int[][] out = new int[6][];
        out[0] = map.getLandmass();
        out[1] = map.getLandiness();
        out[2] = map.getTemps();
        out[3] = map.getRain();
        out[4] = map.getBiomes();
        out[5] = map.getBiomes2();
        return out;
    }

    @Override
    public String getParameters() {
        return "MULTI" + SRS + "Size" + SRS + "Small" + SUS + "Medium" + SUS + "Large" + SFS;
    }

    @Override
    public void setParameters(String param) {
        // Will need expanding with further options
        StringTokenizer tokens = new StringTokenizer(param, SUS + SRS + SGS + SFS);
        if(tokens.nextToken().equals("multi")) {
            if(tokens.nextToken().equals("Size")) {
                Size.set(tokens.nextToken());
            }
        }
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
        return "Noisey Region Maker";
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
        return Size.setting.size;
    }

    @Override
    public int getHeight() {
        return Size.setting.size;
    }
    
}
