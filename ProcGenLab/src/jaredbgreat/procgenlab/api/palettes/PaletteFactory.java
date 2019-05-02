package jaredbgreat.procgenlab.api.palettes;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
*/

import java.util.ArrayList;
import static jaredbgreat.procgenlab.api.Delims.*;
import jaredbgreat.procgenlab.api.IPalette;
import java.util.StringTokenizer;

/**
 * This class provides an alternate way to create palettes for use in generators 
 * / by the GUI to display generators.  Each palette must be defined by a String 
 * of the format:
 * 
 * "type SRS data" where SRS is the non-printable record separator character
 * 
 * Fields within a multi-part data segment must be divided by the non-printable 
 * unit separator.
 * 
 * Multiple palettes, if placed in one String must be divided by the 
 * non-printable group separator character.  Such strings should end in the 
 * file separator chracter, but this is not required.
 * 
 * See jaredbgreat.procgenlab.api.Delims for how to easily find and use these 
 * special `characters.
 * 
 * Each palette type is represented by a constant in the enum DisplayTypes.  The 
 * name must match the enum constant but is case-insensitive.
 * 
 * At this time it will probably be just as good, if not better, to create 
 * most palettes directly by their constructor and set them up with their 
 * setPalette() methods.  However, this provides an alternate system that some 
 * may prefer and which is more consistent with the way other objects in the 
 * game (notably generator defined parameters).  It is also a prototype for 
 * the API of a possible ProcGenLab 2, which, if made, would be written in C++ 
 * with Qt, and hopefully have binding for multiple languages some of which 
 * communicate externally through things like byte streams / arrays or text 
 * strings; additionally ProcGenLab might have other output types besides 
 * images (e.g., text pages, graphs, and/or 3D spaces) which would increase 
 * the need for flexibility in defining the type of display for the data 
 * generated.
 * 
 * @author Jared Blackburn
 */
public class PaletteFactory {
    
    /**
     * This will take a String defining a series of palettes and turn it 
     * into an array of palettes represented by instances of classes 
     * extending IPalette.
     * 
     * @param definition
     * @return and array of palettes (IPalette[])
     */
    public static IPalette[] makePaletteArray(String definition) {
        ArrayList<IPalette> out = new ArrayList<>();
        StringTokenizer lines = new StringTokenizer(definition, SGS + SFS);
        while(lines.hasMoreTokens()) {
            out.add(makePalette(definition));            
        }
        return (IPalette[])out.toArray();
    }
    
    
    /**
     * This will take a String defining an instance of a palette and turn 
     * it into an actual instance of a class implementing IPalette.
     * 
     * @param definition
     * @return a new palette (IPalette)
     */
    public static IPalette makePalette(String definition) {
        StringTokenizer tokens = new StringTokenizer(definition, SRS);
        switch(DisplayType.valueOf(tokens.nextToken().toUpperCase())) {
            case DISCRETE:
                return makeDiscretePalette(tokens.nextToken());
            case CONTINUOUS:
                break;
            case DISCONTINUOUS:
                break;
            case LITERAL:
                return new LiteralPalette();                
            default:
                throw new AssertionError("Unkown DisplayType found "
                                        + "by PaletteFactoy");        
        }
        
        return null;
    }
    
    
    /**
     * This will parse the rest of a string of tokens into a DiscretePalette.
     * 
     * @param tokens
     * @return a new DiscretePalette
     */
    private static DiscretePalette makeDiscretePalette(String data) {
        StringTokenizer tokens = new StringTokenizer(data, SUS);
        DiscretePalette out = new DiscretePalette();
        ArrayList<Integer> colors = new ArrayList<>();
        while(tokens.hasMoreTokens()) {
            colors.add(parseDecimalOrHex(tokens.nextToken()));
        }
        int[] numbers = new int[colors.size()];
        for(int i = 0; i < numbers.length; i++) {
            numbers[i] = colors.get(i);
        }
        out.setPalette(numbers);
        return out;
    }
    
    
    /**
     * This will create a new ContinuousPalette using the supplied 
     * string of tokens.
     * 
     * @param tokens
     * @return a new ContinuousPalette
     */
    private static ContinuousPalette 
                    makeContinuousPalette(String data) {
        StringTokenizer tokens = new StringTokenizer(data, SUS);
        ContinuousPalette out = new ContinuousPalette();
        out.setPalette(parseDecimalOrHex(tokens.nextToken()), 
                       parseDecimalOrHex(tokens.nextToken()),
                       parseDecimalOrHex(tokens.nextToken()), 
                       parseDecimalOrHex(tokens.nextToken()));
        return out;
    }
    
    
    /**
     * This will parse a string definition into a discontinuous palette.
     * 
     * @param tokens
     * @return a new discontinuous palette
     */
    private static DiscontinuousPalette 
                    makeDiscontinuousPalette(StringTokenizer tokens) {        
        DiscontinuousPalette out = new DiscontinuousPalette();
        ArrayList<ContinuousPalette> parts = new ArrayList<>();
        while(tokens.hasMoreTokens()) {
            parts.add(makeContinuousPalette(tokens.nextToken()));            
        }
        out.setPalette((ContinuousPalette[])parts.toArray());
        return out;
    }
                    
    
    /**
     * A helper method for converting strings to numbers, without 
     * foreknowledge of whether they are in decimal of hexidecimal 
     * format.
     * 
     * @param in - a string assumed to represent a number.
     * @return the int value represented by in
     */
    private static int parseDecimalOrHex(String in) {
        if(in.startsWith("0x")) {
            return Integer.valueOf(in, 0x10);
        } else {
            return Integer.parseInt(in);
        }
    }
    
}
