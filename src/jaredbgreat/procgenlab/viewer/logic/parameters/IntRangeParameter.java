package jaredbgreat.procgenlab.viewer.logic.parameters;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Jared Blackburn
 */
public class IntRangeParameter implements IParameter {
    public static final ParameterType type = ParameterType.IRANGE;
    public static final String typeName = "irange";
    
    final JTextField widget;
    final JLabel label;
    final String name;

    
    IntRangeParameter(String def) {
        widget = new JTextField();
        label = new JLabel();
        name = "";
    }
    // TDOD: Use simple text field? Or text field / slider combination?
    
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
