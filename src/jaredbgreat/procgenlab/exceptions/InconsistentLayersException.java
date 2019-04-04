package jaredbgreat.procgenlab.exceptions;

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
public class InconsistentLayersException extends Exception {
	private static final long serialVersionUID = 8620590028774614702L;

	public InconsistentLayersException(String message) {
        super(message);
    }
}
