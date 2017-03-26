package jaredbgreat.procgenlab.viewer.control;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

import jaredbgreat.procgenlab.viewer.logic.RandomHelper;
import javax.swing.JTextField;

/**
 *
 * @author Jared Blackburn
 */
class GenerateCommand implements ICommand {
    private JTextField seedbox;
    
    GenerateCommand(JTextField input) {
        seedbox = input;
    }

    @Override
    public void execute() {
        RandomHelper.getSeedFromText(seedbox.getText());
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
