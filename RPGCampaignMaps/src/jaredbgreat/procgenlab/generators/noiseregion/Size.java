/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaredbgreat.procgenlab.generators.noiseregion;

import jaredbgreat.procgenlab.generators.region.*;

/**
 *
 * @author Jared Blacburn
 */
public enum Size {
    
    SMALL (128, 3.0),
    MEDIUM (256, 1.5),
    LARGE (384, 1.0);
    
    public final int size;
    public final double falloff;
    public final double srfalloff;
    
    public static Size setting = MEDIUM;
    
    Size(int s, double f) {
        size = s;
        falloff = f;
        srfalloff = Math.sqrt(f);
    }
    
    
    public static void set(String change) {
        setting = Size.valueOf(change.toUpperCase());
    }
    
}
