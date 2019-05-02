package jaredbgreat.procgenlab.viewer.control;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 * 
 * @author Jared Blackburn
 */

/**
 * And interface use to represent executable commands issued in response 
 * to GUI events.
 * 
 * @author jared
 */
public interface ICommand {
    /** Execute the command; code for its function goes here.*/
    public void execute(/*Should this take an ActionEvent parameter?*/);
}
