package jaredbgreat.procgenlab.generators;

import jaredbgreat.procgenlab.api.IGenerator;
import jaredbgreat.procgenlab.api.IPalette;
import static jaredbgreat.procgenlab.api.Delims.*;
import jaredbgreat.procgenlab.api.palettes.DiscretePalette;
import jaredbgreat.procgenlab.generators.test.Caves;
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
        palettes = new IPalette[3];
        DiscretePalette pal = new DiscretePalette();
        //ContinuousPalette pal = new ContinuousPalette();
        //LiteralPalette pal = new LiteralPalette();
        pal.setPalette(new int[]{0xff000000, 0xffffffff});
        //pal.setPalette(0, 65535, 0x00000000, 0x00ffffff);
        palettes[0] = pal;
        palettes[1] = pal;
        palettes[2] = pal;
    }
    

    @Override
    public void generate(Long seed) {
        random = new Random(seed);
        data = new int[3][x * y];
        for(int i = 0; i < x; i++) {
            for(int j = 0; j < y; j++) {
                data[0][(i * y) + j] = random.nextInt(2);
                data[1][(i * y) + j] = i % 2;
            }
        }
        data[2] = new Caves(x, y, random).Generate();
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
        return new String[]{"Noise Test", "Stripe Test", "Caves Test"};
    }

    @Override
    public int getNumLayers() {
        return 3;
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
