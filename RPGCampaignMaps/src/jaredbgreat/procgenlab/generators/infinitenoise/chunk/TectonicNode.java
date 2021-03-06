package jaredbgreat.procgenlab.generators.infinitenoise.chunk;

/**
 *
 * @author Jared Blackburn
 */
public class TectonicNode {
    final int x, y;
    double value;

    
    public TectonicNode(int x, int y, double value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }
    
    
    public double getWeaknessAt(int atx, int aty) {
        //System.out.println(x + " - " + atx + " = " + (x - atx) + "; " + y + " - " + aty + " = " + (y - aty));
        double xdisplace = (double)(x - atx);
        double ydisplace = (double)(y - aty);
        //System.out.println(Math.sqrt(Math.sqrt((xdisplace * xdisplace) 
        //        + (ydisplace * ydisplace))));
        return Math.sqrt(Math.sqrt((xdisplace * xdisplace) 
                + (ydisplace * ydisplace)));
    }
    
    
    public static double summateEffect(TectonicNode[] n, ChunkTile t) {
        double effect = 1.0;
        double power, weakness;
        for(int i = 0; i < n.length; i += 2) {
            if(((n[i].x == t.x) && (n[i].y == t.z)) 
                    || ((n[i+1].x == t.x) && (n[i+1].y == t.z))) {
                return 1.0;
            }
            weakness = n[i].getWeaknessAt(t.x, t.z);
            power = weakness;
            weakness = n[i + 1].getWeaknessAt(t.x, t.z);
            power -= weakness;
            power = Math.abs(power);
            if(power < effect) {
                effect = power;
            }
        }
        //System.out.println(effect);
        return effect;
    }
    
}
