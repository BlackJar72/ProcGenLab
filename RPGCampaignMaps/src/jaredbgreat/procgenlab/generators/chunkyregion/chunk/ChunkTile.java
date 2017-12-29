/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaredbgreat.procgenlab.generators.chunkyregion.chunk;

/**
 *
 * @author jared
 */
public class ChunkTile {
    static final int size = 16;
    final int x, z;
    int val = 0, rlBiome = 0;
    int temp = 0, wet = 0;
    int biomeSeed = 0, biome = 0;
    //int midx = 8, midy = 8;
    //boolean isMountain = false, isHilly = false, isRiver = false;
    
    
    
//*********************************************************************************/
//                         DEBUGGING / PROFILING                                   /
//*********************************************************************************/
public static volatile long num = 0;
@Override
public void finalize() throws Throwable {
	num--;
	super.finalize();
}
//*********************************************************************************/
    
    public ChunkTile(int x, int z) {
        this.x = x;
        this.z = z;
        // Profile
        num++;
    }

    public static int getSize() {
        return size;
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

    public int getRlBiome() {
        return rlBiome;
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
}
