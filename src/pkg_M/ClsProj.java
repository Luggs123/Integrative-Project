package pkg_M;

import javafx.beans.property.DoubleProperty;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import pkg_main.AppButton;
import pkg_main.AppTextField;

public class ClsProj implements IProjectile, pkg_main.IConstants {
	
	// Buttons
	public static AppButton btnStart;
	public static AppButton btnDone;
	public static AppButton btnPause;
	public static AppButton btnReset;
	public static AppButton btnHelp;
	
	// Text Fields
	private static AppTextField txtGrav;
	private static AppTextField txtAng;
	private static AppTextField txtVel;
	
	// Charts
	private static LineChart<DoubleProperty, DoubleProperty> chrtVel;
	private static LineChart<DoubleProperty, DoubleProperty> chrtAcc;
	
	public static Pane drawScene() {
		
		// Main VBox.
		VBox winProj = new VBox();
		
		// Sub Windows
		Pane winDisplay = new Pane();
		Pane winButt = new Pane();
		Pane winInfo = new Pane();
		Pane winHelp = new Pane();
		Label cringeuh = new Label("idkf");
		
		// Add all the panes to the main window.
		HBox separator = new HBox(cringeuh);
		separator.getChildren().addAll(winButt, winInfo);
		
		winProj.getChildren().addAll(winDisplay, separator, winHelp);
		
		return winProj;
	}
}
