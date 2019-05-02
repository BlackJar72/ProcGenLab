package jaredbgreat.procgenlab.viewer.control;

import javax.swing.JComboBox;

import jaredbgreat.procgenlab.viewer.MainWindow;
import jaredbgreat.procgenlab.viewer.TopPanel;

public class SelectCategoryCommand implements ICommand {
    private static JComboBox<String> source;

	@Override
	public void execute() {
		TopPanel panel = (TopPanel)MainWindow.getComponenent("TopPanel");
		panel.setCategory((String)source.getSelectedItem());
	}
    
    
    public static void setSource(JComboBox<String> src) {
        source = src;
    }

}
