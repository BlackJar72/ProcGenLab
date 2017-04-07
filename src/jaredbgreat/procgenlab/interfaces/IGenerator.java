package jaredbgreat.procgenlab.interfaces;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

/**
 * The interface for the main class of a generator (i.e., the generators 
 * facade).
 *
 * @author Jared Blackburn
 */
public interface IGenerator {
    
    /*
     *          W.I.P.
     * 
     *  THIS MAY YET UNDERGO A LOT OF 
     *  REFACTORING!  Increasingly I'm
     *  using strings instead of number 
     *  arrays for sending data to and 
     *  from generators.  THIS IS FAR 
     *  FROM FINAL!
     */
    /** Generate content using the seed.*/
    public void generate(Long seed);
    /** Get the results of running generate.*/
    public int[][] getData();
    /** Get the parameters as a parsable String.*/
    public String getParameters();
    /** Accepts an argument String and parses it 
     * to set parameters.*/
    public void setParameters(String param);
    public IPalette[] getColorPaletes();
    /** Get the name for this generator.*/
    public String getName();
    /** Get the names of the layers for display on GUI tabs.*/
    public String[] getLayerNames();
    /** Get an array telling the size in each dimension. 
     *  Usually this should contain exactly two elements; 
     *  more generally the array length is the number of 
     *  dimension and each value is a length allong that
     *  geometric dimension.*/
    public int[] getImageSize();
    /** Get the number of layers (viewing tabs) that will 
     *  be generated.*/
    public int getNumLayers();
    /** Get the width of the map / image created.*/
    public int getWidth();
    /** Get the Height of the map / image created.*/
    public int getHeight();
    
    
}
