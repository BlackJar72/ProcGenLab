package jaredbgreat.procgenlab.viewer.logic.parameters;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

import static jaredbgreat.procgenlab.api.util.Delims.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author Jared Blackburn
 */
public class ParameterFactory {
    
    
    public static List<IParameter> makeParameters(String param) {
        StringTokenizer tokens = new StringTokenizer(param, SFS + SGS);
        ArrayList<IParameter> out = new ArrayList<>();
        while(tokens.hasMoreTokens()) {
            out.add(makeParameter(tokens.nextToken()));
        }
        return out;
    }
    
    
    public static IParameter makeParameter(String def) {
        IParameter out = null;
        StringTokenizer tokens = new StringTokenizer(def, 
                SRS);
        switch(ParameterType.valueOf(tokens.nextToken().trim().toUpperCase())) {
            case BOOL:
                out = new BooleanParameter(tokens.nextToken());
                break;
            case INT:
                out = new IntParameter(tokens.nextToken());
                break;
            case LONG:
                out = new LongParameter(tokens.nextToken());
                break;
            case FLOAT:
                out = new FloatParameter(tokens.nextToken());
                break;
            case DOUBLE:
                out = new DoubleParameter(tokens.nextToken());
                break;
            case RANGE:
                out = new IntRangeParameter(tokens.nextToken(), 
                        tokens.nextToken());
                break;
            case STRING:
                out = new StringParameter(tokens.nextToken());
                break;
            case MULTI:
                out = new MultiselectParameter(tokens.nextToken(), 
                        tokens.nextToken());
                break;
            default:
                System.err.println("ERROR: Parameter factory recieved "
                        + "invalid type!");
                throw new RuntimeException();
        }
        if(tokens.hasMoreTokens()) {
            out.set(tokens.nextToken());
        }
        return out;
    }
    
}
