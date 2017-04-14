package jaredbgreat.procgenlab.viewer;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import static java.awt.GridBagConstraints.*;
import java.awt.Insets;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Jared Blackburn
 */
public class GeneratorPanel extends JPanel {
    SidePanel options;
    ViewPanel viewing;
    JTextArea stats;
    GridBagLayout layout;
    JScrollPane scroll;
    GridBagConstraints optConstraints, viewConstraints;
    
    
    GeneratorPanel() {
        layout = new GridBagLayout();
        setLayout(layout);
        Insets nothing  = new Insets(0, 0, 0, 0);
        optConstraints  = new GridBagConstraints(0, 0, 1, 1, 0.25, 1.0, LINE_START, 
                        BOTH, nothing, 0, 0);
        viewConstraints = new GridBagConstraints(1, 0, 1, 1, 0.75, 1.0, CENTER, 
                        BOTH, nothing, 0, 0);
        options = new SidePanel();
        viewing = new ViewPanel();
        stats   = new JTextArea();
        scroll = new JScrollPane(options);
        add(scroll, optConstraints);
        add(viewing, viewConstraints);
        MainWindow.registerComponenent("SidePanel", options);
        MainWindow.registerComponenent("ViewPanel", viewing);
        MainWindow.registerComponenent("StatsPanel", stats);
        MainWindow.registerComponenent("SideScroll", scroll);
    }
    
    
}
