package jaredbgreat.procgenlab.viewer.logic;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

/**
 *
 * @author Jared Blackburn
 */
public class TimeStats {
    private long[] data;
    
    
    public long mean() {
        long out = 0;
        for(int i = 0; i < data.length; i++) {
            out += data[i];
        }
        return out / data.length;
    }
    
    
    public double stdev(long mean) {
        double sum = 0.0;
        for(int i = 0; i < data.length; i++) {
            // Considered using long for precision, but:
            // A slow off-line generator could take long enough to have a
            // difference of several seconds (several billion nanoseconds).  
            // Two numbers of that order of magnitude when multiplied (or 
            // one when squared) could easily be enough to overflow a long.
            // As the time is measured in nanoseconds, this will not due.
            double difference = data[i] - mean;
            sum += (difference * difference);            
        }
        return Math.sqrt(sum / ((double)(data.length - 1)));
    }
    
    
    public double stdev() {
        return stdev(mean());
    }
    
    
    
    
}
