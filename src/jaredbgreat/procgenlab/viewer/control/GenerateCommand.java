package jaredbgreat.procgenlab.viewer.control;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

import jaredbgreat.procgenlab.interfaces.IGenerator;
import jaredbgreat.procgenlab.registries.Registrar;
import jaredbgreat.procgenlab.viewer.logic.RandomHelper;
import jaredbgreat.procgenlab.viewer.logic.parameters.IParameter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JComponent;
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
                = Registrar.registries
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
    }
    
    
    public static void setSeedbox(JTextField input) {
        seedbox = input;
    }
    
    
    public static void setTimebox(JTextField input) {
        timebox = input;
    }
    
    
    public static void setSelector(JComboBox input) {
        selection = input;
    }
    
    
    public static void setParameters(List<IParameter> input) {
        parameters = input;
    }
    
    
    private String[] getArgumentString() {
        ArrayList<String> builder = new ArrayList<>();
        for(IParameter param : parameters) {
            builder.add(param.getSetting());
        }
        builder.trimToSize();
        return (String[])builder.toArray();
    }
    
}
