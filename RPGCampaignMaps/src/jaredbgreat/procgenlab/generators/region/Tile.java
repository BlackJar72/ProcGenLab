/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaredbgreat.procgenlab.generators.region;

/**
 *
 * @author jared
 */
public class Tile {
    final int x, y;
    int val = 0, rlBiome = 0;
    int temp = 0, wet = 0, fakeheight = 0;
    int biome = 0;
    boolean isMountain = false, isHilly = false;
    
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
