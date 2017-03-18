package jaredbgreat.procgenlab.tranforms;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
*/

import jaredbgreat.procgenlab.interfaces.IPallete;
import java.awt.Color;

/**
 *
 * @author Jared Blackburn
 */
public class ContinuousPallete implements IPallete {
    private int inMin, inMax;
    private int minR, maxR, minG, maxG, minB, maxB, minA, maxA;
    private boolean usesAlpha;

    @Override
    public Color getColor(int value) {
        // TODO: Check Range
        double val = (double)value;
        val -= inMin;
        val /= (inMax - inMin);
        if(usesAlpha) {
            return new Color(minR + (int)(maxR *  val), 
                             minG + (int)(maxG *  val), 
                             minB + (int)(maxB *  val), 
                             minA + (int)(maxA *  val));
        } else {
            return new Color(minR + (int)(maxR *  val), 
                             minG + (int)(maxG *  val), 
                             minB + (int)(maxB *  val));
        }
    }
    
}
