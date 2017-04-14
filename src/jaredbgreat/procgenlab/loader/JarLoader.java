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
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 *
 * @author Jared Blackburn
 */
public class JarLoader extends ClassLoader {
    private static final JarLoader it = new JarLoader();
    
    
    public static JarLoader getJarLoader() {
        return it;
    }
    
    
    public void loadFile(File file) {
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
    private void loadClassFile(File file) 
                    throws ClassNotFoundException, 
                           InstantiationException, 
                           IllegalAccessException,
                           FileNotFoundException,
                           IOException {
        // THIS IS TESTED AND WORKS!
        FileInputStream data = new FileInputStream(file);
        int size = (int)file.length();
        byte[] buffer = new byte[size];
        data.read(buffer);
        Class theClass = it.defineClass(null, buffer, 0, size);
        register(theClass);
    }
    
    
    private void loadJar(File file) 
                        throws FileNotFoundException, 
                               IOException, 
                               ClassNotFoundException, 
                               InstantiationException, 
                               IllegalAccessException {
        // FIXME:  This does not work so far!
        FileInputStream data = new FileInputStream(file);
        List<String> classes = listClasses(file);
        URL url = new URL("jar:file://" + file.getCanonicalPath() + "!/");
        System.out.println(url);
        URLClassLoader loader = new URLClassLoader(new URL[]{url}, getParent());
        for(String path : classes) {
            String name = path.substring(0, path.length() - 6).replace(File.separator, ".");
            System.out.println(path);
            Class theClass = loader.loadClass(name);
            System.out.println(theClass.getName()  + " -> " + IGenerator.class.isAssignableFrom(theClass));
            register(theClass);
        }
    }
    
    
    private static List<String> listClasses(File file)
                        throws FileNotFoundException, 
                               IOException, 
                               ClassNotFoundException, 
                               InstantiationException, 
                               IllegalAccessException {
        ArrayList<String> out = new ArrayList<>();
        FileInputStream data = new FileInputStream(file);
        try (ZipInputStream jar = new ZipInputStream(new BufferedInputStream(data))) {
            ZipEntry entry;
            while((entry = jar.getNextEntry()) != null) {
                if(!entry.isDirectory() && entry.getName().endsWith(".class")) {
                    out.add(entry.getName());
                }
            }
        }        
        return out;
    }
    
    
    private static Class register(Class theClass) 
                        throws InstantiationException, 
                               IllegalAccessException {
        if(IGenerator.class.isAssignableFrom(theClass) 
                && !theClass.isInterface()) {
            try {
                IGenerator newGen = (IGenerator) theClass.newInstance();
                System.out.println("IGenerator found: " + newGen.getName());
                Registrar.getRegistrar().registerGenerator(newGen);
            } catch (InstantiationException e) {
                System.out.println("IGenerator class " + theClass.getName() 
                    + " could not be instantiated.  (Is it abstract?)");
            }
        }
        return theClass;
    }
}
