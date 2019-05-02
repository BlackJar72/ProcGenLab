package jaredbgreat.procgenlab.viewer.logic.parameters;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

import static jaredbgreat.procgenlab.api.Delims.SGS;
import static jaredbgreat.procgenlab.api.Delims.SRS;
import static jaredbgreat.procgenlab.api.Delims.SUS;
import java.util.StringTokenizer;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSlider;

/**
 *
 * @author Jared Blackburn
 */
public class IntRangeParameter implements IParameter {
    public static final ParameterType type = ParameterType.RANGE;
    public static final String typeName = type.name().toLowerCase();
    
    final JSlider widget;
    final JLabel label;
    final String name;

    
    IntRangeParameter(String name, String definition) {
        widget = new JSlider();
        setupSlider(definition);
        widget.setEnabled(true);
        label = new JLabel(name);
        label.setLabelFor(widget);
        this.name = name;
    }
    
    
    private void setupSlider(String definition) {
        StringTokenizer tokens = new StringTokenizer(definition, SUS);
        widget.setMinimum(Integer.valueOf(tokens.nextToken()));
        widget.setMaximum(Integer.valueOf(tokens.nextToken()));
    }
    
    
    @Override
    public JComponent getComponent() {
        return widget;
    }

    @Override
    public String getSetting() {
        return typeName + SRS + name + SRS + ((int)widget.getValue()) + SGS;
    }

    @Override
    public JLabel getLabel() {
        return label;
    }

    @Override
    public void set(String setting) {
        widget.setValue(Integer.valueOf(setting));
    }
    
}
