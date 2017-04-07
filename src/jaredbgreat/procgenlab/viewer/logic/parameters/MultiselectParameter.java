package jaredbgreat.procgenlab.viewer.logic.parameters;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

import static jaredbgreat.procgenlab.util.Delims.SGS;
import static jaredbgreat.procgenlab.util.Delims.SRS;
import static jaredbgreat.procgenlab.util.Delims.SUS;
import java.util.StringTokenizer;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;

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
    public static final String typeName = type.name().toLowerCase();
    
    final JComboBox widget;
    final JLabel label;
    final String name;

    
    MultiselectParameter(String name, String fields) {
        widget = new JComboBox();
        setupWidget(fields);
        widget.setEditable(false);
        widget.setEnabled(true);
        label = new JLabel(name + "Label");
        label.setLabelFor(widget);
        this.name = name;
    }
    
    
    private void setupWidget(String fields) {
        StringTokenizer tokens = new StringTokenizer(fields, SUS);
        while(tokens.hasMoreTokens()) {
            widget.addItem(tokens.nextToken());
        }
    }
    

    @Override
    public JComponent getComponent() {
        return widget;
    }

    @Override
    public String getSetting() {
        return typeName + SRS + name + SRS 
                + widget.getSelectedItem().toString() + SGS;
    }

    @Override
    public JLabel getLabel() {
        return label;
    }
    
}
