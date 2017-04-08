package jaredbgreat.procgenlab.viewer.logic;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

import jaredbgreat.procgenlab.api.IGenerator;
import jaredbgreat.procgenlab.api.IPalette;
import jaredbgreat.procgenlab.viewer.logic.parameters.IParameter;
import jaredbgreat.procgenlab.viewer.logic.parameters.ParameterFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the model of a generator for use with the GUI controls.  Its include 
 * the generator instance used to
 * 
 * (THIS MAY BE A DEADEND BASED ON FORGETTING MY ORIGINAL DESIGN USING THE 
 * REGISTAR TO FIND ALL RELATED PIECES BY NAME.  Do I keep this? Or keep the 
 * separate lists in the Registrar?)
 * 
 * @author Jared Blackburn
 */
public class GuiGenerator {
    private final String name;
    private final IGenerator gen;
    private final List<IParameter> parameters;
    private final List<IPalette> palettes;
    
    
    public GuiGenerator(IGenerator generator) {
        gen = generator;
        name = gen.getName();
        parameters = ParameterFactory.makeParameters(gen.getParameters());
        palettes = new ArrayList<>(); // TODO: Make a palette factory
    }
    
}
