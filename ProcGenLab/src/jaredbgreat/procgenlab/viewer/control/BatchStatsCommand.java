package jaredbgreat.procgenlab.viewer.control;

import jaredbgreat.procgenlab.api.IGenerator;
import jaredbgreat.procgenlab.registries.Registrar;
import jaredbgreat.procgenlab.viewer.GeneratorPanel;
import jaredbgreat.procgenlab.viewer.MainWindow;
import jaredbgreat.procgenlab.viewer.TopPanel;
import jaredbgreat.procgenlab.viewer.ViewPanel;
import jaredbgreat.procgenlab.viewer.logic.StatsHelper;
import jaredbgreat.procgenlab.viewer.logic.WorldMap;
import jaredbgreat.procgenlab.viewer.logic.parameters.IParameter;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

/**
 *
 * @author jared
 */
public class BatchStatsCommand extends GenerateCommand implements ICommand {
	private static JComboBox<String> catSource;

    @Override
    public void execute() {
        int trials = Integer.valueOf(((JTextField)MainWindow
                .getComponenent("Seedbox")).getText());
        IGenerator generator 
                = Registrar.getRegistrar((String)catSource.getSelectedItem())
                        .getGenerator(selection.getSelectedItem().toString());
        if(generator == null) {
            ((JTextField)MainWindow.getComponenent("ProfileTimeBox"))
                    .setText("*(No Such Generator)*");
            return;
        }
        long[] data = new long[trials];
        for(int i = 0; i < trials; i++) {
            long seed = System.nanoTime();
            generator.setParameters(getArgumentString());
            data[i] = System.nanoTime();
            generator.generate(seed);
            data[i] = System.nanoTime() - data[i];            
        }
        ((JTextField)MainWindow.getComponenent("ProfilingTimeBox")).setText("");
        ViewPanel view = (ViewPanel)MainWindow.getComponenent("ViewPanel");
        view.setStatsView(new StatsHelper(data));
        
    }
    
    
    public static void setCatSource(JComboBox<String> cat) {
    	catSource = cat;
    }
    
}
