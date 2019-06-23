package jaredbgreat.procgenlab.generators.scalablenoise.chunk;


/**
 *
 * @author Jared Blackburn
 */
public class ChunkTile implements Comparable {
    public static final int SIZE = 16;
    final int x, z;
    int val = 0, rlBiome = 0;
    int temp = 0, wet = 0;
    int biomeSeed = 0, biome = 0;
    int noiseVal = 0;
    int river = 0;
    double faults, height;
    boolean mountain = false, hilly = false, beach = false;
    
    public ChunkTile(int x, int y) {
        this.x = x;
        this.z = y;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public int getVal() {
        return val;
    }

    public double getHeight() {
        return height;
    }

    public int getTemp() {
        return temp;
    }

    public int getWet() {
        return wet;
    }

    public int getBiomeSeed() {
        return biomeSeed;
    }

    public int getBiome() {
        return biome;
    }

    public int getNoise() {
        return noiseVal;
    }

    public boolean isIsMountain() {
        return mountain;
    }

    public boolean isIsHilly() {
        return hilly;
    }   

    public boolean isIsBeach() {
        return beach;
    }    

    public boolean isRiver() {
        return river > 0;
    } 
    
    public void nextBiomeSeed() {
    	biomeSeed ^= biomeSeed << 13;
    	biomeSeed ^= biomeSeed >> 19;
    	biomeSeed ^= biomeSeed << 7;
    	biomeSeed &= 0x7fffffff;
    }
    
    
    public int compareTo(ChunkTile o) {
        if(height > o.height) return 1;
        else if(height < o.height) return -1;
        return 0;
    }
    

    @Override
    public int compareTo(Object o) {
        // Just let it throw an exception if not a valid type
        ChunkTile oc = (ChunkTile)o;
        if(height > oc.height) return 1;
        else if(height < oc.height) return -1;
        return 0;        
    }
}
