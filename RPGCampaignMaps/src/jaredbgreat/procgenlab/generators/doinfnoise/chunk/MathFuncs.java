package jaredbgreat.procgenlab.generators.doinfnoise.chunk;

/**
 * Math and related functions as static methods.
 *
 * @author jared
 */
public final class MathFuncs {
    private MathFuncs() {/*Do not instantiate me!*/}
    
    /**
     * This is a modulus function which will always return a positive value 
     * equivalent to the modulus of the absolute value.
     * 
     * @param a the dividend
     * @param b the divisor
     * @return the positive remainder (modulus)
     */
    public int modright(int a, int b) {
        return ((a & 0x7fffffff) % b);
    }
    
    
    
}
