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
    
    
    public static long getSeedFromText(String in) {
        long out;
        in = in.trim();
        if(in.isEmpty()) {
            out = System.nanoTime();
        } else try {
            out = Long.parseLong(in);
        } catch (NumberFormatException e) {
            out = (long)in.hashCode();
        }        
        return out;
    }
    
    
    public static Random getJavaRandom(String in) {
        return new Random(getSeedFromText(in));
    }
    
}
