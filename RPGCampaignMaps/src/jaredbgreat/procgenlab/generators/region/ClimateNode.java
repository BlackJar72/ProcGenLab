/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaredbgreat.procgenlab.generators.region;

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
    
    
    public double getWeaknessAt(int atx, int aty) {
        double xdisplace = ((double)(x - atx) * decay);
        double ydisplace = ((double)(y - aty) * decay);
        return Math.min((xdisplace * xdisplace) + (ydisplace * ydisplace), 1.0)
                + faintness;
    }
    
    
    public static int summateEffect(ClimateNode[] n, Tile t, double noise) {
        double effect = 0.0;
        double sum    = 0.0;
        double power, weakness;
        for(int i = 0; i < n.length; i++) {
            if((n[i].x == t.x) && (n[i].y == t.y) && (n[i].faintness == 0)) {
                return (int)n[i].value;
            }
            weakness = n[i].getWeaknessAt(t.x, t.y);
            power = 1.0 / weakness;
            sum += power;
            effect += ((double)n[i].value) * power;
        }
        //System.out.println((int)(effect / sum));
        return (int)Math.max((effect / sum) + noise, 0);
    }
    
}
