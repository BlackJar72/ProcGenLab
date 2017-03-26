package jaredbgreat.procgenlab.viewer;

/*
 * Copyright (C) Jared Blackburn 2016
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Is the main window of the application.  Will hold a menu and a viewing pane
 * for visualizing the maps or map generation layers.  Main use panes for 
 * help and config panes to, et., to be included.
 * 
 * @author Jared Blackburn
 */
public class MainWindow extends JFrame {
    private int width = 1024;
    private int height = 640;
    
    private BorderLayout layout;
    private TopPanel topPanel;
    private SidePanel sidePanel;
    private ViewPanel viewPanel;
    
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem menuExit;
    
    public MainWindow() {
        setupWindow();
        setupMenuBar();
        setVisible(true);
    }
    
    
    /**
     * Sets up the window itself and major / global features
     */
    private void setupWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(width, height);
        setLayout(layout = new BorderLayout());
        topPanel = new TopPanel();
        sidePanel = new SidePanel();
        viewPanel = new ViewPanel(null);
        add(topPanel, BorderLayout.NORTH);
        add(sidePanel, BorderLayout.WEST);
        add(viewPanel, BorderLayout.CENTER);
    }
    
    
    /**
     * Sets up the menu bar and its menus.
     */
    private void setupMenuBar() {
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        menuExit = new JMenuItem("Exit");
        fileMenu.add(menuExit);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);  
    }
    
}
