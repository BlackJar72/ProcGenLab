package jaredbgreat.procgenlab.viewer.logic.parameters;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

import javax.swing.JComponent;

/**
 *
 * @author Jared Blackburn
 */
public interface IParameter {
    JComponent getComponent();
    String getSetting();
    void setup(String definition);    
}
