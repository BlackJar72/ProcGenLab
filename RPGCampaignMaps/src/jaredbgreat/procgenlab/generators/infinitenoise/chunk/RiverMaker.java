package jaredbgreat.procgenlab.generators.infinitenoise.chunk;

import java.util.Random;

/**
 *
 * @author Jared Blackburn
 */
public class RiverMaker {
    private final Map  map;
    private final Random rand;
    private final BasinNode[] starts, ends;
    private final int num;
    
    public RiverMaker(Map mapIn, long seed) {
        map = mapIn;
        rand = new Random(seed);
        num = rand.nextInt(3) + 5;  
        starts = map.getBasins(num, true);
        ends = map.getBasins(num, false);      
    }
    
    public void build() {
        for(int i = 0; i < num; i++) {
            River river = new River(starts[i], ends[i], map);
            river.build(rand);
        }
    }
}
