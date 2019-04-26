package jaredbgreat.procgenlab.generators.doinfnoise.chunk;

/**
 * Represents scaling factor for the landmasses and climate zones.
 *
 * @author Jared Blackburn
 */
public enum SizeScale {
    X1 (1.0, 0),
    X2 (2.0, 1),
    X4 (4.0, 2);
    
    public final int whole;
    public final double fract;
    public final double inv;
    public final int log; // determines fractal iterations, mostly
    
    public static SizeScale setting = X1;
    
    SizeScale(double s, int l) {
        whole = (int)s;
        fract = s;
        inv = 1.0 / fract;
        log = l;
    }
    
    
    public static class Wrapper {
        public SizeScale value;
        public void set(String change) {
            value = SizeScale.valueOf(change.toUpperCase());
        }
    }
    
    
    public static void set(String change) {
        setting = SizeScale.valueOf(change.toUpperCase());
    }
    
    
    public static void set(String change, Wrapper w) {
        w.value = SizeScale.valueOf(change.toUpperCase());
    }
    
    
    
}
