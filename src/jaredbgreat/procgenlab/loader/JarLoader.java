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
public class JarLoader {
    
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
    
    
    public static void loadClassFile(File file) 
                    throws ClassNotFoundException, 
                           InstantiationException, 
                           IllegalAccessException,
                           FileNotFoundException,
                           IOException {
        FileInputStream data = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(data);
        Class theClass = (Class)ois.readObject();
        ClassLoader.getSystemClassLoader().loadClass(theClass.getCanonicalName());
        if(IGenerator.class.isAssignableFrom(theClass)) {            
            IGenerator newGen = (IGenerator) theClass.newInstance();
            System.out.println("IGnerator found: " + newGen.getName());
            Registrar.getRegistrar().registerGenerator(newGen);
        }
    }
    
    
    public static void loadJar(File file) throws FileNotFoundException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        FileInputStream data = new FileInputStream(file);
        try (ZipInputStream jar = new ZipInputStream(new BufferedInputStream(data))) {
            ZipEntry entry;
            while((entry = jar.getNextEntry()) != null) {
                if(entry.getName().endsWith(".class"));
                ObjectInputStream ois = new ObjectInputStream(data);
                Class theClass = (Class)ois.readObject();
                ClassLoader.getSystemClassLoader().loadClass(theClass.getCanonicalName());
                if(IGenerator.class.isAssignableFrom(theClass)) {
                    IGenerator newGen = (IGenerator) theClass.newInstance();
                    System.out.println("IGnerator found: " + newGen.getName());
                    Registrar.getRegistrar().registerGenerator(null);
                }
                
            }
        }
    }
}
