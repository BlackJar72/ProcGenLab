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
            case BOOL:
                    
                break;
            case INT:
                    
                break;
            case LONG:
                    
                break;
            case FLOAT:
                    
                break;
            case DOUBLE:
                    
                break;
            case IRANGE:
                    
                break;
            case RRANGE:
                    
                break;
            case STRING:
                    
                break;
            case MULTI:
                    
                break;
            default:
                System.err.println("ERROR: Parameter factor recieved "
                        + "invalid type!");
                new Exception().printStackTrace();
                System.exit(1);
        }        
        return out;
    }
    
}
