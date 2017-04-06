package jaredbgreat.procgenlab.viewer;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import jaredbgreat.procgenlab.viewer.control.GenerateCommand;
import jaredbgreat.procgenlab.viewer.control.Interpreter;
import javax.swing.BorderFactory;

/**
 *
 * @author Jared Blackburn
 */
public class TopPanel extends JPanel {
    
    GridLayout layout;
    
    JButton generate;
    JComboBox generators;
    JTextField seedbox;
    JTextField profiler;
    JLabel seedLabel, profLabel, gensLabel;
    
    TopPanel() {
        layout = new GridLayout(1, 4);
        setLayout(layout);
        init();
    }
    
    
    public void init() {
        setBorder(BorderFactory.createRaisedBevelBorder());
        addGenerateButton();
        addSelector();
        addSeedBox();   
        addProfileBox();
    }
    
    
    /**
     * Adds the "Generate!" button
     */
    private void addGenerateButton() {
        generate = new JButton("Generate");
        generate.addActionListener(Interpreter.getInterpeter());
        generate.setActionCommand("generate");
        generate.setName("generate");
        generate.setText("Generate!");
        add(generate);
    }
    
    
    /**
     * Adds the combo box for generators
     */
    private void addSelector() {
        generators = new JComboBox();
        generators.setEditable(false);
        generators.setEnabled(false);
        GenerateCommand.setSelector(generators);
        //TODO: setup combo box
        add(generators);
        
    }
    
    
    /**
     * Adds text box for setting random seed.
     */
    private void addSeedBox() {
        seedLabel = new JLabel("Seed: ");
        seedLabel.setText("Seed: ");
        seedLabel.setHorizontalAlignment(JLabel.TRAILING);
        seedbox = new JTextField();
        seedLabel.setLabelFor(seedbox);
        GenerateCommand.setSeedbox(seedbox);
        add(seedLabel);
        add(seedbox);
    }
    
    
    /**
     * Adds text box for sviewing profiler timings.
     */
    private void addProfileBox() {
        profLabel = new JLabel("Time: ");
        profLabel.setText("Time: ");
        profLabel.setHorizontalAlignment(JLabel.TRAILING);
        profiler = new JTextField();
        profiler.setEditable(false);
        profLabel.setLabelFor(profiler);
        GenerateCommand.setTimebox(profiler);
        add(profLabel);
        add(profiler);
    }
    
    
    public JTextField getSeedBox() {
        return seedbox;
    }
    
    
    private void addGenerator(String name) {
        generators.addItem(name);
        generators.setEnabled(true);
    }
    
    
    private void removeGenerator(String name) {
        generators.removeItem(name);
        if(generators.getItemCount() < 1) {
            generators.setEnabled(false);
        }
    }
}
