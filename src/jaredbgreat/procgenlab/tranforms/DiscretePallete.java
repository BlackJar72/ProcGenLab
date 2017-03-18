package jaredbgreat.procgenlab.tranforms;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
*/

import jaredbgreat.procgenlab.interfaces.IPallete;
import java.awt.Color;

/**
 *
 * @author Jared Blackburn
 */
public class DiscretePallete implements IPallete {
    private Color[] palette;

    @Override
    public Color getColor(int value) {
        // TODO: Check range
        return palette[value];
    }
}
