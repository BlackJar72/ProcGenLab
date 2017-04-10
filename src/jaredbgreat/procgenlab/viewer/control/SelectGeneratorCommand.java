package jaredbgreat.procgenlab.viewer.control;

import jaredbgreat.procgenlab.registries.Registrar;
import jaredbgreat.procgenlab.viewer.MainWindow;
import jaredbgreat.procgenlab.viewer.SidePanel;
import javax.swing.JComboBox;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

/**
 *
 * @author Jared Blackburn
 */
public class SelectGeneratorCommand implements ICommand {
    private static JComboBox source;

    @Override
    public void execute() {
        String selected = (String)source.getSelectedItem();
        if((selected != null) && !selected.isEmpty()) {
            SidePanel panel = (SidePanel)MainWindow.getComponenent("SidePanel");
            panel.setControls(Registrar.getRegistrar().getParameters(selected));
        }
    }
    
    
    public static void setSource(JComboBox src) {
        source = src;
    }
    
}
