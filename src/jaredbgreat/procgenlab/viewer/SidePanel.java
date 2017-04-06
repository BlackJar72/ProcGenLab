package jaredbgreat.procgenlab.viewer;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

import jaredbgreat.procgenlab.viewer.control.GenerateCommand;
import jaredbgreat.procgenlab.viewer.logic.parameters.IParameter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author Jared Blackburn
 */
public class SidePanel extends JPanel {
    BoxLayout layout;
    List<IParameter> controls;
    
    
        SidePanel() {
            super();
            controls = new ArrayList<>();
            setBorder(BorderFactory.createRaisedBevelBorder());
            layout = new BoxLayout(this, BoxLayout.Y_AXIS);
            setLayout(layout);
        }
        
        
        public void setControls(List<IParameter> in) {
            for(IParameter param : controls) {
                remove(param.getComponent());
            }
            controls.clear();
            controls.addAll(in);
            for(IParameter param : controls) {
                add(param.getComponent());
            }
            GenerateCommand.setParameters(controls);
            repaint();
        }
}
