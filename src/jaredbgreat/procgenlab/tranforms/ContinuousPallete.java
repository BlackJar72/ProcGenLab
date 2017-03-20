package jaredbgreat.procgenlab.tranforms;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
*/

import jaredbgreat.procgenlab.exceptions.ImageCreationException;
import jaredbgreat.procgenlab.interfaces.IPalette;
import java.awt.Color;
import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

/**
 *
 * @author Jared Blackburn
 */
public class ContinuousPallete implements IPalette {
    private double inMin, inMax;
    private int minR, maxR, minG, maxG, minB, maxB, minA, maxA;
    private boolean usesAlpha;
    
    boolean inRange(int value) {
        return ((value < inMin) || (value > inMax));
    }

    @Override
    public int getColor(int value) throws ImageCreationException {
        if((value < inMin) || (value > inMax)) {
            throw new ImageCreationException("Tried to use value outside "
                            + "defined range of palette to produce a color.");
        }
        int out;
        double val = (double)value;
        val -= inMin;
        val /= (inMax - inMin);
        out =  minB + (int)(maxB *  val) 
                + (minG + (int)(maxG *  val) << 8)
                + (minR + (int)(maxR *  val) << 16);
        if(usesAlpha) {
            out +=  (minR + (int)(maxR *  val) << 24);
        }
        return out;
    }
    
    
    @Override
    public BufferedImage getImage(int w, int h, int[] data) throws Exception {
        // This is identical to the version in DiscretePallete -- add an 
        // abstract base class?
        if(data.length != (w * h)) {
            throw new ImageCreationException("Data size (" + data.length 
                    + ") for image did not match height (" + h 
                    + ") and width (" + w + ").");
        }
        BufferedImage out = new BufferedImage(w, h, TYPE_INT_ARGB);
        for(int i = 0; i < w; i++) 
            for(int j = 0; j < h; j++) {
                out.setRGB(i, j, getColor(data[(i * h) + j]));
            }
        return out;
    }
    
}
