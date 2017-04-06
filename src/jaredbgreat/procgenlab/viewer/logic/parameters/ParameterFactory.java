package jaredbgreat.procgenlab.viewer.logic.parameters;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

import static jaredbgreat.procgenlab.util.Delims.*;
import java.util.StringTokenizer;

/**
 *
 * @author Jared Blackburn
 */
public class ParameterFactory {
    
    
    public static IParameter makeParameter(String def) {
        IParameter out = null;
        StringTokenizer tokens = new StringTokenizer(def, 
                String.valueOf(RS) + String.valueOf(US));
        switch(ParameterType.valueOf(tokens.nextToken().trim().toUpperCase())) {
            
        }        
        return out;
    }
    
}
