package jaredbgreat.procgenlab.viewer.logic.parameters;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 *
 * @author Jared Blackburn
 */
public interface IParameter {
    /**
     * This will return the GUI component (widget) used to represent this
     * parameter.  This is used to add and remove the component to the side 
     * panel when a generator is selected.
     * 
     * @return JComponent for the parameter
     */
    public JComponent getComponent();
    /**
     * This will return the label to identify the parameters component
     * in the GUI.
     * 
     * @return JLabel naming the component
     */
    public JLabel getLabel();
    /**
     * This will return the contents of the component as a string delimited 
     * by specific non-printable characters.  The format of the string is
     * 
     * type name data
     * 
     * With the record separator (RS) character between each.  For multi-part 
     * data fields the unit separator (US) character is used between specific 
     * items in the list.  Note that is is intended only for internal use in 
     * communication between components and generators, and not for external 
     * storage.  The use of non-printable control characters in the strings 
     * is to allow any ordinary character to be used in names without the need 
     * for complex parsing during tokenization; it also allow for a hierarchal 
     * sequence of tokenizations to easily separate relevant groupings.
     * 
     * The delimets can be found in the class:
     * jaredbgreat.procgenlab.util.Delims
     * 
     * @return A string containing data type, name, and the contents gui widget
     */
    public String getSetting();
    void set(String setting);
}
