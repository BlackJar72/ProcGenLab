package jaredbgreat.procgenlab.map;

/*
 * Copyright (C) Jared Blackburn 2016
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
*/

import jaredbgreat.procgenlab.interfaces.IWorldMap;
import java.awt.Image;

/**
 *
 * @author Jared Blackburn
 */
public final class WorldMap implements IWorldMap {
    
    private final long nanoTime;
    private final MapLayer[] layers;
    
    
    public WorldMap(Image[] images, String[] names, long time) 
            throws WorldMapException {
        // TODO: Make special exception type
        if(images.length != names.length) throw new WorldMapException();
        nanoTime = time;
        layers = new MapLayer[images.length];
        for(int i = 0; i < images.length; i++) {
            layers[i] = new MapLayer(images[i], names[i]);
        }
    }
    

    @Override
    public Image getImage(int number) {
        if(layers == null) return null;
        // If number < 0 or number > layers.length allow Java to throw 
        // its own array index exception.
        else return layers[number].getMap();        
    }
    

    @Override
    public String getImageName(int number) {
        if(layers == null) return "";
        // If number < 0 or number > layers.length allow Java to throw 
        // its own array index exception.
        else return layers[number].getName();        
    }
    

    @Override
    public int getNumImages() {
        if(layers == null) return 0;
        else return layers.length;
    }
    

    @Override
    public long getTimeNanos() {
        return nanoTime;
    }
    

    @Override
    public float getTimeMillis() {
        return ((float)nanoTime) / 1000000.0f;
    }
    

    @Override
    public float getTimeSecods() {
        return ((float)nanoTime) / 1000000000.0f;
    }
}
