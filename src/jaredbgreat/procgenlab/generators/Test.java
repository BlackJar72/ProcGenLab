/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaredbgreat.procgenlab.generators;

import jaredbgreat.procgenlab.api.IGenerator;
import jaredbgreat.procgenlab.api.IPalette;
import static jaredbgreat.procgenlab.api.util.Delims.*;
import jaredbgreat.procgenlab.api.palettes.ContinuousPalette;
import jaredbgreat.procgenlab.api.palettes.DiscretePalette;
import jaredbgreat.procgenlab.api.palettes.LiteralPalette;
import java.util.Random;
import java.util.StringTokenizer;

/**
 *
 * @author jared
 */
public class Test implements IGenerator {
    int x = 100, y = 100;
    Random random;
    int[][] data;
    IPalette[] palettes;
    
    
    public Test() {
        palettes = new IPalette[1];
        DiscretePalette pal = new DiscretePalette();
        //ContinuousPalette pal = new ContinuousPalette();
        //LiteralPalette pal = new LiteralPalette();
        pal.setPalette(new int[]{0xff000000, 0xffffffff});
        //pal.setPalette(0, 65535, 0x00000000, 0x00ffffff);        
        palettes[0] = pal;
    }
    

    @Override
    public void generate(Long seed) {
        random = new Random(seed);
        data = new int[1][x * y];
        for(int i = 0; i < 1; i++) {
            for(int j = 0; j < x * y; j++) {
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
        return "INT" + SRS + "Width" + SRS + x + SGS
                + "INT" + SRS + "Height" + SRS + y + SFS;
    }

    @Override
    public void setParameters(String param) {
        StringTokenizer l1 = new StringTokenizer(param, SGS + SFS);
        while(l1.hasMoreTokens()) {
            StringTokenizer l2 = new StringTokenizer(l1.nextToken(), SRS);
            if(!l2.nextToken().toLowerCase().equals("int")) {
                continue;
            }
            if(l2.nextToken().toLowerCase().equals("width")) {
                x = Integer.valueOf(l2.nextToken());
            } else {
                y = Integer.valueOf(l2.nextToken());
            }
        }
    }

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
        return x;
    }

    @Override
    public int getHeight() {
        return y;
    }
    
}
