package jaredbgreat.procgenlab.viewer;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

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
    List<JComponent> controls;
    
    
        SidePanel() {
            super();
            controls = new ArrayList<>();
            setBorder(BorderFactory.createRaisedBevelBorder());
            layout = new BoxLayout(this, BoxLayout.Y_AXIS);
            setLayout(layout);
        }
        
        
        public void setControls(List<JComponent> in) {
            controls.stream().forEach(this::remove);
            controls.clear();
            controls.addAll(in);
            controls.stream().forEach(this::add);
            repaint();
        }
}
