package jaredbgreat.procgenlab.viewer.control.event;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

import java.awt.event.ActionEvent;
import java.util.EventObject;
import javax.swing.JComponent;

/**
 *
 * @author Jared Blackburn
 */
public class ControlEvent extends EventObject {
    private final String payload;

    public ControlEvent(String msg, Object source) {
        super(source);
        payload = msg;
    }
    
    
    public String  getMsg() {
        return payload;
    }
    
    
    public int getInt() {
        return Integer.getInteger(payload);
    }
    
    
    public double getNumber() {
        return Integer.getInteger(payload);
    }
    
    
    private ActionEvent getActionEvent() {
        if(source instanceof ActionEvent) {
            return (ActionEvent)source;
        } else {
            return null;
        }
    }
    
    
    public JComponent getActionComponent() {
        ActionEvent action = getActionEvent();
        if(action == null) {
            return null;
        } else if(action.getSource() instanceof JComponent) {
            return (JComponent) action.getSource();
        } else {
            return null;
        }
    }
    
    
    public String getActionCommand() {
        ActionEvent action = getActionEvent();
        if(action == null) {
            return null;
        } else {
            return action.getActionCommand();
        }
    }
    
    
    @Override
    public String toString() {
        return (super.toString() + " (\"" + payload + "\")");
    }
}
