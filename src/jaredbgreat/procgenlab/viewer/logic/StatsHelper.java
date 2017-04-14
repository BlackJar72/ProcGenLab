package jaredbgreat.procgenlab.viewer.logic;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author Jared Blackburn
 */
public class StatsHelper {
    private static final long  MILLE       = 1000000;
    private static final float FLOAT_MILLE = 1000000f;
    private static final long  UNIT        = 1000000000;
    private static final float FLOAT_UNIT  = 1000000000f;
    
    private final long[] data; // Needed for 
    private long min, max;
    private final long mean;
    private final double stdev;
    
    public StatsHelper(long[] data) {
        this.data = data;
        min = Long.MAX_VALUE;
        max = Long.MIN_VALUE;
        mean = mean();
        stdev = stdev(mean);
    }
    
    
    public String getMeanText() {
        return getTextForLong(mean);
    }
    
    
    public String getMinText() {
        return getTextForLong(min);
    }
    
    
    public String getMaxText() {
        return getTextForLong(max);
    }
    
    
    public String getStdevText() {
        return getTextForDouble(stdev);
    }
    
    
    public String getLowCIText() {
        return getTextForDouble(((double)mean) - (2 * stdev));
    }
    
    
    public String getHighCIText() {
        return getTextForDouble(((double)mean) + (2 * stdev));
    }
    
    
    private String getTextForLong(long in) {
        if(in < MILLE) {
            return in + " ns";
        } else if(in < FLOAT_UNIT) {
            return (((float)in) / FLOAT_MILLE) + " ms";
        } else {
            return (((float)in) / FLOAT_UNIT) + " s";
        }
    }
    
    
    private String getTextForDouble(double input) {
        float in = (float)input;
        if(in < MILLE) {
            return in + " ns";
        } else if(in < UNIT) {
            return ((in) / FLOAT_MILLE) + " ms";
        } else {
            return ((in) / FLOAT_UNIT) + " s";
        }
    }
    
    
    private long mean() {
        long out = 0;
        for(int i = 0; i < data.length; i++) {
            out += data[i];
            if(data[i] < min) {
                min = data[i];
            }
            if(data[i] > max) {
                max = data[i];
            }                
        }
        return out / data.length;
    }
    
    
    private double stdev(long mean) {
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
