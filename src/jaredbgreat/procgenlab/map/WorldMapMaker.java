package jaredbgreat.procgenlab.map;

/*
 * Copyright (C) Jared Blackburn 2016
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
*/

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author Jared Blackburn
 */
public class WorldMapMaker {
    private final int[] pallete;
    private final String[] names;
    
    private static int xDim;
    private static int yDim;
    
    
    public WorldMapMaker(int[] pallete, String[] names) {
        this.pallete = pallete;
        this.names   = names;
    }
    
    
    public WorldMapMaker(Color[] pallete, String[] names) {        
        this.pallete = new int[pallete.length];
        for(int i = 0; i < pallete.length; i++) {
            this.pallete[i] = pallete[i].getRGB();
        }
        this.names   = names;
    }
    
    
    public WorldMapMaker(float[] pallete, String[] names) {
        this.pallete = new int[pallete.length / 3];
        for(int i = 0; i < this.pallete.length; i++) {
            this.pallete[i] = (new Color(i*3, i*3 + 1, i*3 + 2)).getRGB();
        }
        this.names = names;
    }
    
    
    public WorldMap makeImageMap(int[][] maps, long time) 
            throws WorldMapException 
    {
        if(maps.length != names.length) throw new WorldMapException();
        Image[] images = new Image[maps.length];
        for(int i = 0; i < maps.length; i++) {            
            int dataSize = xDim * yDim;
            if(maps[i].length != dataSize) throw new WorldMapException();
            BufferedImage image = new BufferedImage(xDim, yDim, 
                    BufferedImage.TYPE_INT_ARGB);
            for(int j = 0; j < dataSize; j++) {
                image.setRGB(j % xDim, j / xDim, pallete[maps[i][j]]);                
            }
            images[i] = image;
        }
        return new WorldMap(images, names, time);
    }
    
    
    
    ////////////////////////////////////////////////////////////////////////////
    //       GETTERS AND SETTER FOR STATIC MAP SIZE VARIABLE                  //
    ////////////////////////////////////////////////////////////////////////////
    
    public int getXDim() {
        return xDim;
    }
    
    
    public int getYDim() {
        return yDim;
    }
    
    
    public void setXDim(int size) {
        xDim = size;
    }
    
    
    public void setYDim(int size) {
        yDim = size;
    }
    
    
    public void setSize(int x, int y) {
        xDim = x;
        yDim = y;
    }
}
