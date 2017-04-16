package pkg_main;

import javafx.scene.control.Label;

// Class for the help text that appears at the bottom of each animation window.
public class HelpLabel extends Label {
	private String programTitle;
	
	public HelpLabel(String programTitle) {
		super(programTitle);
		this.programTitle = programTitle;
	}
	
	public void setHelpText(String text) {
		super.setText(programTitle + " - " + text);
	}
}
