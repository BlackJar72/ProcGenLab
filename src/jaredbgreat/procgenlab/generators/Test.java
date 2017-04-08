/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaredbgreat.procgenlab.generators;

import jaredbgreat.procgenlab.api.IGenerator;
import jaredbgreat.procgenlab.api.IPalette;
import jaredbgreat.procgenlab.tranforms.DiscretePalette;
import java.util.Random;

/**
 *
 * @author jared
 */
public class Test implements IGenerator {
    Random random;
    int[][] data;
    IPalette[] palettes;
    
    
    public Test() {
        palettes = new IPalette[1];
        DiscretePalette pal = new DiscretePalette();
        pal.setPalette(new int[]{0xff000000, 0xffffffff});
        palettes[0] = pal;
    }
    

    @Override
    public void generate(Long seed) {
        random = new Random(seed);
        data = new int[1][10000];
        for(int i = 0; i < 1; i++) {
            for(int j = 0; j < 10000; j++) {
                data[i][j] = random.nextInt(2);
            }
        }
    }

    @Override
    public int[][] getData() {
        return data;
    }

    @Override
    public String getParameters() {
        return "";
    }

    @Override
    public void setParameters(String param) {}

    @Override
    public IPalette[] getColorPaletes() {
        return palettes;
    }

    @Override
    public String getName() {
        return "Test";
    }

    @Override
    public String[] getLayerNames() {
        return new String[]{"Test"};
    }

    @Override
    public int getNumLayers() {
        return 1;
    }

    @Override
    public int getWidth() {
        return 100;
    }

    @Override
    public int getHeight() {
        return 100;
    }
    
}
