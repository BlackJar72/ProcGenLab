package jaredbgreat.procgenlab.registries;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

import jaredbgreat.procgenlab.interfaces.IGenerator;
import jaredbgreat.procgenlab.interfaces.IPalette;
import jaredbgreat.procgenlab.viewer.logic.parameters.IParameter;
import java.util.List;

/**
 *
 * @author Jared Blackburn
 */
public class Registrar {
    public static final Registrar registries = new Registrar();
    private final Registry<IGenerator> gens;
    private final Registry<IPalette[]> palettes;
    private final Registry<List<IParameter>> parameters;
    
    
    private Registrar() {
        gens = new Registry<>();
        palettes = new Registry<>();
        parameters = new Registry<>();
    }
    
    
    public void registerGenerator(IGenerator gen, String name) {
        gens.add(name, gen);
        palettes.add(name, gen.getColorPaletes());
        //TODO: Add data to to ComboBox gui widget "generators."
    }
    
    
    public IGenerator getGenerator(String name) {
        if(gens.containsKey(name)) {
            return gens.get(name);
        } else return null;
    }
    
    
    
}
