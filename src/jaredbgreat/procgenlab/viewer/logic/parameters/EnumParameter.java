package jaredbgreat.procgenlab.viewer.logic.parameters;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

import javax.swing.JComboBox;
import javax.swing.JComponent;

/**
 * This represents a finite set of predefined options, each represented by a
 * string, and selected with a combo box menu (JComboBox specifically).  It may 
 * or may not correspond to an enum in the generators code, but is called that
 * due to the obvious similarities (which could also make an emum the logical 
 * choice for storing such selection within a generator).
 * 
 * To create one requires a number options followed by text represent those
 * options.
 * 
 * @author jared
 */
public class EnumParameter implements IParameter {
    JComboBox widgit;

    @Override
    public JComponent getComponent() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getSetting() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setup(String definition) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
