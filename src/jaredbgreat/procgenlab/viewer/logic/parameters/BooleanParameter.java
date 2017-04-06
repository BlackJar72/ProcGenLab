package jaredbgreat.procgenlab.viewer.logic.parameters;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 *
 * @author Jared Blackburn
 */
public class BooleanParameter implements IParameter {
    public static final ParameterType type = ParameterType.BOOL;
    public static final String typeName = "boolean";
    
    final JCheckBox widget;
    final JLabel label;
    final String name;

    
    BooleanParameter(String def) {
        widget = new JCheckBox();
        label = new JLabel();
        name = "";
    }
    
    
    @Override
    public JComponent getComponent() {
        return widget;
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
