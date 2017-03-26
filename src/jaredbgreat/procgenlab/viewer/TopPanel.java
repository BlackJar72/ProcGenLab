package jaredbgreat.procgenlab.viewer;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

import java.awt.FlowLayout;
import static java.awt.FlowLayout.RIGHT;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
        layout = new GridLayout();
        setLayout(layout);
        init();
    }
    
    
    public void init() {
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
        add(profLabel);
        add(profiler);
    }
    
    
    public JTextField getSeedBox() {
        return seedbox;
    }
}
