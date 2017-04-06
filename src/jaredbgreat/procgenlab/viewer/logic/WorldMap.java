package jaredbgreat.procgenlab.viewer.logic;

/*
 * Copyright (C) Jared Blackburn 2016
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
*/

import jaredbgreat.procgenlab.exceptions.ImageCreationException;
import jaredbgreat.procgenlab.exceptions.InconsistentLayersException;
import jaredbgreat.procgenlab.interfaces.IGenerator;
import jaredbgreat.procgenlab.interfaces.IPalette;
import jaredbgreat.procgenlab.interfaces.IWorldMap;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author Jared Blackburn
 */
public class WorldMap implements IWorldMap {
    private Image[] images;
    private String[] names;
    
    
    public WorldMap() {}
    
    
    public void setData(IGenerator generator) 
                        throws InconsistentLayersException, 
                               ImageCreationException {
        int layers = generator.getNumLayers();
        names = generator.getNames();
        if(names.length != layers) {
            if(names.length < layers) {
                throw new 
                    InconsistentLayersException("Not all layers are named.");
            } else {
                throw new 
                    InconsistentLayersException("More names given than layers.");                
            }
        }
        IPalette[] palettes = generator.getColorPaletes();
        if(palettes.length != layers) {
            if(palettes.length < layers) {
                throw new 
                    InconsistentLayersException("Not all layers have palettes.");
            } else {
                throw new 
                    InconsistentLayersException("More palettes than "
                            + "layers given.");                
            }
        }
        int[][] maps = generator.getData();
        if(maps.length != layers) {
            if(maps.length < layers) {
                throw new 
                    InconsistentLayersException("Not all layers have maps.");
            } else {
                throw new 
                    InconsistentLayersException("More names given than maps.");                
            }
        }
        images = new BufferedImage[layers];
        int w = generator.getWidth(), h = generator.getHeight();
        for(int i = 0; i < layers; i++) {
            images[i] = palettes[i].getImage(w, h, maps[i]);
        }
    }
    

    @Override
    public Image getImage(int number) {
        return images[number];
    }
    

    @Override
    public String getImageName(int number) {
        return names[number];
    }
    
}
