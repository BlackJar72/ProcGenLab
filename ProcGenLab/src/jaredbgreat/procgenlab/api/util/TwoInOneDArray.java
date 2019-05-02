package jaredbgreat.procgenlab.api.util;

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
public class TwoInOneDArray {
    private final int[] data;
    private final int w, h; // width and height
    
    
    public TwoInOneDArray(int width, int height) {
        w = width;
        h = height;
        data = new int[w * h];
    }
    
    
    public int get(int x, int y) {
        return data[(y * w) + x];
    }
    
    
    public void set(int value, int x, int y) {
        data[(y * w) + x] = value;
    }
    
    
    public int[] getArray() {
        return data;
    }
    
    
    public TwoInOneDArray setArray(int[] in) {
        assert(in.length == (w * h));
        System.arraycopy(in, 0, data, 0, in.length);
        return this;
    }
    
    
    public TwoInOneDArray setArray(int[][] in) {
        assert(rightSize(in));
        for(int i = 0; i < w; i++)
            for(int j = 0; j < h; j++) {
                data[(j * w) + i] = in[i][j];
            }
        return this;
    }
    
    
    private boolean rightSize(int[][] in) {
        if(in.length != w) {
            return false;
        }
        for(int i = 0; i < in.length; i ++) {
            // Required since 2D Java arrays need not be square
            if(in[i].length != h) {
                return false;
            }
        }
        return true;
    }
    
    
    private static TwoInOneDArray makeFrom2DArray(int[][] values, 
            int width, int height) {
        return new TwoInOneDArray(width, height).setArray(values);
    }
    
    
    private static TwoInOneDArray makeFrom1DArray(int[] values, 
            int width, int height) {
        return new TwoInOneDArray(width, height).setArray(values);
    }
    
}
