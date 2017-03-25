/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaredbgreat.procgenlab.viewer;

import java.awt.FlowLayout;
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
        // OK, WTF!!! Why does this exact code give different result each time run
        // Why does it occationally work and more often not!
        addGenerateButton();
        addSelector();
        addSeedbox();        
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
    private void addSeedbox() {
        seedLabel = new JLabel("Seed: ");
        seedLabel.setText("Seed: ");
        seedbox = new JTextField();
        add(seedLabel);
        add(seedbox);
    }
}
