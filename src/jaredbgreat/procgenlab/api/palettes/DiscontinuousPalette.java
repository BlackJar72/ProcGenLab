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
public class DiscontinuousPalette extends AbstractPalette {
    private ContinuousPalette[] ps;

    @Override
    public int getColor(int value) throws ImageCreationException {
        // Note: There is no way now of making sure the regions
        // don't overlap -- I'm not sure how I might do that yet.
        ContinuousPalette region = getRegion(value);
        if(region == null) {
            throw new ImageCreationException("Tried to use value not in any " 
                        + "region to retrieve color");
        }
        return region.getColor(value);
    }
    
    
    private ContinuousPalette getRegion(int value) {
        for (ContinuousPalette p : ps) {
            if (p.inRange(value)) {
                return p;
            }
        }
        return null;
    }
    
    
    public void setPalette(ContinuousPalette[] in) {
        ps = in;
    }
}
