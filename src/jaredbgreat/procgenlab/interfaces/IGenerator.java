package jaredbgreat.procgenlab.interfaces;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

/**
 * The interface the main class of a generator (i.e., the generators facade).
 *
 * @author Jared Blackburn
 */
public interface IGenerator {
    
    public void generate();
    public int[][] getData();
    public int[][] generateData();
    public void setParameters(String[] param);
    public int[][][] getColorPaletes();
    public String[] getNames();
    public int[] getImageSize();
    public int getNumLayers();
    
    
}
