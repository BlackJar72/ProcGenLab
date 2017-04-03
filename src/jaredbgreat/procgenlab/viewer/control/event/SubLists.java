package jaredbgreat.procgenlab.viewer.control.event;

import java.util.HashMap;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */



/**
 *
 * @author Jared Blackburn
 */
public final class SubLists {
    private static final HashMap<String, SubscriptionList> channels 
            = new HashMap<>();
    
    /**
     * This will return the the requested subscription list.  In no such 
     * list exists then i will be created.
     * 
     * @param Name
     * @return 
     */
    public static final SubscriptionList getList(String Name) {
        if(channels.containsKey(Name)) {
            return channels.get(Name);
        } else {
            SubscriptionList out = new SubscriptionList();
            channels.put(Name, out);
            return out;
        }
    }
    
    
    /**
     * This will remove the given list the set of lists.
     * 
     * Note that this is dangerous, and other objects may still try to access 
     * this by names, leading to a null pointer exception.  This is not caught 
     * and handled in th is class since such an exception is sign of an error 
     * or unsafe practice else where that should be caught and fixed rather 
     * than silently let slide.
     * 
     * @param name 
     */
    public static final void remove(String name) {
        channels.remove(name);
    }
    
    
    /**
     * This will subscribe a listener to the named subscription list, and return
     * the list for possible caching as a recipient.
     * 
     * @param sub
     * @param name
     * @return 
     */
    public static final SubscriptionList subscribe(IControlListener sub, 
            String name) {
        SubscriptionList out = getList(name);
        out.subscribe(sub);
        return out;
    }
    
    
    /**
     * This will sent the event to the named list.
     * 
     * @param name
     * @param event 
     */
    public static final void send(String name, ControlEvent event) {
        channels.get(name).recieveEvent(event);
    }
    
    
    /**
     * This will send the event to all names in the listed after the event.
     * 
     * @param event
     * @param names 
     */
    public static final void send(ControlEvent event, String... names) {
        for(String name : names) {
            channels.get(name).recieveEvent(event);
        }
    }
}
