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
import static java.awt.image.BufferedImage.*;

/**
 *
 * @author Jared Blackburn
 */
public class DiscretePallete implements IPalette {
    private int[] palette;

    @Override
    public int getColor(int value) throws ImageCreationException {
        if((value < 0) || (value > palette.length)) {
            throw new ImageCreationException("Tried to use value not in color "
                            + "paletta (outside bounds of color array).");
        }
        return palette[value];
    }
    
    
    @Override
    public BufferedImage getImage(int w, int h, int[] data) 
                                        throws ImageCreationException {
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
