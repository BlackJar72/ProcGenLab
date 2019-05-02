package jaredbgreat.procgenlab.registries;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * May be used on its own, but most likely to be used as base class for more 
 * specific registry types(?) that add extra functionality specific for their
 * own use.
 * 
 * @author Jared Blackburn
 * @param <T>
 */
public class Registry<T> implements Iterable<T> { 
    
    private final class Entry {
        final T item;
        final String name;
        final int index;
        Entry(String name, T item, int index) {
            this.name  = name;
            this.item  = item;
            this.index = index;
        }
        @Override
        public String toString() {
            return name;
        }
        @Override
        public int hashCode() {
            return name.hashCode();
        }
        @SuppressWarnings("unchecked")
		@Override
        public boolean equals(Object other) {
            if(other.getClass() != this.getClass()) return false;
            return name.equals(((Entry)other).name);
        }
    }    
    
    private List<T> list; // Must contain T, not Entry, to use iterators
    private Map<String, Entry> directory;
    private int n = 0;

    
    public Registry() {
        list = new ArrayList<>();
        directory = new HashMap<>();
    }
    
    
    /**
     * Adds a named item to the registry.
     * 
     * @param name
     * @param item
     * @return true if added, false if not
     */
    public boolean add(String name, T item) {
        if(!directory.containsKey(name)) {
            list.add(n, item);
            directory.put(name, new Entry(name, item, n));
            n++;
            return true;
        }
        return false;
    }
    
    
    /**
     * Removes the item named from the registry.
     * 
     * @param name
     * @return true if successful, false if the name was not in the registry.
     */
    public boolean remove(String name) {
        if(directory.containsKey(name)) {
            list.remove(directory.get(name));
            directory.remove(name);
            n--;
            return true;
        }
        return false;
    }
    
    
    /**
     * Gets a stored object by its name.
     * 
     * @param name
     * @return the object named.
     */
    public T get(String name) {
        return directory.get(name).item;
    }
    
    
    /**
     * Gets a stored object by its index.
     * 
     * @param index
     * @return the object holding that index in the list
     */
    public T get(int index) {
        return list.get(index);
    }
    
    
    /**
     * Gets the index corresponding the name.
     * 
     * @param name
     * @return the list index of the named object
     */
    public int getIndex(String name) {
        return directory.get(name).index;
    }
    
    
    /**
     * Return the number of entries in the registry
     * 
     * @return number of entries
     */
    public int size() {
        return n;
    }

    
    /**
     * Tells if the registry is empty, that is, contains no entries.
     * 
     * @return true if empty, false if not empty
     */
    public boolean isEmpty() {
        return (n <= 0);
    }

    
    /**
     * Gets a list iterator for the registry.
     * 
     * @return 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public Iterator iterator() {
        return list.iterator();
    }  
    
    
    /**
     * Is an item named by key present in the registry.  If key is anything
     * but a String this will return false.  If key is a string it will
     * return true if the it matches a name used as a key in the registry.
     * 
     * @param key
     * @return true if key names an entry, false in not
     */
    public boolean containsKey(Object key) {
        return directory.containsKey(key);
    }
    
    /**
     * Returns the contents of the list as an array.
     * 
     * @return an array containing the items in the registry.
     */
    @SuppressWarnings("unchecked")
	public T[] toArray() {
        return (T[])list.toArray();
    }
    
    
    /**
     * Returns an array containing the items in the registry.
     * 
     * @param in
     * @return an array containing the items in the registry
     */
    public T[] toArray(T[] in) {
        return list.toArray(in);
    }
    
    
    /**
     * Returns a copy of the list.
     * 
     * @return a the data as a clones list.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> getListCopy() {
        return (List<T>) ((ArrayList)list).clone();
    }
    
    
    /**
     * Returns a reference to the internal list representation.
     * 
     * @return a reference to the actual internal list.
     */
    public List<T> getList() {
        return list;
    }
    
    
    /**
     * This will add the object (value) named by the key to to the registry,
     * and return the added object regardless of if its was actually added,
     * already present, or if the addition failed.
     * 
     * @param key
     * @param value
     * @return 
     */
    public T put(String key, T value) {
        add(key, value);
        return value;
    }

    
    /**
     * Removes all entries from the registry.
     */
    public void clear() {
        list.clear();
        directory.clear();
        n = 0;
    } 
}
