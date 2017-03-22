package jaredbgreat.procgenlab.viewer;

/*
 * Copyright (C) Jared Blackburn 2016
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

/**
 *
 * @author Jared Blackburn
 */
public final class MapPanel extends JPanel {
    private Graphics graphics;
    
    public MapPanel(Image img) {
        graphics = getGraphics();
        // This could be done better, to avoid aspect ratio distortion
        graphics.drawImage(img, 0, 0, getWidth(), getHeight(), this);
    }

}
