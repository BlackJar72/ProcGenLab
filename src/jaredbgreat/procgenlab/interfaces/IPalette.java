package jaredbgreat.procgenlab.interfaces;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
*/

import jaredbgreat.procgenlab.exceptions.ImageCreationException;
import java.awt.image.BufferedImage;

/**
 *
 * @author Jared Blackburn
 */
public interface IPalette {
    public int getColor(int value) throws ImageCreationException;
    public BufferedImage getImage(int w, int h, int[] data) 
            throws ImageCreationException;
}
