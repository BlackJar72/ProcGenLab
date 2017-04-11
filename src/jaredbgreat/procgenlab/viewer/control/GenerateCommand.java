package jaredbgreat.procgenlab.viewer.control;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

import jaredbgreat.procgenlab.api.IGenerator;
import jaredbgreat.procgenlab.registries.Registrar;
import static jaredbgreat.procgenlab.api.Delims.SFS;
import jaredbgreat.procgenlab.viewer.MainWindow;
import jaredbgreat.procgenlab.viewer.ViewPanel;
import jaredbgreat.procgenlab.viewer.logic.RandomHelper;
import jaredbgreat.procgenlab.viewer.logic.WorldMap;
import jaredbgreat.procgenlab.viewer.logic.parameters.IParameter;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 *
 * @author Jared Blackburn
 */
public class GenerateCommand implements ICommand {
    private static final long  MILLE       = 1000000;
    private static final float FLOAT_MILLE = 1000000f;
    private static final long  UNIT        = 1000000000;
    private static final float FLOAT_UNIT  = 1000000000f;
    
    private static JTextField seedbox;
    private static JTextField timebox;
    private static JComboBox  selection;
    private static List<IParameter> parameters;

    @Override
    public void execute() {
        long seed = RandomHelper.getSeedFromText(seedbox.getText());
        if((selection == null) || (selection.getSelectedItem() == null)) {
            timebox.setText("*(ERROR: no generators!)*");  
            return;
        }
        IGenerator generator 
                = Registrar.getRegistrar()
                        .getGenerator(selection.getSelectedItem().toString());
        if(generator == null) {
            timebox.setText("*(No Such Generator)*");
            return;
        }
        generator.setParameters(getArgumentString());
        long time = System.nanoTime();
        generator.generate(seed);
        time = System.nanoTime() - time;
        String timeString;
        if(time < MILLE) {
            timeString = time + " ns";
        } else if(time < UNIT) {
            timeString = (((float)time) / FLOAT_MILLE) + " ms";
        } else {
            timeString = (((float)time) / FLOAT_UNIT) + " s";
        }
        timebox.setText(timeString);
        ViewPanel view = (ViewPanel)MainWindow.getComponenent("ViewPanel");
        view.setWorldMap(new WorldMap(generator));
    }
    
    
    /**
     * This sets the JTextBox from which the provided 
     * seed (if any) should be read.  This should only  
     * be called by the GUI's TopPanel to attach its 
     * widget to this command.
     * 
     * @param input 
     */
    public static void setSeedbox(JTextField input) {
        seedbox = input;
    }
    
    
    /**
     * This sets the JTextBox into which the time taken 
     * to complete generation should be written.  This should 
     * only be called by the GUI's TopPanel instance in order 
     * to link its widget to this command.
     * 
     * @param input 
     */
    public static void setTimebox(JTextField input) {
        timebox = input;
    }
    
    
    /**
     * This set the JComboBox to use as the generator 
     * selector.  This should only be called by the GUI 
     * internally (specifically the TopPanel) to link the 
     * GUI widget to the this command.
     * 
     * @param input 
     */
    public static void setSelector(JComboBox input) {
        selection = input;
    }
    
    
    /**
     * Set the parameter list.  This should be called by the GUI 
     * combo box of generators whenever a new generator is selected.
     * 
     * @param input 
     */
    public static void setParameters(List<IParameter> input) {
        parameters = input;
    }
    
    
    /**
     * This will go through the array of parameters, extract the 
     * data string from each, concatenate them into a single 
     * String that can be returned to the IGenerator object.
     * 
     * @return 
     */
    private String getArgumentString() {
        if(parameters == null || parameters.isEmpty()) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        parameters.stream().forEach((param) -> {
            builder.append(param.getSetting());
        });
        builder.append(SFS);
        return builder.toString();
    }
    
}
