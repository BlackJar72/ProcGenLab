package jaredbgreat.procgenlab.generators.doinfnoise.chunk;

/**
 *
 * @author Jared Blackburn
 */
public class BasinNode {
    final double x, y, value;
    final double decay;
    private static final double[] LOGTABLE = makeLogTable();
    
    
    public BasinNode(int x, int y, int value, double decay) {
        this.x = x;
        this.y = y;
        this.value = value;
        this.decay = decay;
    }
    
    
    public double getRelativeWeakness(int range) {
        double effect = ((range) * decay);
        return range * range;
    }
    
    
    public double getWeaknessAt(int atx, int aty, double scale) {
        //System.out.println("Scale = " + scale);
        double xdisplace = ((x - (((double)atx) * scale)) * decay);
        double ydisplace = ((y - (((double)aty) * scale)) * decay);
        return Math.min((xdisplace * xdisplace) + (ydisplace * ydisplace), 1.0);
    }
    
    
    public static int summateEffect(BasinNode[] n, int x, int z, double scale) {
        double effect = 0.0;
        double sum    = 0.0;
        double power, weakness;
        for(int i = 0; i < n.length; i++) {
            if((n[i].x == x) && (n[i].y == z)) {
                return (int)n[i].value;
            }
            weakness = n[i].getWeaknessAt(x, z, scale);
            power = 1.0 / (weakness * weakness);
            sum += power;
            effect += Math.max(((double)n[i].value) * power, 0);
        }
        //System.out.println((int)(effect / sum));
        return (int)(effect / sum);
    }
    
    
    public static int summateEffect(BasinNode[] n, int x, int z, 
                double noise, double scale) {
        double effect = 0.0;
        double sum    = 0.0;
        double power, weakness;
        for(int i = 0; i < n.length; i++) {
            if((n[i].x == x) && (n[i].y == z)) {
                return (int)n[i].value;
            }
            weakness = n[i].getWeaknessAt(x, z, scale);
            power = 1.0 / (weakness * weakness);
            sum += power;
            effect += Math.max(((double)n[i].value) * power, 0);
        }
        //System.out.println((int)(effect / sum));
        return (int)((effect / sum) + noise);
    }
    
    
    private static double[] makeLogTable() {
        double[] out = new double[31];
            for(int i = 0; i < out.length; i++) {
                out[i] = Math.pow(10, ((double)(i - 15)) / 10);
            }
        return out;
    }
    
    
    public static double getLogScaled(int in) {
        return LOGTABLE[in + 15];
    }
    
    
    public String toString() {
        return "    [x=" + x + ", z=" + y + ", val=" + value 
             + ", decay=" + decay + "] ";
    }
    
    
    public String briefString() {
        return "    [x=" + x + ", z=" + y + "] ";
    }
    
}
