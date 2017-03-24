/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaredbgreat.procgenlab.viewer;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
    
    GridBagLayout layout;
    
    JButton generate;
    JComboBox generators;
    JTextField seedbox;
    JTextField profiler;
    JLabel seedLabel, profLabel, gensLabel;
    
    TopPanel() {
        layout = new GridBagLayout();
        // OK, WTF!!! Why does this exact code give different result each time run
        // Why does it occationally work and more often not!
        addGenerateButton();
        //addSelector();
        //addSeedbox();
    }
    
    
    /**
     * Adds the "Generate!" button
     */
    private void addGenerateButton() {
        generate = new JButton("Generate");
        generate.setActionCommand("generate");
        generate.setName("generate");
        generate.setText("Generate!");
        add(generate, new GridBagConstraints(0, 0, 2, 1, 1, 1, 
                1, 1, new Insets(1, 1, 1, 1), 3, 3));
    }
    
    
    /**
     * Adds the combo box for generators
     */
    private void addSelector() {
        generators = new JComboBox();
        //TODO: setup combo box
        add(generators, new GridBagConstraints(1, 0, 2, 1, 1, 1, 
                1, 1, new Insets(1, 1, 1, 1), 3, 3));
    }
    
    
    /**
     * Adds text box for setting random seed.
     */
    private void addSeedbox() {
        seedLabel = new JLabel("Seed: ");
        seedLabel.setText("Seed: ");
        add(seedLabel, new GridBagConstraints(2, 0, 1, 1, 1, 1, 
                1, 1, new Insets(1, 1, 1, 1), 1, 1));
        seedbox = new JTextField("seed");
        seedbox.setName("seed");
        seedbox.setText("");
        add(seedbox, new GridBagConstraints(2, 1, 1, 1, 1, 1, 
                1, 1, new Insets(1, 1, 1, 1), 1, 1));
    }
}
