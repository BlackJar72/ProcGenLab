/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaredbgreat.procgenlab.generators.infinitenoise.chunk;

import jaredbgreat.procgenlab.generators.region.Size;
import static jaredbgreat.procgenlab.generators.region.Size.MEDIUM;
import static jaredbgreat.procgenlab.generators.region.Size.setting;

/**
 *
 * @author jared
 */
public enum SizeScale {
    X1 (1),
    X2 (2),
    X4 (4);
    
    public final int whole;
    public final double fract;
    public final double inv;
    
    public static SizeScale setting = X1;
    
    SizeScale(int s) {
        whole = s;
        fract = s;
        inv = 1.0 / fract;
    }
    
    
    public static class Wrapper {
        public SizeScale value;
        public void set(String change) {
            value = SizeScale.valueOf(change.toUpperCase());
        }
    }
    
    
    public static void set(String change) {
        setting = SizeScale.valueOf(change.toUpperCase());
    }
    
    
    public static void set(String change, Wrapper w) {
        w.value = SizeScale.valueOf(change.toUpperCase());
    }
    
    
    
}
