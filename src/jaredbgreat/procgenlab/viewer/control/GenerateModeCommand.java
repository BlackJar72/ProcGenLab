package jaredbgreat.procgenlab.viewer.control;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

import jaredbgreat.procgenlab.viewer.MainWindow;
import jaredbgreat.procgenlab.viewer.TopPanel;

/**
 *
 * @author jared
 */
public class GenerateModeCommand implements ICommand {

    @Override
    public void execute() {
        ((TopPanel)MainWindow.getComponenent("TopPanel")).viewMode();
    }
    
}
