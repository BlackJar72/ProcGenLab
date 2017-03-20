package jaredbgreat.procgenlab.interfaces;

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
public interface IGenerator {
    
    public void generate();
    public IWorldMap[] getData();
    public IWorldMap[] generateData();
    public void setParameters(String[] param);
    
    
}
