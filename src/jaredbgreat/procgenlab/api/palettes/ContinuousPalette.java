package jaredbgreat.procgenlab.api.palettes;

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
    private double rRange, gRange, bRange;
    
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
        //int bOut = (minB + (int)(bRange ))
        return 0xff000000 + minB + (int)(bRange *  val) 
                + (minG + (int)(gRange *  val) << 8)
                + (minR + (int)(rRange *  val) << 16);
    }
    
    
    public void setPalette(int minIn, int maxIn, int minOut, int maxOut) {
        inMin = minIn;
        inMax = maxIn;
        minB =  minOut & 0x000000ff;
        minG = (minOut & 0x0000ff00) >> 8;
        minR = (minOut & 0x00ff0000) >> 16;
        maxB =  maxOut & 0x000000ff;
        maxG = (maxOut & 0x0000ff00) >> 8;
        maxR = (maxOut & 0x00ff0000) >> 16;
        rRange = maxR - minR;
        gRange = maxG - minG;
        bRange = maxB - minB;
    }
    
}
