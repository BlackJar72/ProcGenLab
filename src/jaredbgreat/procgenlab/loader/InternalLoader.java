package jaredbgreat.procgenlab.loader;

import jaredbgreat.procgenlab.api.IGenerator;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

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
public class InternalLoader {
    private static final String dir = "/jaredbgreat/procgenlab/generators";
    private static final String pac = "jaredbgreat.procgenlab.generators.";
    
    public static void listClasses() {
        URL resource = InternalLoader.class.getResource(dir);
        System.out.println(resource);
        File file = new File(resource.getFile());
        String[] contents = file.list();
        URL[] res = new URL[contents.length];
        for(int i = 0; i < contents.length; i++) {
            System.out.println(contents[i]);
            InputStream out;            
            out = InternalLoader.class.getResourceAsStream(dir + "/" + contents[i]);            
            char c;
            int  v = -1;
            if(out != null) do {
                try {
                    v = out.read();
                } catch (IOException ex) {
                    Logger.getLogger(InternalLoader.class.getName()).log(Level.SEVERE, null, ex);
                }
                c = (char) v;
                System.out.print(c);
            } while (v > 0);
            System.out.println();
            try {
                if(contents[i].endsWith(".class")) {
                    String name = pac + contents[i].substring(0, contents[i].length() - 6);
                    Class geegee = ClassLoader.getSystemClassLoader().loadClass(name);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(InternalLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println();            
        }
    }
    
    
    
}
