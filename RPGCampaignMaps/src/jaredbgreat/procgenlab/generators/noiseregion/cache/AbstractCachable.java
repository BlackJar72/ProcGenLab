/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaredbgreat.procgenlab.generators.noiseregion.cache;

/**
 *
 * @author jared
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
