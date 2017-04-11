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
public class DiscretePalette extends AbstractPalette {
    private int[] palette;

    @Override
    public int getColor(int value) throws ImageCreationException {
        if((value < 0) || (value >= palette.length)) {
            throw new ImageCreationException("Tried to use value not in color "
                            + "paletta (outside bounds of color array).");
        }
        return palette[value];
    }
    
    
    public void setPalette(int[] in) {
        palette = in;
    }
}
