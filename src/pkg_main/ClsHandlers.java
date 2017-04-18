package pkg_main;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import pkg_Coll.ClsColl;
import pkg_Ele.ClsEle;
import pkg_Fld.ClsFld;
import pkg_Proj.ClsProj;

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
		
		// Electrostatic Force
		else if (source == ClsEle.btnStart) {
			ClsEle.doBtnStart();
			
		} else if (source == ClsEle.btnDone) {
			ClsEle.doBtnDone();
			
		} else if (source == ClsEle.btnPause) {
			ClsEle.doBtnPause();
			
		} else if (source == ClsEle.btnReset) {
			ClsEle.doBtnReset();
		
		} else if (source == ClsEle.btnHelp) {
			ClsEle.doBtnHelp();
			
		} else if (source == ClsEle.btnAdd) {
			ClsEle.doBtnAdd();
			
		} else if (source == ClsEle.btnSelect) {
			ClsEle.doBtnSelect();
			
		} else if (source == ClsEle.btnRemove) {
			ClsEle.doBtnRemove();
			
		}
		
		// Electrostatic Potential
				else if (source == ClsFld.btnStart) {
					ClsFld.doBtnStart();
					
				} else if (source == ClsFld.btnDone) {
					ClsFld.doBtnDone();
					
				} else if (source == ClsFld.btnPause) {
					ClsFld.doBtnPause();
					
				} else if (source == ClsFld.btnReset) {
					ClsFld.doBtnReset();
				
				} else if (source == ClsFld.btnHelp) {
					ClsFld.doBtnHelp();
					
				} else if (source == ClsFld.btnAdd) {
					ClsFld.doBtnAdd();
					
				} else if (source == ClsFld.btnSelect) {
					ClsFld.doBtnSelect();
					
				} else if (source == ClsFld.btnRemove) {
					ClsFld.doBtnRemove();
					
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

		// Electricity.
		} else if (source == ClsMain.menuEle) {
			ClsMain.updatePane(ClsEle.drawScene());
			
		} else if (source == ClsMain.menuFld) {
			ClsMain.updatePane(ClsFld.drawScene());
		
		// Other
		} else if (source == ClsMain.menuStr) {
			ClsMain.inConstruction("Waves on a String");
			
		} else if (source == ClsMain.menuOsci) {
			ClsMain.inConstruction("Oscillation");
			
		} else if (source == ClsMain.menuCred) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Integrative Project");
			alert.setHeaderText(null);
			alert.setContentText("Created by Paul Gaudnik, Mark Jarjour & Michael Luger");

			alert.showAndWait();
			
		} else if (source == ClsMain.menuExit) {
			Platform.exit();
			
		} else {
			ClsMain.inConstruction(null);
		}
	}
}