package jaredbgreat.procgenlab.loader;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

import jaredbgreat.procgenlab.api.IGenerator;
import jaredbgreat.procgenlab.registries.Registrar;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 *
 * @author Jared Blackburn
 */
public class JarLoader extends ClassLoader {
    private static JarLoader it = new JarLoader();
    
    public static void loadFile(File file) {
        if(!(file.exists() && file.canRead() && file.isFile())) {
            return;
        }
        try {
            if(file.getName().endsWith(".class")) {
                loadClassFile(file);
            } else if (file.getName().endsWith(".jar") 
                    || file.getName().endsWith(".zip")) {
                loadJar(file);
            }
        } catch (Exception ex) {
             Logger.getLogger(JarLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    /**
     * Loads a class file not found in a jar.
     * 
     * @param file
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static void loadClassFile(File file) 
                    throws ClassNotFoundException, 
                           InstantiationException, 
                           IllegalAccessException,
                           FileNotFoundException,
                           IOException {
        // THIS IS TESTED AND WORKDS!
        FileInputStream data = new FileInputStream(file);
        int size = (int)file.length();
        byte[] buffer = new byte[size];
        data.read(buffer);
        Class theClass = it.defineClass(null, buffer, 0, size);
        if(IGenerator.class.isAssignableFrom(theClass)) {            
            IGenerator newGen = (IGenerator) theClass.newInstance();
            System.out.println("IGnerator found: " + newGen.getName());
            Registrar.getRegistrar().registerGenerator(newGen);
        }
    }
    
    
    public static void loadJar(File file) 
                        throws FileNotFoundException, 
                               IOException, 
                               ClassNotFoundException, 
                               InstantiationException, 
                               IllegalAccessException {
        // FIXME:  This does not work so far!
        FileInputStream data = new FileInputStream(file);
        try (ZipInputStream jar = new ZipInputStream(new BufferedInputStream(data))) {
            ZipEntry entry;
            while((entry = jar.getNextEntry()) != null) {
                if(entry.getName().endsWith(".class"));
                int size = (int)entry.getSize();
                byte[] buffer = new byte[size];
                jar.read(buffer);
                Class theClass = it.defineClass(null, buffer, 0, size);
                if(IGenerator.class.isAssignableFrom(theClass)) {
                    IGenerator newGen = (IGenerator) theClass.newInstance();
                    System.out.println("IGnerator found: " + newGen.getName());
                    Registrar.getRegistrar().registerGenerator(null);
                }
                
            }
        }
    }
}
