package jaredbgreat.procgenlab.viewer.logic.parameters;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

import static jaredbgreat.procgenlab.api.Delims.SGS;
import static jaredbgreat.procgenlab.api.Delims.SRS;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 *
 * @author Jared Blackburn
 */
public class BooleanParameter implements IParameter {
    public static final ParameterType type = ParameterType.BOOL;
    public static final String typeName = type.name().toLowerCase();
    
    final JCheckBox widget;
    final JLabel label;
    final String name;
    private Object delims;

    
    BooleanParameter(String name) {
        this.name = name;
        widget = new JCheckBox(name);
        widget.setEnabled(true);
        label = new JLabel(name);
        label.setLabelFor(widget);
        label.setText(name);
    }
    
    
    @Override
    public JComponent getComponent() {
        return widget;
    }

    @Override
    public String getSetting() {
        return typeName + SRS + name 
                + SRS + Boolean.toString(widget.isSelected()) + SGS;
    }

    @Override
    public JLabel getLabel() {
        return label;
    }

    @Override
    public void set(String setting) {
        widget.setSelected(Boolean.valueOf(setting));
    }
    
}
