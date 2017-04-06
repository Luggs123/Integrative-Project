package pkg_M;

import javafx.animation.AnimationTimer;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import pkg_main.AppButton;
import pkg_main.AppTextField;
import pkg_main.ClsMain;

public class ClsProj implements IProjectile, pkg_main.IConstants {
	
	private static AnimationTimer mainLoop;
	private static Ball cannonBall;
	
	// Windows
	private static VBox winProj;
	private static Pane winDisplay;
	private static HBox winButt;
	private static Pane winInfo;
	private static Pane winHelp;
	
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
		winProj = new VBox();
		
		// Sub Windows
		winDisplay = new Pane();
		winButt = new HBox(30);
		winInfo = new Pane();
		winHelp = new Pane();
		
		winButt.setStyle("-fx-border-color: black");
		
		// Setup animation window.
		winDisplay.setMinWidth(WINDOW_WIDTH);
		winDisplay.setMinHeight(WINDOW_HEIGHT / 2);
		
		// Setup button window.
		Label lblGrav = new Label("Gravitational Constant: ");
		Label lblAng = new Label("Angle of Launch: ");
		Label lblVel = new Label("Initial Velocity: ");
		lblGrav.setTextAlignment(TextAlignment.RIGHT);
		lblAng.setTextAlignment(TextAlignment.RIGHT);
		lblVel.setTextAlignment(TextAlignment.RIGHT);
		
		txtGrav = new AppTextField("Gravitational Constant");
		txtAng = new AppTextField("Angle of Launch");
		txtVel = new AppTextField("Initial Velocity");
		
		btnStart = new AppButton("Start");
		btnDone = new AppButton("Done");
		btnPause = new AppButton("Pause");
		btnReset = new AppButton("Reset");
		btnHelp = new AppButton("Help");
		
		// Add buttons and labels to winButt.
		VBox vLabels = new VBox(30);
		VBox vFields = new VBox(30);
		VBox vButtons = new VBox(20);
		
		vLabels.getChildren().addAll(lblGrav, lblAng, lblVel);
		vFields.getChildren().addAll(txtGrav, txtAng, txtVel);
		vButtons.getChildren().addAll(btnStart, btnDone, btnPause);
		
		HBox buttonLayout1 = new HBox();
		HBox buttonLayout2 = new HBox();
		buttonLayout1.setAlignment(Pos.BOTTOM_CENTER);
		buttonLayout2.setAlignment(Pos.BOTTOM_CENTER);
		
		buttonLayout1.getChildren().addAll(btnStart, btnDone, btnPause);
		buttonLayout2.getChildren().addAll(btnReset, btnHelp);
		
		vButtons.setPadding(new Insets(35));
		vButtons.getChildren().addAll(buttonLayout1, buttonLayout2);
		
		winButt.setMinWidth(WINDOW_WIDTH / 2);
		winButt.getChildren().addAll(vLabels, vFields, vButtons);
		
		// Add all the panes to the main window.
		HBox separator = new HBox();
		separator.getChildren().addAll(winButt, winInfo);
		
		winProj.getChildren().addAll(winDisplay, separator, winHelp);
		
		return winProj;
	}
	
	private static void redrawScene() {
		HBox separator = new HBox();
		separator.getChildren().addAll(winButt, winInfo);
		
		winProj.getChildren().clear();
		winProj.getChildren().addAll(winDisplay, separator, winHelp);
		
		ClsMain.updatePane(winProj);
	}
	
	// User presses btnStart.
	public static void doBtnStart() {
		Point2D initialPos = new Point2D(0, 00);
		cannonBall = new Ball(initialPos, Point2D.ZERO, new Image(ClsMain.resourceLoader("Sphere.png")));
		cannonBall.update();
		
		Group dispGroup = new Group(cannonBall.getImageView());
		winDisplay.getChildren().clear();
		winDisplay.getChildren().add(dispGroup);
		redrawScene();
	}
}
