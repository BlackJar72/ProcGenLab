package jaredbgreat.procgenlab.generators.chunkyregion.chunk;

/*
 * DO NOT ACTUALLY USE THIS, CHANGE TO REGION AT ONCE STRATEGY (NoiseRegion)!
 */

import java.util.Random;

/**
 *
 * @author Jared Blackburn
 */
public class RiverMaker {
    private final ChunkMaker  map;
    private final Random rand;
    //private final BasinNode[] starts, ends;
    //private final int num;
    
    public RiverMaker(ChunkMaker mapIn, long seed) {
        map = mapIn;
        rand = new Random(seed);
        //num = rand.nextInt(3) + 5;  
        //starts = map.getBasins(num, true);
        //ends = map.getBasins(num, false);      
    }
    
    public void build() {
        /*for(int i = 0; i < num; i++) {
            //River river = new River(starts[i], ends[i], map);
            //river.build(rand);
        }*/
    }
}
