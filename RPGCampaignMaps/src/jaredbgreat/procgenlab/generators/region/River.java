package jaredbgreat.procgenlab.generators.region;


import java.util.Random;

/**
 * @author Jared Blackburn
 */
public class River {
    Map map;
    BasinNode begin, end;
    double dx, dy;
    double angle, da;
    double cx, cy, rx, ry;
    AS s;
    
    private enum AS {
        P2 (7,   0),
        P1 (9,  -3),
        Z0 (11, -5),
        N1 (-9,  3),
        N2 (-7,  0);        
        public double a, b;
        private static final AS[] vals = values();
        AS(double a, double b) {
            this.a = a;
            this.b = b;
        }
        public static AS getRandom(Random r) {
            return vals[r.nextInt(vals.length)];
        }
        public AS shift(Random r) {
            int v = this.ordinal() + (r.nextInt(3)) - 1;
            if(v < 0) {
                v = 1;
            } else if(v >= vals.length) {
                v = vals.length - 1;
            }
            return vals[v];
        }
    }
    
    public River(BasinNode high, BasinNode low, Map mapIn) {
        map = mapIn;
        begin = high;
        end   = low;        
        da = angle = 0; 
        cx = begin.x;
        cy = begin.y;
        double length = findLength(cx, cy, end.x, end.y);
        dx = (end.x - begin.x) / length;
        dy = (end.y - begin.y) / length;
        s = AS.Z0;
    }
    
    private double findLength(double x1, double y1, double x2, double y2) {
        double difX = x1 - x2;
        double difY = y1 - y2;
        return Math.sqrt((difX * difX) + (difY * difY));
    }
    
    public void build(Random r) {
        double l, das, dac, f, p;
        do {
            // TODO: Optimize: Keep angle in Radians, don't convert 
            das = Math.sin(Math.toRadians(angle)); 
            l = Math.sqrt((das * das) + 1);
            f = 1 / l;
            p = das / l;
            incrementAngle(r);
            cx += (f * dx) + (p * dy);
            cy += (f * dy) + (p * dx);
            // TODO: Apply changes to the map
        } while(!shouldEnd((int)cx, (int)cy));
    }
    
    private boolean shouldEnd(int x, int y) {
        Tile t = map.getTile(x, y);
        if(t == null) {
            return true;
        } else {
            // This will be true if the biome is water
            return t.rlBiome < 3;
        }
    }
    
    private void incrementAngle(Random r) {        
        angle += da;
        da += (s.a * r.nextDouble()) + s.b;
        if(r.nextInt(10) == 0) {
            if(r.nextBoolean()) {
                s = AS.getRandom(r);
            } else {
                s = s.shift(r);
            }
        }        
    }
    
}
