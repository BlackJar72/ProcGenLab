package jaredbgreat.procgenlab.util;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 *
 * (Note, actual codes are official ASCII standard, making the basic content
 *  of this class basically public domain.)
 */


/**
 * Constants to represent non-printable delimeters.
 * 
 * @author Jared Blackburn
 */
public class Delims {
    
    /*
     *  DELIMETERS AS CHARACTERS
     */
    /** File Seperator */
    public static final char FS  = '\u001C'; 
    /** Group Seperator */
    public static final char GS  = '\u001D'; 
    /** Record Seperator */
    public static final char RS  = '\u001E'; 
    /** Unit Seperator */
    public static final char US  = '\u001F'; 
    /** End of Text (non-null terminator) */
    public static final char ETX = '\u0003'; 
    
    
    
    /*
     *  DELIMETERS AS STRINGS
     */
    /** File Seperator as a String*/
    public static final String SFS  = String.valueOf(FS); 
    /** Group Seperator as a String*/
    public static final String SGS  = String.valueOf(GS); 
    /** Record Seperator as a String*/
    public static final String SRS  = String.valueOf(RS); 
    /** Unit Seperator as a String*/
    public static final String SUS  = String.valueOf(US); 
    /** End of Text (non-null terminator) as a String */
    public static final String SETX = String.valueOf(ETX); 
}
