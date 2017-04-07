package jaredbgreat.procgenlab.viewer.logic.parameters;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * This represents a finite set of predefined options, each represented by a
 * string, and selected with a combo box menu (JComboBox specifically).  It may 
 * or may not correspond to an enum in the generators code.
 * 
 * To create one requires a number options followed by text represent those
 * options.
 * 
 * @author jared
 */
public class MultiselectParameter implements IParameter {
    public static final ParameterType type = ParameterType.MULTI;
    public static final String typeName = "multiselect";
    
    final JComboBox widget;
    final JLabel label;
    final String name;

    
    MultiselectParameter(String def) {
        widget = new JComboBox();
        label = new JLabel();
        name = "";
    }

    @Override
    public JComponent getComponent() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getSetting() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JLabel getLabel() {
        return label;
    }
    
}
