package jaredbgreat.procgenlab.viewer.logic.parameters;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

import static jaredbgreat.procgenlab.util.Delims.SGS;
import static jaredbgreat.procgenlab.util.Delims.SRS;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Jared Blackburn
 */
public class StringParameter implements IParameter {
    public static final ParameterType type = ParameterType.STRING;
    public static final String typeName = type.name().toLowerCase();
    
    final JTextField widget;
    final JLabel label;
    final String name;

    
    StringParameter(String name) {
        widget = new JTextField(name);
        widget.setEnabled(true);
        widget.setEditable(true);
        label = new JLabel(name + "Label");
        label.setLabelFor(widget);
        this.name = name;
    }
    
    @Override
    public JComponent getComponent() {
        return widget;
    }

    @Override
    public String getSetting() {
        return typeName + SRS + name + SRS +widget.getText() + SGS;
    }

    @Override
    public JLabel getLabel() {
        return label;
    }
    
}
