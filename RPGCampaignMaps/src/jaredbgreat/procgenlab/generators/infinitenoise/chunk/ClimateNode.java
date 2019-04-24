package jaredbgreat.procgenlab.generators.infinitenoise.chunk;

/**
 *
 * @author jared
 */
public class ClimateNode extends BasinNode {

    static int summateEffect(TectonicNode[] plates, ChunkTile chunkTile, double d) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    final double faintness;

    public ClimateNode(int x, int y, int value, double decay, int faintness) {
        super(x, y, value, decay);
        this.faintness = faintness * decay;
    }
    
    
    public double getRelativeWeakness(int range) {
        double effect = ((range + faintness) * decay);
        return range * range;
    }
    
    
    public double getWeaknessAt(int atx, int aty, double scale) {
        double xdisplace = ((x - (((double)atx) * scale)) * decay);
        double ydisplace = ((y - (((double)aty) * scale)) * decay);
        return Math.min((xdisplace * xdisplace) + (ydisplace * ydisplace), 1.0)
                + faintness;
    }
    
    
    public static int summateEffect(ClimateNode[] n, ChunkTile t, 
                double noise, double scale) {
        double effect = 0.0;
        double sum    = 0.0;
        double power, weakness;
        for(int i = 0; i < n.length; i++) {
            if((n[i].x == t.x) && (n[i].y == t.z) && (n[i].faintness == 0)) {
                return (int)n[i].value;
            }
            weakness = n[i].getWeaknessAt(t.x, t.z, scale);
            power = 1.0 / weakness;
            sum += power;
            effect += ((double)n[i].value) * power;
        }
        //System.out.println((int)(effect / sum));
        return (int)Math.max((effect / sum) + noise, 0);
    }
    
    
    public String toString() {
        return "    [x=" + x + ", z=" + y + ", val=" + value + ", decay=" + decay + ", faint=" + "] ";
    }
    
}
