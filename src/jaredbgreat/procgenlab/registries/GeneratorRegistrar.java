package jaredbgreat.procgenlab.registries;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

import jaredbgreat.procgenlab.interfaces.IGenerator;
import jaredbgreat.procgenlab.interfaces.IPalette;

/**
 *
 * @author jared
 */
public class GeneratorRegistrar {
    private final Registry<IGenerator> gens;
    private final Registry<IPalette[]> palettes;
    
    
    public GeneratorRegistrar() {
        gens = new Registry<>();
        palettes = new Registry<>();
    }
    
    
    public void registerGenerator(IGenerator gen, String name) {
        gens.add(name, gen);
        palettes.add(name, gen.getColorPaletes());
        //TODO: Add data to to ComboBox gui widget "generators."
    }
    
    
    
}
