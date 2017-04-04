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
 * A class to store, retrieve, and execute commands defined with gui components.
 * 
 * This is used to exectute commands sent from the swing components.
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
    
    private void addCommands() {
        commands.put("exit", new ExitCommand());
        commands.put("generate", new GenerateCommand());
    }
    
    
    /**
     * Returns a singleton instance.
     * 
     * (Yes, I know "friends don't let friends use singletons," 
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
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        execute(e.getActionCommand());
    }
}
