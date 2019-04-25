package jaredbgreat.procgenlab.generators.doinfnoise.cache;

/**
 * A so far unused memory pool system for IChacheable objects.  In theory this 
 * would reduce or prevent garbage collection and new allocations.  Instead, 
 * old objects are saved and later re-initialized and recycled.
 * 
 * This may or may not ever be used, but for now its being preserved.
 * 
 * @author Jared Blackburn
 * @param <T>
 */
public class CachedPool<T extends ICachable>  {
    private final ObjectStack<T>      stack;
    private final ObjectFactory<T> factory;
    private final Cache<T> cache;
    
    
    @SuppressWarnings({ "unchecked", "rawtypes" }) 
    public CachedPool(ObjectFactory<T> fact, int poolSize, int initCacheSize) {
        factory = fact;
        stack = new ObjectStack(poolSize);
	cache = new Cache<>(initCacheSize);
    }
    
    
    /**
     * The method used to retrieve or create an object (depending on whether an 
     * object is in the pool).
     * 
     * @return 
     */
    public T getEntry(MutableCoords c) {
        T out;
    	if((out = cache.get(c)) != null) {
            return out;
    	} else {
            if(stack.isEmpty()) {
                out = factory.create();
                out.setCached(false);
                return out;
            } else {
                return stack.pop();
            }
    	}
    }
    
    
    /**
     * This will add an entry to the cache.
     * 
     * @param entry 
     */
    public void add(T entry) {
            entry.setCached(true);
            cache.add(entry);
    }
    
    
    /**
     * This will add an entry to the cache while setting its coords.  
     * 
     * This should not be used; set the entry's coords where it is acquired 
     * instead.
     * 
     * @param entry
     * @param c
     * @deprecated
     */
    @Deprecated
    public void add(T entry, MutableCoords c) {
            entry.setCached(true);
            entry.getCoords().init(c.getX(), c.getZ());
            cache.add(entry);
    }
    
    
    /**
     * This will add an entry to the cache while setting its coords.  
     * 
     * This should not be used; set the entry's coords where it is acquired 
     * instead.
     * 
     * @param entry
     * @param c
     * @deprecated
     */
    @Deprecated
    public void add(T entry, int x, int z) {
            entry.setCached(true);
            entry.getCoords().init(x, z);
            cache.add(entry);
    }

    
    /**
     * The method for placed objects in the pool for storage.  This should be 
     * used after the object in question is no longer going to be in use, much 
     * like calling delete on a (non-pooled / non-managed) C++ object.
     * 
     * @param object 
     */
    private void free(T object) {
        stack.push(object);
    }
	
	
	public void clean() {
            cache.cleanup();
        }
    
    
    //**********************************************************************************************************/
    //                              Inner Classes barrowed from ObjectPool                                      /
    //**********************************************************************************************************/
    
    /**
     * An interface for creating a factory that is used to create objects when 
     * none are available in the pool.
     * 
     * @param <T> 
     */
    public interface ObjectFactory<T> {
        T create();
    }
    
    
    /**
     * A simple stack structure for storing the pooled objects.
     * 
     * @param <T> 
     */
    private class ObjectStack<T extends ICachable> {
        final Object[] data;
        int pos;
        public ObjectStack(int size) {
            pos = 0;
            data = new Object[size];
        }
        void push(T in) {
            in.setCached(false);
            if(pos < data.length) {
                data[pos++] = in;
            }
        }
        @SuppressWarnings({ "unchecked", "rawtypes" }) 
        T pop() {
            T out = (T)data[--pos];
            data[pos] = null;
            return out;
        }
        boolean isEmpty() {
            return pos == 0;
        }
        boolean isFull() {
            return pos == data.length;
        }
    }
	

}
