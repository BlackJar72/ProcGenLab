package jaredbgreat.procgenlab.generators.region;

import java.util.Random;

/**
 *
 * @author Jared Blackburn
 */
public class Map {
    final int w, h;
    final Tile[] map;
    private BasinNode[] basins;
    private ClimateNode[] temp;
    private ClimateNode[] wet;
    private ClimateNode[] height;
    private BiomeBasin[][] subbiomes;
    
    
    public Map(int width, int height) {
        map = new Tile[width * height];
        w = width;
        h = height;
        for(int i = 0; i < width; i++) 
            for(int j = 0; j < height; j++) {
                map[(i * h) + j] = new Tile(i, j);
            }
    }
    
    
    public void generate(Random random) {
        makeBasins(5, 10, 15, random);
        for(int i = 0; i < map.length; i++) {
            map[i].val = BasinNode.summateEffect(basins, map[i]);
            edgeFix(map[i]);
        }
        int[] noisy = refineNoise(makeNoise(random), 
                /*random.nextInt(4) + random.nextInt(4) +*/ 4);
        for(int i = 0; i < map.length; i++) {
            map[i].rlBiome = 1 - noisy[i];
        }
        double[] doubleNoise;
        makeTempBasins(10, random);
        doubleNoise = averageNoise(makeDoubleNoise(random));
        for(int i = 0; i < map.length; i++) {
            map[i].temp = Math.min(ClimateNode.summateEffect(temp, map[i], 
                    doubleNoise[i]), 4);
        }
        makeRainBasins(12, random);
        doubleNoise = averageNoise(makeDoubleNoise(random));
        for(int i = 0; i < map.length; i++) {
            map[i].wet = Math.min(ClimateNode.summateEffect(wet, map[i], 
                    doubleNoise[i]), 9);
        }
        BiomeType.makeBiomes(this, random);
        makeBiomes(256, random);
    }
    
    
    private void edgeFix(Tile t) {
        if(t.x < 10) {
            t.val += (t.x - 10);
        } else if(t.x >= (w - 10)) {
            t.val -= (t.x - w + 10);
        }
        if(t.y < 10) {
            t.val += (t.y - 10);
        } else if(t.y >= (h - 10)) {
            t.val -= (t.y - h + 10) * 2;
        }
    }
    
    
    public int[] getLandmass() {
        int[] out = new int[map.length];
        for(int i = 0; i < out.length; i++) {
            out[i] = map[i].rlBiome;
        }
        return out;
    }
    
    
    public int[] getLandiness() {
        int[] out = new int[map.length];
        for(int i = 0; i < out.length; i++) {
            out[i] = Math.max(map[i].val, 0);
        }
        return out;
    }

    public int[] getTemps() {
        int[] out = new int[map.length];
        for(int i = 0; i < out.length; i++) {
            out[i] = Math.max(map[i].temp, 0);
        }
        return out;
     }

    public int[] getRain() {
        int[] out = new int[map.length];
        for(int i = 0; i < out.length; i++) {
            out[i] = Math.max(map[i].wet, 0);
        }
        return out;
     }

    public int[] getFakeHeight() {
        int[] out = new int[map.length];
        for(int i = 0; i < out.length; i++) {
            out[i] = Math.max(map[i].fakeheight, 0);
        }
        return out;
     }

    public int[] getBiomes() {
        int[] out = new int[map.length];
        for(int i = 0; i < out.length; i++) {
            out[i] = map[i].biome;
        }
        return out;
     }

    public int[] getBiomes2() {
        int[] out = new int[map.length];
        for(int i = 0; i < out.length; i++) {
            out[i] = colorBlend(BiomeType.values()[map[i].rlBiome].color, map[i].biome);
        }
        return out;
     }
    
    public Tile getTile(int x, int y) {
        int index = (x * h) + y;
        if(index >= map.length) {
            return null;
        } else {
            return map[(x * h) + y];
        }
    }
    
    private BasinNode makeBasin(int value, double decay, Random random) {
        int place = random.nextInt(map.length);
        return new BasinNode(place % w, place / w, value, 
                decay * Size.setting.falloff);
    }
    
    
    private BasinNode makeCentralBasin1(int value, double decay, Random random) {
        int x = random.nextInt(w / 2) + (w / 4);
        int y = random.nextInt(h / 2) + (h / 4);
        return new BasinNode(x, y, value, decay * Size.setting.falloff);
    }
    
    
    private BasinNode makeCentralBasin2(int value, double decay, Random random) {
        int x = random.nextInt((w * 8) / 10) + (w / 10);
        int y = random.nextInt((h * 8) / 10) + (h / 10);
        return new BasinNode(x, y, value, decay * Size.setting.falloff);
    }
    
    
    public void makeBasins(int main, int pos, int neg, Random random) {
        basins = new BasinNode[main + pos + neg];
        for(int i = 0; i < main; i++) {
            basins[i] = makeCentralBasin1(10, 
                    BasinNode.getLogScaled(-10) / 10, random);
        }
        for(int i = main; i < (pos + main); i++) {
            basins[i] = makeCentralBasin2(9, 
                    BasinNode.getLogScaled(random.nextInt(5) - 9) / 10, random);
        }
        for(int i = (pos + main); i < basins.length; i++) {
            basins[i] = makeBasin(0, 
                    BasinNode.getLogScaled(random.nextInt(10) - 10) / 10, random);
        }
    }
    
    public BasinNode[] getBasins(int num, boolean beginning) {
        if(num > basins.length) {
            num = basins.length;
        }
        BasinNode[] out = new BasinNode[num];
        if(beginning) {
            System.arraycopy(basins, 0, out, 0, num);
        } else {
            System.arraycopy(basins, 0, out, basins.length - num, num);
        }
        return out;
    }
    
    
    private void makePoles(ClimateNode[] nodes, Random random) {
        int dist = (Size.setting.size / 6) 
                + random.nextInt(Size.setting.size / 3);
        double angle = random.nextDouble() * 2 * Math.PI;
        int x = (Size.setting.size / 2) + (int)(dist * Math.cos(angle));
        int y = (Size.setting.size / 2) + (int)(dist * Math.sin(angle));
        nodes[0] = new ClimateNode(x, y, 0, 
                (BasinNode.getLogScaled(-10) / 40) * Size.setting.falloff, 0);
        dist = (Size.setting.size / 6) + random.nextInt(Size.setting.size / 3);
        angle = angle + random.nextDouble() + (Math.PI / 2);
        //if(angle > Math.PI) {
        //    angle -= Math.PI;
        //}
        x = (Size.setting.size / 2) + (int)(dist * Math.cos(angle));
        y = (Size.setting.size / 2) + (int)(dist * Math.sin(angle));
        nodes[1] = new ClimateNode(x, y, 5, 
                (BasinNode.getLogScaled(-10) / 40) * Size.setting.falloff, 0);        
    }
    
    
    public void makeTempBasins(int n, Random random) {
        temp = new ClimateNode[n + 2];
        makePoles(temp, random);
        for(int i = 2; i < temp.length; i++) {
            temp[i] = new ClimateNode(random.nextInt(Size.setting.size), 
                random.nextInt(Size.setting.size), 
                random.nextInt(6), 
                (BasinNode.getLogScaled(random.nextInt(4) - 9) / 30) * Size.setting.falloff, 
                random.nextInt(5) + 5);
        }
    }
    
    
    public void makeRainBasins(int n, Random random) {
        wet = new ClimateNode[n];
        for(int i = 0; i < wet.length; i++) {
            int cycle = i % 3;
            switch(cycle) {
                case 0:
                    wet[i] = new ClimateNode(random.nextInt(Size.setting.size), 
                        random.nextInt(Size.setting.size), 
                        9, 
                        (BasinNode.getLogScaled(random.nextInt(4) - 10) / 30) * Size.setting.falloff, 
                        random.nextInt(5));
                    break;
                case 1:
                    wet[i] = new ClimateNode(random.nextInt(Size.setting.size), 
                        random.nextInt(Size.setting.size), 
                        0, 
                        (BasinNode.getLogScaled(random.nextInt(4) - 10) / 30) * Size.setting.falloff, 
                        random.nextInt(5));
                    break;
                case 2:
                    wet[i] = new ClimateNode(random.nextInt(Size.setting.size), 
                        random.nextInt(Size.setting.size), 
                        random.nextInt(10), 
                        (BasinNode.getLogScaled(random.nextInt(4) - 10) / 10) * Size.setting.falloff, 
                        random.nextInt(5));
                    break;
            }
        }
    }
    
    
    private void makeFakeHeight(Random random) {
        height = new ClimateNode[basins.length];
        for(int i = 0; i < height.length; i++) {
            height[i] = new ClimateNode(basins[i].x, basins[i].y, 
                        random.nextInt(basins[i].value + 1) + (basins[i].value / 2), 
                        (BasinNode.getLogScaled(random.nextInt(2) - 9) / (random.nextInt(11) + 10)) 
                                * Size.setting.srfalloff, 
                        random.nextInt(3) + 2);
        }
    }
    
    
    protected int[][] makeNoise(Random random) {
        int[][] noise = new int[w + 2][h + 2];
        for(int i = 0; i < (w + 2); i++)
            for(int j = 0; j < (h + 2); j++) {
                noise[i][j] = random.nextInt(2);
            }
        return noise;
    }
    
    
    protected int[] refineNoise(int[][] noise, int times) {
        int[][] out = noise;
        for(int i = times; i > 0; i--) {
            out = refineNoise2(out);
        }
        return refineNoise(out);
    }
    
    
    protected int[] refineNoise(int[][] noise) {
        int[] out = new int[map.length];
        // Could be better optimized, but this is a test of the gui and api
        for(int i = 1; i < (w + 1); i++) 
            for(int j = 1; j < (h + 1); j++) {
                out[((j - 1) * w) + (i - 1)] = refineCell(noise, i, j);
            }
        return out;
    }
    
    
    protected int[][] refineNoise2(int[][] noise) {
        int[][] out = new int[noise.length][noise[0].length];
        // Could be better optimized, but this is a test of the gui and api
        for(int i = 1; i < (w + 1); i++) 
            for(int j = 1; j < (h + 1); j++) {
                out[i][j] = refineCell(noise, i, j);
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
        if(sum < map[((y -1) * w) + (x - 1)].val) {
            return 0;
        } else {
            return 1;
        }
    }
    
    
    private double[][] makeDoubleNoise(Random random) {
        double[][] noise = new double[w + 4][h + 4];
        for(int i = 0; i < (w + 2); i++)
            for(int j = 0; j < (h + 2); j++) {
                noise[i][j] = (random.nextDouble() / 5) - 0.1;
            }
        return noise;
    }
    
    
    public double[] averageNoise(double[][] noise) {
        double[] out = new double[map.length];
        // Could be better optimized, but this is a test of the gui and api
        for(int i = 2; i < (w + 2); i++) 
            for(int j = 2; j < (h + 2); j++) {
                out[((j - 2) * w) + (i - 2)] = averageNoise(noise, i, j);
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
    
    
    public void makeBiomes(int sizeBlocks, Random random) {
        int size = sizeBlocks / 16;
        int across = Size.setting.size / size;
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
        for (Tile tile : map) {
            tile.biome = BiomeBasin.summateEffect(subbiomes, tile, size);
        }
    }
    
    
    private int colorBlend(int c1, int c2) {
        int r = ((((0x00ff0000 & c1) >> 16) * 4) + ((0x00ff0000 & c2) >> 16)) / 5; 
        int g = ((((0x0000ff00 & c1) >> 8) * 4) + ((0x0000ff00 & c2) >> 8))  / 5; 
        int b = (((0x000000ff & c1) * 4) + (0x000000ff & c2)) / 5; 
        return 0xff000000 + (r << 16) + (g << 8) + b;
    }
    
    
}
