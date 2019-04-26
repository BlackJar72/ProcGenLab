package jaredbgreat.procgenlab.generators.doinfnoise.chunk;

import static jaredbgreat.procgenlab.generators.doinfnoise.chunk.MathFuncs.*;

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
    
    

/*-*********************************************************-*/
/*                  A Bit-Field Approach                     */
/*                                                           */
/*                    VERY EXPERIMENTAL                      */
/* I have no idea how effective this would be even in native */
/* code, much less how the JVM might get in the way or if    */
/* the JIT compiler will in any way help out!                */
/*                                                           */
/* The hope is that compating in this way will be more cache */
/* friendly while also reducing the number of random hashes  */
/* needed, and that this will outweigh the extra bitwise     */
/* processing (and not complicate thing to the point of      */
/* confusing the developer!).                                */
/*-*********************************************************-*/
    
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
     * This will apply a semi-"lifelike" cellular automata transform on a 
     * 50/50 split.  For variable cut offs a similar but more complex 
     * function will be needed.
     * 
     * @param in
     * @param out
     * @param width 
     */
    // What I still need to know -left- is this faster or slower than a byte-array
    // solution?  (And by byte array I do mean and array or byte arrays using 
    // one byte per bit.)
    public static void caTransBits(byte[] in, byte[] out, int width) {
        int wEnd = width - 1;
        int tmp, loc;
        for(int i = 1; i < wEnd; i++)
            for(int j = 1; j < wEnd; j++) {
                // Yes it looks repetetive and like a mess but:
                // Basically just find the bit at the top-left and
                // progress to the bottom right (without adding extra
                // looping variables snd conditional).
                // Then clear set the cleftental bit in output, found as 
                // up and right by one from the final location of bottom-left.
                loc = (((j - 1) * width) + (i - 1));
                tmp = in[loc / 8] >> (loc % 8);
                loc++;                
                tmp += in[loc / 8] >> (loc % 8);
                loc++;                
                tmp += in[loc / 8] >> (loc % 8);
                loc = loc + width - 2;
                tmp += in[loc / 8] >> (loc % 8);
                loc++;                
                tmp += in[loc / 8] >> (loc % 8);
                loc++;                
                tmp += in[loc / 8] >> (loc % 8);
                loc = loc + width - 2;
                tmp += in[loc / 8] >> (loc % 8);
                loc++;                
                tmp += in[loc / 8] >> (loc % 8);
                loc++;                
                tmp += in[loc / 8] >> (loc % 8);
                loc =  loc - width - 1; // The center
                out[loc / 8] &= (byte)(~(0x1 >> (loc % 8)));
                out[loc / 8] |= (byte)(1 >> (tmp / 5)) | out[loc / 8];
            }
    }
    
    
    /**
     * This will apply a semi-"lifelike" cellular automata transform based on 
     * landiness (BasinNodes influence).  For variable cut offs a similar but 
     * more complex function will be needed.
     * 
     * @param in
     * @param out
     * @param width 
     */
    public static void bbTransBits(byte[] in, byte[] out, 
                int width, int scale, BasinNode[] basins) {
        int wEnd = width - 1;
        int tmp, loc;
        for(int i = 1; i < wEnd; i++)
            for(int j = 1; j < wEnd; j++) {
                // Yes it looks repetetive and like a mess but:
                // Basically just find the bit at the top-left and
                // progress to the bottom right (without adding extra
                // looping variables snd conditional).
                // Then clear set the cleftental bit in output, found as 
                // up and right by one from the final location of bottom-left.
                loc = (((j - 1) * width) + (i - 1));
                tmp = in[loc / 8] >> (loc % 8);
                loc++;                
                tmp += in[loc / 8] >> (loc % 8);
                loc++;                
                tmp += in[loc / 8] >> (loc % 8);
                loc = loc + width - 2;
                tmp += in[loc / 8] >> (loc % 8);
                loc++;                
                tmp += in[loc / 8] >> (loc % 8);
                loc++;                
                tmp += in[loc / 8] >> (loc % 8);
                loc = loc + width - 2;
                tmp += in[loc / 8] >> (loc % 8);
                loc++;                
                tmp += in[loc / 8] >> (loc % 8);
                loc++;                
                tmp += in[loc / 8] >> (loc % 8);                
                loc =  loc - width - 1; // The center                
                if(tmp < BasinNode
                        .summateEffect(basins, i + width, j + width, scale)) {
                    out[loc / 8] &= (byte)(~(0x1 >> (loc % 8)));                    
                } else {
                    out[loc / 8] |= (byte)(1 >> (tmp / 5)) | out[loc / 8];                        
                }
            }
    }
    
    /**
     * This returns a byte array of four times the size representing doubling 
     * each of two dimension represented by the field. This does not rough, 
     * smooth, or in any way change the edge; further processing with 
     * bbTransBits is needed for that.
     * 
     * in.length must equal width * height for this to work.
     * 
     * In its expected use case width and height will be equal, making this a
     * square.
     * 
     * @param in
     * @param width
     * @param height
     * @return 
     */
    public static byte[] enlargeBitFiels(byte[] in, int width, int height) {
        // The full 2d array should be, and most be, rectagular
        // Normally (with expected use) it would square.
        byte[] out = new byte[in.length * 4];
        int loc1, loc2, off1, off2, tmp;
        for(int i = 0; i < width; i++)
            for(int j = 0; j < height; j++) {
                loc1 = (j * width) + i;
                loc2 = loc1 * 2;
                off1 = loc1 % 8;
                loc1 /= 8;
                off2 = loc2 % 8;
                out[loc2 / 8] |= (byte)(((in[loc1] >> off1) & 1) << off2);
                loc2 += 1; off2 = loc2 % 8;
                out[loc2 / 8] |= (byte)(((in[loc1] >> off1) & 1) << off2);
                loc2 += width; off2 = loc2 % 8;
                out[loc2 / 8] |= (byte)(((in[loc1] >> off1) & 1) << off2);
                loc2 -= 1; off2 = loc2 % 8;
                out[loc2 / 8] |= (byte)(((in[loc1] >> off1) & 1) << off2);
            }
        return out;
    }

    
    
/*-*********************************************************-*/
/*                 Probably Won't Be Used                    */
/*-*********************************************************-*/

    
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
    
    
/*-*********************************************************-*/
/*                  A Byte Array Approach                    */
/*-*********************************************************-*/
    
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
    
    /**
     * This will apply a semi-"lifelike" cellular automata transform on a 
     * 50/50 split.  For variable cut offs a similar but more complex 
     * function will be needed.
     * 
     * @param in
     * @param out
     * @param width 
     */
    public static void caTransBytes(byte[][] in, byte[][] out, int width) {
        int wEnd = width - 1;
        int tmp;
        for(int i = 1; i < wEnd; i++)
            for(int j = 1; j < wEnd; j++) {
                tmp = in[i -1][j - 1]
                    + in[i][j - 1]
                    + in[i + 1][j - 1]
                    + in[i - 1][j]
                    + in[i][j]
                    + in[i + 1][j]
                    + in[i - 1][j + 1]
                    + in[i][j + 1]
                    + in[i + 1][j + 1];
                out[i][j] = (byte)(tmp / 5);
            }
    }
    
    /**
     * This will apply a semi-"lifelike" cellular automata transform on a 
     * 50/50 split.  For variable cut offs a similar but more complex 
     * function will be needed.
     * 
     * @param in
     * @param out
     * @param width 
     */
    public static void bbTransBytes(byte[][] in, byte[][] out, 
                int width, int scale, BasinNode[] basins) {
        int wEnd = width - 1;
        int tmp;
        for(int i = 1; i < wEnd; i++)
            for(int j = 1; j < wEnd; j++) {
                tmp = in[i -1][j - 1]
                    + in[i][j - 1]
                    + in[i + 1][j - 1]
                    + in[i - 1][j]
                    + in[i][j]
                    + in[i + 1][j]
                    + in[i - 1][j + 1]
                    + in[i][j + 1]
                    + in[i + 1][j + 1];
                if(tmp < BasinNode
                        .summateEffect(basins, i + width, j + width, scale)) {
                    out[i][j] = 0;
                } else {
                    out[i][j] = 1;                    
                }
            }
    }
    
    /**
     * This returns a 2d byte array of twice the dimension of the input 
     * array in which in byte is transformed into a 2x2 square of bytes.  This 
     * does not rough, smooth, or in any way change the edge; further 
     * processing with bbTransBytes is needed for that.
     * 
     * This assumes and requires the byte[][] give as import is an array of 
     * equal length byte array (i.e., represents a rectangle); variable lengths 
     * will break this!
     * 
     * @param in
     * @return 
     */
    public static byte[][] enlargeByteArray(byte[][] in) {
        // The full 2d array should be, and most be, rectagular
        // Normally (with expected use) it would square.
        byte[][] out = new byte[in.length * 2][in[0].length * 2];
        for(int i = 0; i < in.length; i++)
            for(int j = 0; j < in[0].length; j++) {
                out[i*2][j*2] = out[i*2+1][j*2] 
                        = out[i*2][j*2+1] = out[i*2+1][j*2+1] 
                        = in[i][j];                
            }
        return out;
    }
    
    
    
}
