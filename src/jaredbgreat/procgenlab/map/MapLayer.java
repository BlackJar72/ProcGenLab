package jaredbgreat.procgenlab.map;

/*
 * Copyright (C) Jared Blackburn 2016
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
*/

import java.awt.Image;

/**
 *
 * @author Jared Blackburn
 */
final class MapLayer {
    private final String name;
    private final Image  map;
    
    
    public MapLayer(Image map, String name) {
        this.name = name;
        this.map  = map;
    }
    
    
    public String getName() {
        return name;
    }
    
    
    public Image getMap() {
        return map;
    }    
}
