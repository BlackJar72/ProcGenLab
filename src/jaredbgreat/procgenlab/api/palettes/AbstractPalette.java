package jaredbgreat.procgenlab.api.palettes;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

import jaredbgreat.procgenlab.exceptions.ImageCreationException;
import jaredbgreat.procgenlab.api.IPalette;
import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

/**
 *
 * @author Jared Blackburn
 */
public abstract class AbstractPalette implements IPalette {
    
    @Override
    public BufferedImage getImage(int w, int h, int[] data) 
                                        throws ImageCreationException {
        if(data.length != (w * h) ) {
            throw new ImageCreationException("Data size (" + data.length 
                    + ") for image did not match height (" + h 
                    + ") and width (" + w + ").");
        }
        BufferedImage out = new BufferedImage(w, h, TYPE_INT_ARGB);
        for(int i = 0; i < w; i++) 
            for(int j = 0; j < h; j++) {
                out.setRGB(i, j, getColor(data[(j * w) + i]));
            }
        return out;
    }
}
