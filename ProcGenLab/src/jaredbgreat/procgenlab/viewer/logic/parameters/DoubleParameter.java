package jaredbgreat.procgenlab.viewer.logic.parameters;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

import static jaredbgreat.procgenlab.api.Delims.SGS;
import static jaredbgreat.procgenlab.api.Delims.SRS;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Jared Blackburn
 */
public class DoubleParameter implements IParameter {
    public static final ParameterType type = ParameterType.DOUBLE;
    public static final String typeName = type.name().toLowerCase();
    
    final JTextField widget;
    final JLabel label;
    final String name;

    
    DoubleParameter(String name) {
        widget = new JTextField(name);
        widget.setText("0.0");
        widget.setEditable(true);
        widget.setEnabled(true);
        widget.setHorizontalAlignment(JTextField.TRAILING);
        label = new JLabel(name);
        label.setLabelFor(widget);
        this.name = name;
    }
    
    @Override
    public JComponent getComponent() {
        return widget;
    }

    @Override
    public String getSetting() {
        try {
            return typeName + SRS + name + SRS 
                    + Double.valueOf(widget.getText()) + SGS;
        } catch (NumberFormatException e) {
            //TODO: A better way to report errors, shoing something in the GUI
            System.err.println("ERROR: Invalid format for parameter "
                    + name + " of type " + typeName + "; using 0.0.");
            return typeName + SRS + name + SRS 
                    + "0.0" + SGS;            
        }
    }

    @Override
    public JLabel getLabel() {
        return label;
    }

    @Override
    public void set(String setting) {
        widget.setText(setting);
    }
    
}
