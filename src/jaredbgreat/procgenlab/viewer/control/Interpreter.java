package jaredbgreat.procgenlab.viewer.control;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * A class to store, retrieve, and execute commands defined with GUI components.
 * 
 * This is used to execute commands sent from the swing components.
 * 
 * @author Jared Blackburn
 */
public class Interpreter implements ActionListener {
    private final Map<String, ICommand> commands;
    private static Interpreter it;
    
    private Interpreter(){
        commands = new HashMap<>();
        addCommands();
    }    
    
    /**
     * A command that should only be called once, but the \
     * constructor, to register all the commands.
     */
    private void addCommands() {
        commands.put("exit", new ExitCommand());
        commands.put("generate", new GenerateCommand());
    }
    
    
    /**
     * Returns a singleton instance.
     * 
     * (Yes, I know, "Friends don't let friends create singletons," 
     *  but this really is the simplest way for this.)
     * 
     * @return 
     */
    public static Interpreter getInterpeter() {
        if(it == null) {
            it = new Interpreter();
        }
        return it;
    }
    
    
    /**
     * Find and execute the command given by the string.  This string should 
     * usually be an command action defined by a gui component.
     * 
     * @param command 
     */
    public void execute(String command) {
        ICommand com = commands.get(command);
        if(com != null) {
            com.execute();
        } else {
            System.err.println("ERROR: Gui issued invalid command string \""
                    + command + "\"!");
            throw new RuntimeException();
        }
    }
    
    
    /**
     * Required by the ActionListener interface to receive its 
     * events.  In effect this wraps execute(), though that c
     * method only takes the action command rather than the whole 
     * event.
     * 
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        execute(e.getActionCommand());
    }
}
