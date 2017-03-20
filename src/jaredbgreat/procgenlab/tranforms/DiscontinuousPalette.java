package jaredbgreat.procgenlab.tranforms;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

import jaredbgreat.procgenlab.exceptions.ImageCreationException;
import jaredbgreat.procgenlab.interfaces.IPalette;
import java.awt.image.BufferedImage;

/**
 *
 * @author Jared Blackburn
 */
public class DiscontinuousPalette implements IPalette  {

    @Override
    public int getColor(int value) throws ImageCreationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BufferedImage getImage(int w, int h, int[] data) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
