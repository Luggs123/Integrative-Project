package pkg_main;

import javafx.scene.control.MenuItem;

public class AppMenuItem extends MenuItem {
	
	public AppMenuItem(String displayText) {
		super(displayText);
		this.setOnAction(new ClsMenuHandler());
	}
	
	public AppMenuItem() {
		super();
		this.setOnAction(new ClsMenuHandler());
	}
}
