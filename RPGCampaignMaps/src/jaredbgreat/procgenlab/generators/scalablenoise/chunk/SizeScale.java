package jaredbgreat.procgenlab.generators.scalablenoise.chunk;

/**
 *
 * @author Jared Blackburn
 */
public enum SizeScale {
    X1 (1, 0),
    X2 (2, 1),
    X4 (4, 2);
    
    public final int whole;
    public final double fract;
    public final double inv;
    public final int log; // determines fractal iterations, mostly
    public final int sq;
    
    public static SizeScale setting = X1;
    
    SizeScale(int s, int l) {
        whole = s;
        fract = s;
        inv = 1.0 / fract;
        log = l;
        sq = whole * whole;
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
