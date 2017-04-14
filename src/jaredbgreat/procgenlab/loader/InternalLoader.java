package jaredbgreat.procgenlab.loader;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

import jaredbgreat.procgenlab.api.IGenerator;
import jaredbgreat.procgenlab.registries.Registrar;
import java.io.File;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jared Blackburn
 */
public class InternalLoader extends ClassLoader {
    private static final InternalLoader it = new InternalLoader();
    private static final String dir = "/jaredbgreat/procgenlab/generators";
    private static final String pac = "jaredbgreat.procgenlab.generators.";
    
    public static void listClasses() {
        URL resource = InternalLoader.class.getResource(dir);
        File directory = new File(resource.getFile());
        String[] contents = directory.list();
        for(int i = 0; i < contents.length; i++) {
            try {
                if(contents[i].endsWith(".class")) {
                    String name = pac + contents[i].substring(0, contents[i].length() - 6);
                    Class theClass = it.loadClass(name, true);
                    if(IGenerator.class.isAssignableFrom(theClass)) {
                        IGenerator newGen = (IGenerator) theClass.newInstance();
                        System.out.println("IGnerator found: " + newGen.getName());
                        Registrar.getRegistrar().registerGenerator(newGen);
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(InternalLoader.class.getName()).log(Level.SEVERE, null, ex);
                System.exit(1);
            }
        }
    }
    
    
    
}
