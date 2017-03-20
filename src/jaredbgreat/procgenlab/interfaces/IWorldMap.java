package jaredbgreat.procgenlab.interfaces;

/*
 * Copyright (C) Jared Blackburn 2016
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
*/

import java.awt.Image;

/**
 *  For use internally in representing processed representations
 * 
 * @author Jared Blackburn
 */
public interface IWorldMap {
    
    public Image getImage(int number);
    public String getImageName(int number);
    
}
