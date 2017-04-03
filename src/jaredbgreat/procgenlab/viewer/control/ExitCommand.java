package jaredbgreat.procgenlab.viewer.control;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

/**
 *
 * @author Jared Blackburn
 */
public class ExitCommand implements ICommand {

    @Override
    public void execute() {
        // This may need to be changed to call a clean-up routine, but most 
        // likely is fine ass it is.
        System.exit(0);
    }
    
}
