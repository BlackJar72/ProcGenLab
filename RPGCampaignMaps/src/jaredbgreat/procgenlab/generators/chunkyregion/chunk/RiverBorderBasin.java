/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaredbgreat.procgenlab.generators.chunkyregion.chunk;

/**
 *
 * @author jared
 */
public class RiverBorderBasin {
    int x, z;
    double strength;

    public RiverBorderBasin(int x, int z, double strength) {
        this.x = x;
        this.z = z;
        this.strength = strength;
    }
    
    
    public double getWeaknessAt(int atx, int aty) {
        double xdisplace = ((double)(x - atx));
        double ydisplace = ((double)(z - aty));
        return (xdisplace * xdisplace) + (ydisplace * ydisplace);
    }
    
    
    public static boolean summateEffect(RiverBorderBasin r1, 
                RiverBorderBasin r2, BasinNode b1, BasinNode b2, ChunkTile t) {
        double effect1, effect2;
        double totalStrength =  r1.strength + r2.strength;
        effect1 = r1.strength / (r1.getWeaknessAt(t.x, t.z) * totalStrength);
        effect2 = r2.strength / (r2.getWeaknessAt(t.x, t.z) * totalStrength);
        //System.out.println(Math.abs(effect1 - effect2));
        if(checkRange(b1, b2, t)) {
            return (Math.sqrt(Math.abs(effect1 - effect2)) 
                        <= 3072.0 / (((double)t.val) * ((double)t.val) * basinDistance(b1, b2)));
        }
        else return false;
    }
    
    
    private static double basinDistance(BasinNode n1, BasinNode n2) {
        return ((n1.x - n2.x) * (n1.x - n2.x)) + ((n1.z - n2.z) * (n1.z - n2.z));
    }
    
    
    private static boolean checkRange(BasinNode b1, 
                BasinNode b2, ChunkTile t) {
        if(b1.x < b2.x) {
            if(b1.z < b2.z) {
                return (t.x > b1.x) && (t.x < b2.x) 
                        && (t.z > b1.z) && (t.z < b2.z);
            } else {
                return (t.x > b1.x) && (t.x < b2.x) 
                        && (t.z < b1.z) && (t.z > b2.z);                
            }
        } else {
            if(b1.z < b2.z) {
                return (t.x < b1.x) && (t.x > b2.x) 
                        && (t.z > b1.z) && (t.z < b2.z);
            } else {
                return (t.x < b1.x) && (t.x > b2.x) 
                        && (t.z < b1.z) && (t.z > b2.z);                
            }
        }
    }
}
