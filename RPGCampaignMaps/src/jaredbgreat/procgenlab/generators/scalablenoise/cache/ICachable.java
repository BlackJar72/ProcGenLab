package jaredbgreat.procgenlab.generators.scalablenoise.cache;

public interface ICachable {	
	
	/**
	 * Updates last used time.
	 */
	public void use();
	/**
	 * Is this ready to be removed?
	 * 
	 * @return
	 */
	public boolean isOldData();
	/**
	 * Was this an old item returned from the cache.
	 * 
	 * @return
	 */
	public boolean isCached();
        /**
         * Return the MutableCoords for this object.
         */
        public MutableCoords getCoords();
	/**
	 * Mark whether this is in the cache.
	 */
	public void setCached(Boolean wasCached);
	/**
	 * Return the Coords object for this object.
	 * 
	 * @return
	 */
}
