package jaredbgreat.procgenlab.generators.scalablenoise.chunk;

/**
 *
 * @author jared
 */
public class ClimateNode extends BasinNode {    
    final double faintness;
    

    public ClimateNode(int x, int y, int value, double decay, int faintness) {
        super(x, y, value, decay);
        this.faintness = faintness * decay;
    }
    
    
    public double getRelativeWeakness(int range) {
        double effect = ((range + faintness) * decay);
        return range * range;
    }
    
    
    public double getWeaknessAt(double atx, double aty) {
        double xdisplace = ((double)(x - atx) * decay);
        double ydisplace = ((double)(y - aty) * decay);
        return Math.min((xdisplace * xdisplace) + (ydisplace * ydisplace), 1.0)
                + faintness;
    }
    
    
    public static double summateEffect(ClimateNode[] n, ChunkTile t, 
                double noise, double scale) {
        double effect = 0.0;
        double sum    = 0.0;
        double power, weakness;
        for(int i = 0; i < n.length; i++) {
            double x = t.x * scale;
            double z = t.z * scale;
            if((n[i].x == (int)x) && (n[i].y == (int)z) && (n[i].faintness == 0)) {
                return (int)n[i].value;
            }
            weakness = n[i].getWeaknessAt(x, z);
            power = 1.0 / weakness;
            sum += power;
            effect += ((double)n[i].value) * power;
        }
        return Math.max((effect / sum) + noise, 0);
    }
    
    
    public String toString() {
        return "    [x=" + x + ", z=" + y + ", val=" + value + ", decay=" + decay + ", faint=" + "] ";
    }
    
}
