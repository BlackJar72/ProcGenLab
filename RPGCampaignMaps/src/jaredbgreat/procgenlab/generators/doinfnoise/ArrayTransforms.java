package jaredbgreat.procgenlab.generators.doinfnoise;

import static jaredbgreat.procgenlab.generators.doinfnoise.MathFuncs.*;

/**
 * A class to hold functions (static methods) for transforming arrays of 
 * integer data (bytes or ints), predominantly through cellular automata as 
 * well as with randomness.
 * 
 *
 * @author Jared Blackburn
 */
// At this point many possible ways of doing this are bing considered and 
// given preliminary development.
public final class ArrayTransforms {    
    private ArrayTransforms() {/*Do not instantiate me!*/}
    
    /**
     * Generate a one dimensional representation of a square bit field.
     * 
     * The requested widths should be given as multiples of 8, though it will 
     * protect itself from errors involving this, too a point.
     * 
     * @param width the height and width of the square field
     * @param xoff the offset in fields on x
     * @param yoff the offset in fields on y
     * @param t the number series to use
     * @param random the SpatialNoise hash used to generate the numbers
     * @return 
     */
    public static byte[] makeBitfield(int width, int xoff, int yoff, int t,
                SpatialNoise random) {
        int bwidth = ((width - 7) / 8) + 1;
        int size = width * bwidth;
        xoff *= bwidth; yoff *= width;
        byte[] out = new byte[size];
        for(int i = 0; i < out.length; i++) {
            out[i] = random.byteFor((i % bwidth) + xoff, (i / bwidth) + yoff, t);
        }
        return out;
    }

    /**
     * Generate a table of bytes.
     * 
     * @param xsize the number of row
     * @param ysize the number of columns
     * @param xoff the number of tables off from the central (0, 0) table
     * @param yoff the number of tables off from the central (0, 0) table
     * @param t the number series to use
     * @param random the SpatialNoise hash used to generate the numbers
     * @return 
     */
    public static byte[][] genBytesAt(int xsize, int ysize, 
                int xoff, int yoff, int t, SpatialNoise random) {
        byte out[][] = new byte[xsize][ysize];
        xoff *= xsize; yoff *= ysize;
        for(int i = 0; i < xsize; i++)
            for(int j = 0; j < ysize; j++) {
                out[i][j] = random.byteFor(i + xoff, j + yoff, t);
            }
        return out;
    }
    
    /**
     * Generate a table of bytes containing ones and zeros.  This is a bit 
     * wasteful of space, but an improvement over ints and simpler to use.
     * 
     * Still, it would be a good idea to develop transforms using packed bytes 
     * might be a good idea.  Trade-off complexity and increaded processing for 
     * (hopefully) 1/8th the cache misses.
     * 
     * @param xsize the number of row
     * @param ysize the number of columns
     * @param xoff the number of tables off from the central (0, 0) table
     * @param yoff the number of tables off from the central (0, 0) table
     * @param t the number series to use
     * @param random the SpatialNoise hash used to generate the numbers
     * @return 
     */
    public static byte[][] genBitsAt(int xsize, int ysize, 
                int xoff, int yoff, int t, SpatialNoise random) {
        byte out[][] = new byte[xsize][ysize];
        xoff *= xsize; yoff *= ysize;
        for(int i = 0; i < xsize; i++)
            for(int j = 0; j < ysize; j++) {
                out[i][j] = random.bitFor(i + xoff, j + yoff, t);
            }
        return out;
    }
    
    
    
}
