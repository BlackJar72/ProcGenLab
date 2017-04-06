package jaredbgreat.procgenlab.viewer.logic.parameters;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;

/**
 *
 * @author jared
 */
public class RealRangeParameter implements IParameter {
    public static final ParameterType type = ParameterType.RRANGE;
    public static final String typeName = "range";
    
    final JSlider widget;
    final JLabel label;
    final String name;

    
    RealRangeParameter(String def) {
        widget = new JSlider();
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
    public void setup(String definition) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JLabel getLabel() {
        return label;
    }
    
}
