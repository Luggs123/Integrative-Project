package pkg_main;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import pkg_EM.ClsEle;
import pkg_M.ClsColl;
import pkg_M.ClsProj;

// Button Handlers
public class ClsHandlers implements EventHandler<MouseEvent> {

	@Override
	public void handle(MouseEvent event) {
		Object source = event.getSource();
		
		// Projectile Motion
		if (source == ClsProj.btnStart) {
			ClsProj.doBtnStart();
			
		} else if (source == ClsProj.btnDone) {
			ClsProj.doBtnDone();
			
		} else if (source == ClsProj.btnPause) {
			ClsProj.doBtnPause();
			
		} else if (source == ClsProj.btnReset) {
			ClsProj.doBtnReset();
		
		} else if (source == ClsProj.btnHelp) {
			ClsProj.doBtnHelp();
		
		// Collisions
		} else if (source == ClsColl.btnStart) {
			ClsColl.doBtnStart();
			
		} else if (source == ClsColl.btnDone) {
			ClsColl.doBtnDone();
			
		} else if (source == ClsColl.btnPause) {
			ClsColl.doBtnPause();
			
		} else if (source == ClsColl.btnReset) {
			ClsColl.doBtnReset();
		
		} else if (source == ClsColl.btnHelp) {
			ClsColl.doBtnHelp();
		}
	}
}

// Menu Item Handlers
class ClsMenuHandler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		Object source = event.getSource();
		
		// Mechanics.
		if (source == ClsMain.menuProj) {
			ClsMain.updatePane(ClsProj.drawScene());
			
		} else if (source == ClsMain.menuColl) {
			ClsMain.updatePane(ClsColl.drawScene());

		} else if (source == ClsMain.menuEle) {
			ClsMain.updatePane(ClsEle.drawScene());
			
		}	else if (source == ClsMain.menuExit) {
			Platform.exit();
			
		}
	}
}