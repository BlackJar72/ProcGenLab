package jaredbgreat.procgenlab.tranforms;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
*/

import jaredbgreat.procgenlab.exceptions.ImageCreationException;

/**
 * This is basically a non-palette for dealing with RGB/ARGB images; that is 
 * for dealing with images that need no transform of the data.  All its does 
 * is take the 32 bit ints in the array and turn them into a BufferedImage 
 * of the given dimensions.  Specifically, the getColor() method with simply 
 * return the value passed in with no changes, while the getImage() method 
 * will change the bit into ARGB Colors and arrange them correctly into an 
 * image for displaying.
 * 
 * @author Jared Blackburn
 */
public class LiteralPalette extends AbstractPalette {

    @Override
    public int getColor(int value) throws ImageCreationException {
        return value;
    }
    
}
