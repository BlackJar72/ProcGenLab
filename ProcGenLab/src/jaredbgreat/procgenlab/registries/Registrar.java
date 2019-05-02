package jaredbgreat.procgenlab.registries;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

import jaredbgreat.procgenlab.api.IGenerator;
import jaredbgreat.procgenlab.api.IPalette;
import jaredbgreat.procgenlab.viewer.MainWindow;
import jaredbgreat.procgenlab.viewer.logic.parameters.IParameter;
import jaredbgreat.procgenlab.viewer.logic.parameters.ParameterFactory;
import java.util.List;
import javax.swing.JComboBox;

/**
 * This class handles registries, registering them and providing access to 
 * resources located within them.
 * 
 * @author Jared Blackburn
 */
public class Registrar {
    private static Registrar registries = new Registrar();
    private final Registry<IGenerator> gens;
    private final Registry<IPalette[]> palettes;
    private final Registry<List<IParameter>> parameters;
    @SuppressWarnings("rawtypes")
	private final JComboBox selector;
    
    
    @SuppressWarnings("rawtypes")
	private Registrar() {
        gens = new Registry<>();
        palettes = new Registry<>();
        parameters = new Registry<>();
        selector = (JComboBox)MainWindow.getComponentRegistry()
                .get("SelectorComboBox");
    }
    
    
    public static Registrar getRegistrar() {
        if(registries == null) {
            registries = new Registrar();
        }
        return registries;
    }
    
    
    /**
     * This will register the generator passed to it using the names 
     * provided by the IGenerator's getName() method.  In addition, it
     * will also register the generators parameters and palettes using those 
     * names so that they are conveniently cached and available when needed, 
     * such as when the generator selection is changed or the generators 
     * generate() method is called.
     * 
     * @param gen = the generator being registered.
     */
    @SuppressWarnings("unchecked")
	public void registerGenerator(IGenerator gen) {
        String name = gen.getName();
        gens.add(name, gen);
        palettes.add(name, gen.getColorPaletes());
        parameters.add(name, 
                ParameterFactory.makeParameters(gen.getParameters()));        
        selector.addItem(name);
        selector.setEnabled(true);
    }
    
    
    /**
     * This will return the generator corresponding the name given, 
     * assuming a generator by that name exists.
     * 
     * @param name
     * @return the IGenerator stored under name
     */
    public IGenerator getGenerator(String name) {
        if(gens.containsKey(name)) {
            return gens.get(name);
        } else return null;
    }
    
    
    /**
     * This will retrieve the array of palettes to be used in converting
     * map data to graphical form.
     * 
     * @param name
     * @return IPalette[] - the array of palettes for the named generator
     */
    public IPalette[] getPalettes(String name) {
        if(gens.containsKey(name)) {
            return palettes.get(name);
        } else return null;
    }
    
    
    /**
     * This will retrieve the list of parameters for the named generators, 
     * allowing quick access.
     * 
     * @param name
     * @return a List of IParameter
     */
    public List<IParameter> getParameters(String name) {
        if(gens.containsKey(name)) {
            return parameters.get(name);
        } else return null;
    }
    
    
    
}
