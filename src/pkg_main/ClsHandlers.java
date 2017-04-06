package pkg_main;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import pkg_M.ClsProj;

public class ClsHandlers implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		Object source = event.getSource();
		/*** Menu Items ***/
		
		if (source == ClsMain.test) {
			Platform.exit();
			
		} else if (source == ClsMain.menuProj) {
			ClsMain.updatePane(ClsProj.drawScene());
			
		} else if (source == ClsProj.btnStart) {
			ClsProj.doBtnStart();	
		} else if (source == ClsProj.btnPause) {
			ClsProj.doBtnPause();	
		}
	}
}
