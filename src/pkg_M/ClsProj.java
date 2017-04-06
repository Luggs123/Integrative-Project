package pkg_M;

import javafx.animation.AnimationTimer;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
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
	private static boolean isPaused;
	
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
	private static AppTextField txtAng;
	private static AppTextField txtGrav;
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
		winDisplay.setPrefWidth(WINDOW_WIDTH);
		winDisplay.setPrefHeight(WINDOW_HEIGHT / 2);
		
		// Setup button window.
		Label lblAng = new Label("Angle of Launch (Degrees):");
		Label lblGrav = new Label("Gravitational Constant: ");
		Label lblVel = new Label("Initial Velocity: ");
		lblAng.setTextAlignment(TextAlignment.RIGHT);
		lblGrav.setTextAlignment(TextAlignment.RIGHT);
		lblVel.setTextAlignment(TextAlignment.RIGHT);

		txtAng = new AppTextField("Angle of Launch");
		txtGrav = new AppTextField("Gravitational Constant");
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
		
		vLabels.getChildren().addAll(lblAng, lblGrav, lblVel);
		vFields.getChildren().addAll(txtAng, txtGrav, txtVel);
		
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
		
		isPaused = true;
		
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
		int launchAngle = 0;
		float gravityConst = 0f;
		float initVel = 0f;
		
		// TODO: Figure out max values.
		// Get the user inputed values and return if any of the inputs are invalid.
		if (
				!(txtAng.tryGetInt()
				&& txtGrav.tryGetFloat()
				&& txtVel.tryGetFloat())
				)
		{
			return;
		}
		
		launchAngle = Integer.parseInt(txtAng.getText());
		gravityConst = Float.parseFloat(txtGrav.getText());
		initVel = Float.parseFloat(txtVel.getText());
		
		if (launchAngle < 0 || launchAngle > 90) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Input Value Error!");
			alert.setHeaderText(null);
			alert.setContentText("The value inputed for the Angle of Launch must be between 0 and 90 degrees.");

			alert.showAndWait();
			return;
		}
		
		if (gravityConst <= 0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Input Value Error!");
			alert.setHeaderText(null);
			alert.setContentText("The value inputed for the Gravitational Constant must be a number greater than 0.");

			alert.showAndWait();
			return;
		}
		
		if (initVel <= 0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Input Value Error!");
			alert.setHeaderText(null);
			alert.setContentText("The value inputed for the Initial Velocity must be a number greater than 0.");

			alert.showAndWait();
			return;
		}
		
		Point2D initialPos = new Point2D(40, WINDOW_HEIGHT / 2);
		Point2D initialVel = new Point2D(initVel * Math.cos(launchAngle * DEG_TO_RAD), -initVel * Math.sin(launchAngle * DEG_TO_RAD));
		
		cannonBall = new Ball(initialPos, initialVel, new Image(ClsMain.resourceLoader("Sphere.png")));
		cannonBall.update();
		
		Group dispGroup = new Group(cannonBall.getImageView());
		winDisplay.getChildren().clear();
		winDisplay.getChildren().add(dispGroup);
		redrawScene();
		
		Point2D acceleration = new Point2D(0, gravityConst);
		
		isPaused = false;
		mainLoop = new AnimationTimer() {

			@Override
			public void handle(long now) {
				// Check if the animation is paused or finished.
				if (btnDone.isPressed()) {
					winDisplay.getChildren().clear();
					mainLoop.stop();
				} else if (btnPause.isPressed() && btnPause.getText().equals("Pause")) {
					btnDone.setDisable(true);
					btnPause.setText("Resume");
					mainLoop.stop();
				}
				
				if (!isPaused) {
					// Apply gravitational acceleration.
					cannonBall.applyForce(acceleration);
					cannonBall.move();
					cannonBall.update();
					redrawScene();
				}
			}
		};
		
		mainLoop.start();
	}
	
	public static void doBtnPause() {
		if (btnPause.getText().equals("Resume")) {
			btnPause.setText("Pause");
			btnDone.setDisable(false);
			mainLoop.start();
		}
	}
}
