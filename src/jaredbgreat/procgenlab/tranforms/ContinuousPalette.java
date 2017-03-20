package jaredbgreat.procgenlab.tranforms;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
*/

import jaredbgreat.procgenlab.exceptions.ImageCreationException;

/**
 *
 * @author Jared Blackburn
 */
public class ContinuousPalette extends AbstractPalette {
    private double inMin, inMax;
    private int minR, maxR, minG, maxG, minB, maxB, minA, maxA;
    
    boolean inRange(int value) {
        return ((value < inMin) || (value > inMax));
    }

    @Override
    public int getColor(int value) throws ImageCreationException {
        if((value < inMin) || (value > inMax)) {
            throw new ImageCreationException("Tried to use value outside "
                            + "defined range of palette to produce a color.");
        }
        double val = (double)value;
        val -= inMin;
        val /= (inMax - inMin);
        return minB + (int)(maxB *  val) 
                + (minG + (int)(maxG *  val) << 8)
                + (minR + (int)(maxR *  val) << 16);
    }
    
}
