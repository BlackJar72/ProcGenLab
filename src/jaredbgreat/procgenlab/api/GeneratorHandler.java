package jaredbgreat.procgenlab.api;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

import jaredbgreat.procgenlab.registries.Registrar;

/**
 * This is a class of static helper methods for registering generators.
 * 
 * @author Jared Blackburn
 */
public class GeneratorHandler { 
    private static Registrar registrar;
    
    /**
     * This will register the generator passed to it in the central 
     * registries used by other part of the application.
     * 
     * @param gen 
     */
    public static void register(IGenerator gen) {
        if(registrar == null) {
            registrar = Registrar.getRegistrar();
        }
        registrar.registerGenerator(gen);
    }
    
}
