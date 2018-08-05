package jaredbgreat.procgenlab.generators.infinitenoise.chunk;

import jaredbgreat.procgenlab.generators.infinitenoise.cache.MutableCoords;

// -6786601185332121418 

/**
 * This represents a 256x256 chunk map geared toward Minecraft, which this 
 * will likely become a mod for, though it could be used to hold similar 
 * data for another game.
 * 
 * Biome data for each chunk is held as a single int, with the first 8 bits 
 * representing the in game biome and the next two bytes representing a biome 
 * for world gen, which my differ in specialized situation.  The last byte is 
 * reserved for future use, most likely as a set of bit flag allowing storage 
 * of up to eight boolean variables.
 * 
 * This will likely not be used directly in these simulations, but will be 
 * created and written too for the purpose of full profiling.
 * 
 * @author Jared Blackburn
 */
public class Map {
    final MutableCoords region;
    final int[] data = new int[65536];
    
    public Map(int x, int z) {
        region = new MutableCoords().init(x, z);
    }
    
    /**
     * Gets the actual in game biome id.
     * 
     * @param x relative chunk x within region
     * @param z relative chunk x within region
     * @return The biome as a single byte.
     */
    public byte getBiomeAsByte(int x, int z) {
        return (byte)(data[(x * 256) + z] & 0xff);
    }
    
    
    /**
     * Returns in game biome id.
     * 
     * @param x relative chunk x within region
     * @param z relative chunk x within region
     * @return The biome id as in int
     */
    public int getBiome(int x, int z) {
        return (data[(x * 256) + z] & 0xff);
    }
    
    
    /**
     * Returns the and id for the biome to be for world gen.  For 
     * registered biomes this should be there real id, and less than 
     * 256.  For pseudo-biomes used for generated specialized terrain 
     * this should be between 356 and 32767 and indexed to a specialized 
     * registry map (probably actually an ArrayList).
     * 
     * The id will be returned as a short.
     * 
     * @param x relative chunk x within region
     * @param z relative chunk x within region
     * @return The biome id for world-gen as a short
     */
    public short getPseudoBiomeAsShort(int x, int z) {
        return (short)((data[(x * 256) + z] & 0xffff00) >> 8);
    }
    
    
    
    
    /**
     * Returns the and id for the biome to be for world gen.  For 
     * registered biomes this should be there real id, and less than 
     * 256.  For pseudo-biomes used for generated specialized terrain 
     * this should be between 356 and 32767 and indexed to a specialized 
     * registry map (probably actually an ArrayList).
     * 
     * The id will be returned as an int.
     * 
     * @param x relative chunk x within region
     * @param z relative chunk x within region
     * @return The biome id for world-gen as an int
     */
    public int getPseudoBiome(int x, int z) {
        return (data[(x * 256) + z] & 0xffff00) >> 8;
    }
    
    
    /**
     * This sets the biome data using a byte.
     * 
     * @param biome
     * @param x relative chunk x within region
     * @param z relative chunk x within region
     */
    public void setBiome(byte biome, int x, int z) {
        data[(x * 256) + z] &= 0xffffff00;
        data[(x * 256) + z] |= biome;
    }
    
    
    /**
     * This sets the biome data using an int.
     * 
     * @param biome
     * @param x relative chunk x within region
     * @param z relative chunk x within region
     */
    public void setBiome(int biome, int x, int z) {
        data[(x * 256) + z] &= 0xffffff00;
        data[(x * 256) + z] |= (biome & 0xff);
    }
    
    
    /**
     * This set the pseudo biome used by world gen.  This will 
     * most often be the same as the real biome, but may not if 
     * if specialized terrain generation is desired.
     * 
     * @param biome
     * @param x relative chunk x within region
     * @param z relative chunk x within region
     */
    public void setPseudoBiome(int biome, int x, int z) {
        data[(x * 256) + z] &= 0xff0000ff;
        data[(x * 256) + z] |= ((biome & 0xffff) << 8);
    }
    
    
    /**
     * This will set the real and pseudo-biomes to the same value 
     * while assuming no data has been stored (i.e., that the array 
     * is freshly initialized so that the value is zero).
     * 
     * @param biome
     * @param x relative chunk x within region
     * @param z relative chunk x within region
     */
    public void setBiomeExpress(int biome, int x, int z) {
        data[(x * 256) + z] |= biome;
        data[(x * 256) + z] |= (biome << 8);        
    }
    
    
    
    
    
    
}
