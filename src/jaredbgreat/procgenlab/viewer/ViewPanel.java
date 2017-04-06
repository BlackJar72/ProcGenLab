package jaredbgreat.procgenlab.viewer;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

import jaredbgreat.procgenlab.viewer.logic.WorldMap;
import java.awt.Color;
import javax.swing.JTabbedPane;

/**
 *
 * @author Jared Blackburn
 */
public class ViewPanel extends JTabbedPane {
    private MapPanel[] layers;
    private WorldMap map;
    
    public ViewPanel(MapPanel[] in) {
        layers = in;
        if((layers != null) && (layers.length > 0))for(MapPanel img : layers ){
            add(img);
        }
    }
    
}
