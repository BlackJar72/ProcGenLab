package jaredbgreat.procgenlab.viewer;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

import jaredbgreat.procgenlab.viewer.logic.StatsHelper;
import jaredbgreat.procgenlab.viewer.logic.WorldMap;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

/**
 *
 * @author Jared Blackburn
 */
public class ViewPanel extends JTabbedPane {
    
    
    public void setWorldMap(WorldMap map) {
        removeAll();
        int size = map.getSize();
        if((map != null) && (size > 0)) {
            for(int i = 0; i < size; i++) {
                //System.out.println(map.getImage(i));
                MapPanel img = new MapPanel(map.getImage(i));
                img.setName(map.getImageName(i));
                add(img);
            }
        }
    }
    
    
    public void setStatsView(StatsHelper stats) {
        removeAll();
        JTextArea img = new JTextArea();
        img.setName("Time Profiling Statistics");
        img.setText(stats.getStatsText());
        img.setEditable(false);
        add(img);
    }
    
}
