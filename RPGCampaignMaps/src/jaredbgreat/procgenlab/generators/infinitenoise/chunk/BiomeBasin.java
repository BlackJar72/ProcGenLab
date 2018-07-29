package jaredbgreat.procgenlab.generators.infinitenoise.chunk;


/**
 *
 * @author jared
 */
public class BiomeBasin {
    int x, y, value;
    double strength;

    public BiomeBasin(int x, int y, int value, double strength) {
        this.x = x;
        this.y = y;
        this.value = value;
        this.strength = strength;
    }
    
    
    public double getWeaknessAt(int atx, int aty) {
        double xdisplace = ((double)(x - atx));
        double ydisplace = ((double)(y - aty));
        return (xdisplace * xdisplace) + (ydisplace * ydisplace);
    }
    
    
    public static int summateEffect(BiomeBasin[][] n, ChunkTile t, int scale) {
        int minx = Math.max((t.x / scale) - 2, 0);
        int maxx = Math.min((t.x / scale) + 3, n.length);
        int miny = Math.max((t.z / scale) - 2, 0);
        int maxy = Math.min((t.z / scale) + 3, n[0].length);
        double effect = 0.0;
        int indexx = 0;
        int indexy = 0;
        double power;
        for(int i = minx; i < maxx; i++) 
            for(int j = miny; j < maxy; j++) {
                power = n[i][j].strength / n[i][j].getWeaknessAt(t.x, t.z);
                if(effect < power) {
                    effect = power;
                    indexx = i;
                    indexy = j;
            }
        }
        return n[indexx][indexy].value;
    }
    
}
