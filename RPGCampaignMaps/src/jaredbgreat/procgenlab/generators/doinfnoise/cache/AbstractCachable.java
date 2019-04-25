package jaredbgreat.procgenlab.generators.doinfnoise.cache;

/**
 * Something a base class for thing that can be stored in this cashe system.
 * 
 * @author Jared Blackburn
 */
public abstract class AbstractCachable implements ICachable { 
    private MutableCoords coords;
    private long timestamp;
    private boolean cached;
    
    
    public AbstractCachable() {
    	cached = false;
        coords = new MutableCoords();
        timestamp = System.currentTimeMillis();
    }
    
    
    public AbstractCachable(int x, int z) {
    	cached = false;
        coords = new MutableCoords().init(x, z);
        timestamp = System.currentTimeMillis();
    }
    
    
    @Override
    public void use() {
            timestamp = System.currentTimeMillis();		
    }
    
    
    @Override
    public boolean isOldData() {
            long t = System.currentTimeMillis() - timestamp;
            return ((t > 1000) || (t < 0));	
    }
    
    
    @Override
    public boolean isCached() {
            return cached;
    }
    
    
    @Override
    public MutableCoords getCoords() {
        return coords;
    }
        
        
    @Override
    public void setCached(Boolean wasCached) {
            cached = wasCached;
    }
    
}
