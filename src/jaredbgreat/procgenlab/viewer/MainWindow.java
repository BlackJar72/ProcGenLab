package jaredbgreat.procgenlab.viewer;

/*
 * Copyright (C) Jared Blackburn 2016
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

import jaredbgreat.procgenlab.registries.Registry;
import jaredbgreat.procgenlab.viewer.control.Interpreter;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

/**
 * Is the main window of the application.  Will hold a menu and a viewing pane
 * for visualizing the maps or map generation layers.  Main use panes for 
 * help and config panes to, et., to be included.
 * 
 * @author Jared Blackburn
 */
public class MainWindow extends JFrame {
    private static MainWindow it;
    private static Registry<JComponent> components;
    
    private final int width = 1024;
    private final int height = 640;
    
    private BorderLayout layout;
    private TopPanel topPanel;
    private GeneratorPanel gensArea;
    
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem menuExit;
    
    private MainWindow() {
        setupWindow();
        setupMenuBar();
        setVisible(true);
    }
    
    
    public static MainWindow getMainWindow() {
        if(it == null) {
            it = new MainWindow();
        }
        return it;
    }
    
    
    /**
     * Sets up the window itself and major / global features
     */
    private void setupWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(width, height);
        setLayout(layout = new BorderLayout());
        topPanel = new TopPanel();
        gensArea = new GeneratorPanel();
        add(topPanel, BorderLayout.NORTH);
        add(gensArea, BorderLayout.CENTER);
        MainWindow.registerComponenent("TopPanel", topPanel);
        MainWindow.registerComponenent("GeneratorArea", gensArea);
    }
    
    
    /**
     * Sets up the menu bar and its menus.
     */
    private void setupMenuBar() {
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        MainWindow.registerComponenent("FileMenu", fileMenu);
        menuExit = new JMenuItem("Exit");
        menuExit.setActionCommand("exit");
        menuExit.addActionListener(Interpreter.getInterpeter());        
        fileMenu.add(menuExit);
        MainWindow.registerComponenent("MenuExit", menuExit);
        menuBar.add(fileMenu);
        menuBar.setBorder(BorderFactory.createRaisedBevelBorder());
        setJMenuBar(menuBar);  
        MainWindow.registerComponenent("MenuBar", menuBar);
    }
    
    
    private JTextField getSeedBox() {
        return topPanel.getSeedBox();
    }
    
    
    public static Registry<JComponent> getComponentRegistry() {
        if(components == null) {
            components = new Registry<>();
        }
        return components;
    }
    
    
    public static void registerComponenent(String name, 
            JComponent component) {
        getComponentRegistry().add(name, component);
    }
    
    
    public static JComponent getComponenent(String name) {
        return getComponentRegistry().get(name);
    }
    
}
