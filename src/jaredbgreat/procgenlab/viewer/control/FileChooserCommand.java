package jaredbgreat.procgenlab.viewer.control;

/*
 * Copyright (C) Jared Blackburn 2017
 *
 * Currently under the Creative Commons Attribution License version 4.0:  
 * https://creativecommons.org/licenses/by/4.0/legalcode
 */

import jaredbgreat.procgenlab.loader.JarLoader;
import jaredbgreat.procgenlab.viewer.MainWindow;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author jared
 */
public class FileChooserCommand implements ICommand {

    @Override
    public void execute() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = 
                new FileNameExtensionFilter("Java Files", "jar", "zip", "class");
        chooser.setFileFilter(filter);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setMultiSelectionEnabled(true);
        chooser.setDialogTitle("Open Java File (jar, class, or zip)");
        int result = chooser.showOpenDialog(MainWindow.getMainWindow());
        if(result == JFileChooser.APPROVE_OPTION) {
            File[] files = chooser.getSelectedFiles();
            for(int i = 0; i < files.length; i++) {
                JarLoader.loadFile(files[i]);
            }
        }
    }
    
}
