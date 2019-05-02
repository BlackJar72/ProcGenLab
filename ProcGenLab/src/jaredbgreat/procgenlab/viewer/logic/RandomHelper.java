package jaredbgreat.procgenlab.viewer.logic;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

import java.util.Random;

/**
 *
 * @author Jared Blackburn
 */
public class RandomHelper {
    
    
    /**
     * This will retrieve a seed from the text given in 
     * the String, if any.  If the String is valid representation
     * of an integer capable of being represent as a long, 
     * it will be read as that number.  I the string does not 
     * represent a number it will but is not an empty string it 
     * will return the hash of the string, allowing non-number 
     * seeds to be used for easy memory.  If the string passed in 
     * is empty (or all whitespace) this will instead return the 
     * System time in nanoseconds to allow for repeated generations 
     * in which no seed is provided.
     * 
     * @param in
     * @return 
     */
    public static long getSeedFromText(String in) {
        long out;
        in = in.trim();
        if(in.isEmpty()) {
            out = System.nanoTime();
        } else try {
            out = Long.valueOf(in);
        } catch (NumberFormatException e) {
            out = (long)in.hashCode();
        }        
        return out;
    }
    
    
    /**
     * This will get an instance of java.util.Random with 
     * a seed based on the given String (if any) or the 
     * system time (if no string is given, i.e., if the 
     * input is an empty string or contains only whitespace).
     * 
     * This is a currently unused convenience function.
     * 
     * @param in
     * @return 
     */
    public static Random getJavaRandom(String in) {
        return new Random(getSeedFromText(in));
    }
    
}
