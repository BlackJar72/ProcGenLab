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
                SRS);
        switch(ParameterType.valueOf(tokens.nextToken().trim().toUpperCase())) {
            case BOOL:
                return new BooleanParameter(tokens.nextToken());
            case INT:
                return new IntParameter(tokens.nextToken());
            case LONG:
                return new LongParameter(tokens.nextToken());
            case FLOAT:
                return new FloatParameter(tokens.nextToken());
            case DOUBLE:
                return new DoubleParameter(tokens.nextToken());
            case RANGE:
                return new IntRangeParameter(tokens.nextToken(), 
                        tokens.nextToken());
            case STRING:
                return new StringParameter(tokens.nextToken());
            case MULTI:
                return new MultiselectParameter(tokens.nextToken(), 
                        tokens.nextToken());
            default:
                System.err.println("ERROR: Parameter factory recieved "
                        + "invalid type!");
                new Exception().printStackTrace();
                System.exit(1);
        }        
        return out;
    }
    
}
