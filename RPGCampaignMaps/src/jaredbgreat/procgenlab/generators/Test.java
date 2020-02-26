package jaredbgreat.procgenlab.generators;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
*/

import jaredbgreat.procgenlab.api.IGenerator;
import jaredbgreat.procgenlab.api.IPalette;
import static jaredbgreat.procgenlab.api.Delims.*;
import jaredbgreat.procgenlab.api.palettes.DiscretePalette;
import jaredbgreat.procgenlab.api.palettes.LiteralPalette;
import jaredbgreat.procgenlab.api.util.SpatialHash;
import jaredbgreat.procgenlab.generators.test.Caves;
import jaredbgreat.procgenlab.generators.test.Caves2;
import java.util.Random;
import java.util.StringTokenizer;

/**
 *
 * @author Jared Blackburn
 */
public class Test implements IGenerator {
    int x = 100, y = 100, depth = 0;
    SpatialHash random;
    int[][] data;
    int[][] data2;
    IPalette[] palettes;
    int numZeros;
    
    
    public Test() {
        palettes = new IPalette[5];
        DiscretePalette pal = new DiscretePalette();
        //ContinuousPalette pal = new ContinuousPalette();
        //LiteralPalette pal = new LiteralPalette();
        pal.setPalette(new int[]{0xff000000, 0xffffffff});
        //pal.setPalette(0, 65535, 0x00000000, 0x00ffffff);
        palettes[0] = pal;
        palettes[1] = new LiteralPalette();
        palettes[2] = pal;
        palettes[3] = pal;
        palettes[4] = pal;
    }
    

    @Override
    public void generate(Long seed) {
        numZeros = 0;
        random = new SpatialHash(seed);
        data = new int[5][x * y];
        for(int i = 0; i < x; i++) {
            for(int j = 0; j < y; j++) {
                data[0][(i * y) + j] = absModulus(random.intFor(i, j, 0), 2);
                data[1][(i * y) + j] = random.intFor(i, j, 1) | 0xff000000;
                if(data[1][(i * y) + j] == 0) {
                    numZeros++;
                }
                data[2][(i * y) + j] = j % 2;
            }
        }
        data[3] = new Caves(x, y, random).generate();
        data[4] = new Caves2(x, y, random).generate(depth);
    }

    @Override
    public int[][] getData() {
        System.out.println();
        System.out.println(numZeros + " zeros");
        System.out.println(((numZeros  / (x * y)) * 100) + " percent zeros");
        System.out.println((1 / 0x00ffffff) + " percent zeros ");
        System.out.println();
        return data;
        
    }

    @Override
    public String getParameters() {
        return "INT" + SRS + "Width" + SRS + x + SGS
                + "INT" + SRS + "Height" + SRS + y + SGS
                + "INT" + SRS + "Cave Fractal Depth" + SRS + depth + SFS;
    }

    @Override
    public void setParameters(String param) {
        StringTokenizer l1 = new StringTokenizer(param, SGS + SFS);
        while(l1.hasMoreTokens()) {
            StringTokenizer l2 = new StringTokenizer(l1.nextToken(), SRS);
            if(!l2.nextToken().toLowerCase().equals("int")) {
                continue;
            }
            String field = l2.nextToken().toLowerCase();
            if(field.equals("width")) {
                x = Integer.valueOf(l2.nextToken());
            } else if(field.equals("height")){
                y = Integer.valueOf(l2.nextToken());
            } else if(field.equals("cave fractal depth")){
                depth = Integer.valueOf(l2.nextToken());
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
        return new String[]{"Noise Test", "Color Noise Test", "Stripe Test", "Caves Test", 
            "Fractal Cave Test"};
    }

    @Override
    public int getNumLayers() {
        return 5;
    }

    @Override
    public int getWidth() {
        return x;
    }

    @Override
    public int getHeight() {
        return y;
    }
    
    public static int absModulus(int in, int bound) {
        if(in < 0) {
            return -(in % bound);             
        } else {
            return (in % bound);
        }
    }
    
}
