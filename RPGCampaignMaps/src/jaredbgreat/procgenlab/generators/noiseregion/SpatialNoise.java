package jaredbgreat.procgenlab.generators.noiseregion;

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
public class SpatialNoise {
    private final long seed1;
    protected final long seed2;
    
    
    /*=====================================*
     * CONSTRUCTORS & BASIC CLASS METHODS  *
     *=====================================*/
    
    
    public SpatialNoise() {
        long theSeed = System.nanoTime();
        seed1 = theSeed;
        seed2 = new java.util.Random(seed1).nextLong();
    }
    
    
    public SpatialNoise(final long theSeed) {
        seed1 = theSeed;
        seed2 = new Random(seed1).nextLong();
    }
    
    
    public SpatialNoise(final long theSeed, final long altSeed) {
        seed1 = theSeed;
        seed2 = altSeed;
    }
    
    
    @Override
    public int hashCode() {
        return (int)seed1;
    }
    
    
    @Override
    public boolean equals(Object obj) {
        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }
        final SpatialNoise other = (SpatialNoise) obj;
        return (seed1 == other.seed1);
    }
    
    
    protected long getSeed() {
        return seed1;
    }
    
    
    /*====================================*
     *  NON-STATIC METHODS USING THE SEED *
     *====================================*/
    
    
    /**
     * Generate a boolean from a given seed and coords.
     * 
     * @param 
     * @param z
     * @param t a fake iteration
     * @return 
     */
    public boolean booleanFor(int x, int z, int t) {
        return ((longFor(x, z, t) & 1) == 1);
    }
    
    
    /**
     * Generate a float from a given seed and coords.
     * 
     * @param x
     * @param z
     * @param t a fake iteration
     * @return 
     */
    public float floatFor(int x, int z, int t) {
        return ((float)(longFor(x, z, t) & 0x7fffffff)) 
                / ((float)Long.MAX_VALUE);
    }
    
    
    /**
     * Generate a double from a given seed and coords.
     * 
     * @param x
     * @param z
     * @param t a fake iteration
     * @return 
     */
    public double doubleFor(int x, int z, int t) {
        return ((double)(longFor(x, z, t) 
                & 0x7fffffffffffffffl)) 
                / ((double)Long.MAX_VALUE);
    }
    
    
    /**
     * Should produce a random int from seed at coordinates x, y, t
     * 
     * @param seed
     * @param x
     * @param z
     * @param t a fake iteration
     * @return 
     */
    public int intFor(int x, int z, int t) {
        return (int)(longFor(x, z, t) & 0xffffffff);
    }
    
    
    /**
     * Should produce a random long from seed at coordinates x, y, t
     * 
     * @param x
     * @param z
     * @param t a fake iteration
     * @return 
     */
    public long longFor(int x, int z, int t) {
        long out = seed1 + (15485077L * (long)t)
                         + (12338621L * (long)x) 
                         + (14416417L * (long)z);
        long alt = seed2 + (179424743L * (long)t)
                         + (179426003L * (long)x) 
                         + (179425819L * (long)z);
        alt ^= rotateLeft(alt, (x % 29) + 13);
        alt ^= rotateRight(alt, (z % 31) + 7);
        alt ^= rotateLeft(alt, (t % 23) + 19);
        out ^= rotateLeft(out, ((x & 0x7fffffff) % 13) + 5);
        out ^= rotateRight(out, ((z & 0x7fffffff) % 11) + 28);  
        out ^= rotateLeft(out, ((t & 0x7ffffff)% 17) + 45); 
        return (out ^ alt);
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
        return (in << dist) | (in >>> (64 - dist));
    }
    
    
    /**
     * Performs right bit shift (>>) with wrap-around.
     * 
     * @param in
     * @param dist
     * @return 
     */
    private static long rotateRight(long in, int dist) {
        return (in >>> dist) | (in << (64 - dist));
    }
    
    
    public Random getRandom(int x, int z, int t) {
        return new Random(longFor(x, z, t));
    }
    
    
    public RandomAt getRandomAt(int x, int z, int t) {
        return new RandomAt(this, x, z, t);
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
        private final int x1, x2, z1, z2, t1, t2;
        private final long addative1;
        private final long addative2;
        /**
         * Create a RandomAt using a given seed.
         * 
         * @param seed
         * @param x
         * @param z
         * @param t 
         */
        public RandomAt(long seed1, long seed2, int x, int z, int t) {
            super(seed1, seed2);
            nextSeed = super.getSeed();
            addative1 = (15485077L * (long)t) + (12338621L * (long)x) 
                        +  (14416417L * (long)z) + 32452841L;
            addative2 = seed2 + (179424743L * (long)t)
                         + (179426003L * (long)x) 
                         + (179425819L * (long)z);
            x1 = ((x & 0x7fffffff) % 29) + 13;
            z1 = ((z & 0x7fffffff)% 31) + 7;
            t1 = ((t & 0x7fffffff)% 23) + 19;
            x2 = (x % 13) + 5;
            z2 = (z % 11) + 28;
            t2 = (t % 17) + 45;
        }
        
        
        /**
         * Create a RandomAt with the same seed as the SpatialRandom that is 
         * given.
         * @param from
         * @param x
         * @param z
         * @param t 
         */
        public RandomAt(SpatialNoise from, int x, int z, int t) {
            super(from.seed1, from.seed2);
            nextSeed = from.getSeed();
            addative1 = (15485077L * (long)t) + (12338621L * (long)x) 
                        + (14416417L * (long)z) + 32452841L;
            addative2 = seed2 + (179424743L * (long)t)
                         + (179426003L * (long)x) 
                         + (179425819L * (long)z);
            this.x1 = ((x & 0x0fffffff) % 29) + 13;
            this.z1 = ((z & 0x0fffffff) % 31) + 7;
            this.t1 = ((t & 0x0fffffff) % 23) + 19;
            x2 = (x % 13) + 5;
            z2 = (z % 11) + 28;
            t2 = (t % 17) + 45;
        }
        
        
        /**
         * Get the next available long.
         * 
         * @return a pseudorandom long.
         */
        public long nextLong() {
            nextSeed += addative1;
            long alt = seed2 + addative2;
            alt ^= rotateLeft(seed2, x2);
            alt ^= rotateRight(alt, z2);
            alt ^= rotateLeft(alt, t2);
            nextSeed ^= rotateLeft(nextSeed, x1); 
            nextSeed ^= rotateLeft(nextSeed, z1);
            nextSeed ^= rotateRight(nextSeed, t1);
            nextSeed += alt;
            return nextSeed;
        }
        
        
        /**
         * Get the next available int.
         * 
         * @return a pseudorandom int.
         */
        public int nextInt() {
            return (int)nextLong();
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
            return (((int)nextLong()) & 0x7fffffff) % bound;
        }
        
        
        /**
         * Get the next pseudorandom boolean -- basically a virtual coin toss.
         * 
         * @return a pseudorandom boolean
         */
        public boolean nextBoolean() {
            return ((nextLong() & 1) == 1);
        }
        
        
        /**
         * Get the next pseudorandom float.
         *
         * @return a pseudorandom float
         */
        public float nextFloat() {
            return ((float)(nextLong() 
                    & 0x7fffffffffffffffl) / (float)Long.MAX_VALUE);
        }
        
        
        /**
         * Get the next double.
         * 
         * @return a pseudorandom double
         */
        public double nextDouble() {
            return ((double)(nextLong() & 0x7fffffffffffffffl) 
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

