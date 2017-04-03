package jaredbgreat.procgenlab.viewer.control.event;

import java.util.ArrayList;

/**
 *
 * @author Jared Blackburn
 */
public class SubscriptionList implements IControlListener {
    private final ArrayList<IControlListener> subs;
    
    public SubscriptionList() {
        subs = new ArrayList<>();
    }

    @Override
    public void recieveEvent(ControlEvent event) {
        for(IControlListener sub : subs) {
            sub.recieveEvent(event);
        }
    }
    
    
    public void subscribe(IControlListener subscriber) {
        if(!subs.contains(subscriber)) {
            subs.add(subscriber);
        }
    }
    
    
    public void remove(IControlListener subscriber) {
        subs.remove(subscriber);
    }    
}
