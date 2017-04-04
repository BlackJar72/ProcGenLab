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
public class GenerateCommand implements ICommand {
    private static JTextField seedbox;

    @Override
    public void execute() {
        long seed = RandomHelper.getSeedFromText(seedbox.getText());
        System.out.println("Seed = " + seed);
        //TDOD: Call a generator!
    }
    
    
    public static void setSeedbox(JTextField input) {
        seedbox = input;
    }
    
}
