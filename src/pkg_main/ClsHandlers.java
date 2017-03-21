package pkg_main;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ClsHandlers implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		Object source = event.getSource();
		/*** ClsMain ***/
		
		if (source == ClsMain.test) {
			Platform.exit();
		}
	}
}
