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
public class SpatialNoise {
    private final long seed;
    
    
    /*=====================================*
     * CONSTRUCTORS & BASIC CLASS METHODS  *
     *=====================================*/
    
    
    public SpatialNoise() {
        long theSeed = System.nanoTime();
        seed = theSeed ^ (theSeed << 32);
    }
    
    
    public SpatialNoise(final long theSeed) {
        seed = theSeed;
    }
    
    
    @Override
    public int hashCode() {
        return (int)seed;
    }
    
    
    @Override
    public boolean equals(Object obj) {
        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }
        final SpatialNoise other = (SpatialNoise) obj;
        return (seed == other.seed);
    }
    
    
    protected long getSeed() {
        return seed;
    }
    
    
    /*====================================*
     *  NON-STATIC METHODS USING THE SEED *
     *====================================*/
    
    
    /**
     * Generate a boolean from a given seed and coords.
     * 
     * @param x
     * @param y
     * @param z
     * @param t a fake iteration
     * @return 
     */
    public boolean booleanFor(int x, int y, int z, int t) {
        return ((longFromSeed(seed, x, y, z, t) & 1) == 1);
    }
    
    
    /** 
     * Should generate num boolean values using longFor.
     * 
     * @param x
     * @param y
     * @param z
     * @param t a fake iteration
     * @param num
     * @return 
     */
    public boolean[] booleansFor(int x, int y, int z, int t, int num) {
        boolean[] out = new boolean[num];
        long nextSeed = intFor(x, y, z, t);
        for(int i = 0; i < num; i++) {
            out[i] = booleanFromSeed(nextSeed, x, y, z, t);
            nextSeed = longFromSeed(nextSeed, x, y, z, t);
        }
        return out;
    }
    
    
    /**
     * Generate a float from a given seed and coords.
     * 
     * @param x
     * @param y
     * @param z
     * @param t a fake iteration
     * @return 
     */
    public float floatFor(int x, int y, int z, int t) {
        return ((float)(longFromSeed(seed, x, y, z, t) & 0x7fffffff)) 
                / ((float)Long.MAX_VALUE);
    }
    
    
    /** 
     * should generate num float values using longFor.
     * 
     * @param x
     * @param y
     * @param z
     * @param t a fake iteration
     * @param num
     * @return 
     */
    public float[] floatsFor(int x, int y, int z, int t, int num) {
        float[] out = new float[num];
        long nextSeed = intFromSeed(seed, x, y, z, t);
        for(int i = 0; i < num; i++) {
            out[i] = ((float)(nextSeed & 0x7fffffff) / (float)Long.MAX_VALUE);
            nextSeed = longFromSeed(nextSeed, x, y, z, t);
        }
        return out;
    }
    
    
    /**
     * Generate a double from a given seed and coords.
     * 
     * @param x
     * @param y
     * @param z
     * @param t a fake iteration
     * @return 
     */
    public double doubleFor(int x, int y, int z, int t) {
        return ((double)(longFromSeed(seed, x, y, z, t) 
                & 0x7fffffffffffffffl)) 
                / ((double)Long.MAX_VALUE);
    }
    
    
    /** 
     * should generate num double values using longFor.
     * 
     * @param x
     * @param y
     * @param z
     * @param t a fake iteration
     * @param num
     * @return 
     */
    public double[] doublesFor(int x, int y, int z, int t, int num) {
        double[] out = new double[num];
        long nextSeed = longFromSeed(seed, x, y, z, t);
        for(int i = 0; i < num; i++) {
            out[i] = ((double)(nextSeed & 0x7fffffffffffffffl) 
                    / (double)Long.MAX_VALUE);
            nextSeed = longFromSeed(nextSeed, x, y, z, t);
        }
        return out;
    }
    
    
    /**
     * Should produce a random int from seed at coordinates x, y, t
     * 
     * @param x
     * @param y
     * @param z
     * @param t a fake iteration
     * @return 
     */
    public int intFor(int x, int y, int z, int t) {
        return (int) longFromSeed(seed, x, y, z, t);
    }
    
    
    /** 
     * should generate num int values using longFor.
     * 
     * @param x
     * @param y
     * @param z
     * @param t a fake iteration
     * @param num
     * @return 
     */
    public int[] intsFor(int x, int y, int z, int t, int num) {
        int[] out = new int[num];
        out[0] = intFromSeed(seed, x, y, z, t);
        for(int i = 1; i < num; i++) {
            out[i] = intFromSeed(out[i-1], x, y, z, t);
        }
        return out;
    }
    
    
    /**
     * Should produce a random long from seed at coordinates x, y, t
     * 
     * @param x
     * @param y
     * @param z
     * @param t a fake iteration
     * @return 
     */
    public long longFor(int x, int y, int z, int t) {
        long out = seed + (15485077L * (long)t)
                        + (12338621L * (long)x) 
                        + (15485863L * (long)y)
                        + (14416417L * (long)z);
        out ^= rotateLeft(out, (x % 29) + 13);
        out ^= rotateRight(out, (y % 31) + 7);  
        out ^= rotateLeft(out, (z % 23) + 19); 
        out ^= rotateRight(out, (t % 43) + 11);
        return out;
    }
    
    
    /** 
     * should generate num long values using longFor.
     * 
     * @param x
     * @param y
     * @param z
     * @param t a fake iteration
     * @param num
     * @return 
     */
    public long[] longsFor(int x, int y, int z, int t, int num) {
        long[] out = new long[num];
        out[0] = longFromSeed(seed, x, y, z, t);
        for(int i = 1; i < num; i++) {
            out[i] = longFromSeed(out[i-1], x, y, z, t);
        }
        return out;
    }
    
    
    /*==========================================*
     *  STATIC METHODS TAKING A   SEED DIRECTLY *
     *==========================================*/
    
    
    /**
     * Generate a boolean from a given seed and coords.
     * 
     * @param seed
     * @param x
     * @param y
     * @param z
     * @param t a fake iteration
     * @return 
     */
    public static boolean booleanFromSeed(long seed, int x, int y, int z, int t) {
        return ((longFromSeed(seed, x, y, z, t) % 2) == 0);
    }
    
    
    /**
     * Generate a float from a given seed and coords.
     * 
     * @param seed
     * @param x
     * @param y
     * @param z
     * @param t a fake iteration
     * @return 
     */
    public static float floatFromSeed(long seed, int x, int y, int z, int t) {
        return ((float)(longFromSeed(seed, x, y, z, t) & 0x7fffffffffffffffl)) /
               ((float)Long.MAX_VALUE);
    }
    
    
    /**
     * Generate a double from a given seed and coords.
     * 
     * @param seed
     * @param x
     * @param y
     * @param z
     * @param t a fake iteration
     * @return 
     */
    public static double doubleFromSeed(long seed, int x, int y, int z, int t) {
        return ((double)(longFromSeed(seed, x, y, z, t) 
                & 0x7fffffffffffffffl)) 
                / ((double)Long.MAX_VALUE);
    }
    
    
    /**
     * Should produce a random int from seed at coordinates x, y, t
     * 
     * @param seed
     * @param x
     * @param y
     * @param z
     * @param t a fake iteration
     * @return 
     */
    public static int intFromSeed(long seed, int x, int y, int z, int t) {
        return (int) longFromSeed(seed, x, y, z, t);
    }
    
    
    /**
     * Should produce a random long from seed at coordinates x, y, t
     * 
     * @param seed
     * @param x
     * @param y
     * @param z
     * @param t a fake iteration
     * @return 
     */
    public static long longFromSeed(long seed, int x, int y, int z, int t) {
        long out = seed + (15485077L * (long)t) 
                        + (12338621L * (long)x) 
                        + (15485863L * (long)y)
                        + (14416417L * (long)z)
                        +  32452841L;
        out ^= rotateLeft(out, (x % 29) + 13);
        out ^= rotateRight(out, (y % 31) + 7);  
        out ^= rotateLeft(out, (z % 23) + 19);
        out ^= rotateRight(out, (t % 43) + 11);
        return out;
    }
    
    
    /*=============================*
     *  INTERNAL UNTILITY METHODS  *
     ==============================*/
    
    
    /**
     * Performs left bit shift (<<) with wrap-around.
     * 
     * @param in
     * @param dist
     * @return 
     */
    private static long rotateLeft(long in, int dist) {
        long out = in << dist;
        out += (in >>> (64 - dist));    
        return out;
    }
    
    
    /**
     * Performs right bit shift (>>) with wrap-around.
     * 
     * @param in
     * @param dist
     * @return 
     */
    private static long rotateRight(long in, int dist) {
        long out = in >>> dist;
        out += (in << (64 - dist));    
        return out;
    }
    
    
    public RandomAt getRandomAt(int x, int y, int z, int t) {
        return new RandomAt(this, x, y, z, t);
    }
    
    
    /**
     * The Random will return a series of numbers at a set of coords (including 
     * time), effectively acting as a traditional PRNG specific to the given
     * location.  The methods are essentially the more commonly used methods
     * from java.util.random, though though using a modified xorshift that is 
     * specific to the coordinates gives.
     */
    public static class RandomAt extends SpatialNoise {
        private long nextSeed;
        private final int x, y, z, t;
        
        
        /**
         * Create a RandomAt using a given seed.
         * 
         * @param seed
         * @param x
         * @param y
         * @param z
         * @param t 
         */
        public RandomAt(long seed, int x, int y, int z, int t) {
            super(seed);
            nextSeed = super.getSeed();
            this.x = x;
            this.y = y;
            this.z = z;
            this.t = t;
        }
        
        
        /**
         * Create a RandomAt with the same seed as the SpatialRandom that is 
         * given.
         * @param from
         * @param x
         * @param y
         * @param z
         * @param t 
         */
        public RandomAt(SpatialNoise from, int x, int y, int z, int t) {
            nextSeed = from.getSeed();
            this.x = x;
            this.y = y;
            this.z = z;
            this.t = t;
        }
        
        
        /**
         * Get the next available long.
         * 
         * @return a pseudorandom long.
         */
        public long nextLong() {
            nextSeed = longFromSeed(nextSeed, x, y, z, t);
            return nextSeed;
        }
        
        
        /**
         * Get the next available int.
         * 
         * @return a pseudorandom int.
         */
        public int nextInt() {
            nextSeed = longFromSeed(nextSeed, x, y, z, t);
            return (int)nextSeed;
        }
        
        
        /**
         * Get the next pseudorandom int between 0 and bound.
         * 
         * This is the same as Math.abs(nextInt() % bound).
         * 
         * @param bound
         * @return a pseudorandom in between 0 and bounds
         */
        public int nextInt(int bound) {
            nextSeed = longFromSeed(nextSeed, x, y, z, t);
            return (((int)nextSeed) & 0x7fffffff) % bound;
        }
        
        
        /**
         * Get the next pseudorandom boolean -- basically a virtual coin toss.
         * 
         * @return a pseudorandom boolean
         */
        public boolean nextBoolean() {
            nextSeed = longFromSeed(nextSeed, x, y, z, t);
            return ((nextSeed & 1) == 1);
        }
        
        
        /**
         * Get the next pseudorandom float.
         *
         * @return a pseudorandom float
         */
        public float nextFloat() {
            nextSeed = longFromSeed(nextSeed, x, y, z, t);
            return ((float)(nextSeed & 0x7fffffffffffffffl) / (float)Long.MAX_VALUE);
        }
        
        
        /**
         * Get the next double.
         * 
         * @return a pseudorandom double
         */
        public double nextDouble() {
            nextSeed = longFromSeed(nextSeed, x, y, z, t);
            return ((double)(nextSeed & 0x7fffffffffffffffl) 
                    / (double)Long.MAX_VALUE);
        }        
    }
    
    
    public static int absModulus(int in, int bound) {
        if(in < 0) {
            return -(in % bound);             
        } else {
            return (in % bound);
        }
    }
}

