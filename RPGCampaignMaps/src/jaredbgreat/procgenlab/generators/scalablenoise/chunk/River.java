package jaredbgreat.procgenlab.generators.scalablenoise.chunk;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;

/**
 * @author Jared Blackburn
 */
public class River {
    final int MAX;
    MapMaker map;
    BasinNode begin, end;
    int id, x, z, oc, dstx, dstz;
    final Deque<ChunkTile> Q;
    SizeScale scale;
    boolean merged;
    ChunkTile[] possible;
    
    
    public River(int riverId, BasinNode high, BasinNode low, MapMaker mapIn, SizeScale sc) {
        id = riverId;
        scale = sc;
        x = high.x * sc.whole;
        z = high.y * sc.whole;
        dstx = low.x * sc.whole;
        dstz = low.y * sc.whole;
        MAX = (MapMaker.RSIZE * sc.whole) - 2;
        Q = new ArrayDeque<>();
        merged = false;
        map = mapIn;
        begin = high;
        end = low;
        possible = new ChunkTile[8];
    }
    
    
    private double findLength(double x1, double y1, double x2, double y2) {
        double difX = x1 - x2;
        double difY = y1 - y2;
        return Math.sqrt((difX * difX) + (difY * difY));
    }
    
    
    public void build(Random r) {
        ChunkTile t = map.getTile(x, z);
        while(!shouldEnd(t)) {
            t.river = id;
            Q.add(t);
            t = findNext(t, r);
        }
        for(ChunkTile tr : Q) {
            makeRiver(tr);
        }
    }
    
    private ChunkTile findNext(ChunkTile t, Random r) {
        int n = 0;
        possible[0] = null;
        if(testIfUsed(map.getTile(t.x + 1, t.z + 1) , n)) n++;
        if(testIfUsed(map.getTile(t.x + 1, t.z), n)) n++;
        if(testIfUsed(map.getTile(t.x + 1, t.z - 1), n)) n++;
        if(testIfUsed(map.getTile(t.x, t.z + 1), n)) n++;
        if(testIfUsed(map.getTile(t.x, t.z - 1), n)) n++;
        if(testIfUsed(map.getTile(t.x - 1, t.z + 1), n)) n++;
        if(testIfUsed(map.getTile(t.x - 1, t.z), n)) n++;
        if(testIfUsed(map.getTile(t.x - 1, t.z - 1), n)) n++;
        ChunkTile out = possible[0];
        for(int i = 1; i < n; i++) {
            if(getAdjusted(possible[i]) > getAdjusted(out)) out = possible[i];
        }
        if(out == null) {
            out = map.getTile(t.x + r.nextInt(3) - 1, t.z + r.nextInt(3) - 1);
        }
        return out;
    }
    
    private double getAdjusted(ChunkTile t) {
        return (Math.sqrt(((t.x - dstx) * (t.x - dstx))
                       + ((t.z - dstz) * (t.z - dstz))) / (20.0 * scale.fract)) 
                - t.height;
    }
    
    
    private boolean testIfUsed(ChunkTile t, int n) {
        boolean out = !(t.river == id);
        if(out) possible[n] = t;
        return out;
    }
    
    
    private boolean shouldEnd(ChunkTile t) {
        if(merged || (t == null)) {
            return true;
        } else {
            if(t.height < 0.5) {
                oc++;
            } else {
                oc = 0;
            }
            merged = (t.river > 0) && (t.river != id); 
            return ((t.height < 0.5) && (oc > (8 * scale.whole))) 
                    || outOfBounds(t.x, t.z);
        }
    }
    
    
    private boolean outOfBounds(int x, int z) {        
        return (x <= 1) || (z <= 1) 
                ||(x >= MAX) || (z >= MAX);
    }
    
    
    private void makeRiver(ChunkTile t) {
        map.getTile(t.x + 1, t.z).river = id;
        map.getTile(t.x + 1, t.z + 1).river = id;
        map.getTile(t.x, t.z + 1).river = id;
        t.rlBiome = 3;
        map.getTile(t.x + 1, t.z).rlBiome = 3;
        map.getTile(t.x + 1, t.z + 1).rlBiome = 3;
        map.getTile(t.x, t.z + 1).rlBiome = 3;          
    }
    
}
