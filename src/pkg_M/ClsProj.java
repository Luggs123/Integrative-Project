package pkg_M;

import javafx.animation.AnimationTimer;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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
	
	public static AnimationTimer mainLoop;
	private static Ball cannonBall;
	private static boolean isPaused;
	private static long initialTime;
	
	// Windows
	private static VBox winProj;
	private static Pane winDisplay;
	private static HBox winButt;
	private static HBox winInfo;
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
	
	// Labels
	private static Label lblHelp;
	
	// Charts
	private static LineChart<Number, Number> chrtVel;
	private static XYChart.Series<Number, Number> seriesVel;
	private static LongProperty elapsedTime;
	private static LongProperty timeUntilGraph;
	private static FloatProperty previousPos;
	
	public static Pane drawScene() {
		
		// Main VBox.
		winProj = new VBox();
		
		// Sub Windows
		winDisplay = new Pane();
		winButt = new HBox(30);
		winInfo = new HBox();
		winHelp = new Pane();
		
		// Add CSS classes.
		winDisplay.getStyleClass().add("win-display");
		winButt.getStyleClass().add("win-butt");
		winInfo.getStyleClass().add("win-info");
		winHelp.getStyleClass().add("win-help");
		
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
		txtAng.setText("45");
		txtGrav.setText("0.03");
		txtVel.setText("5");
		
		btnStart = new AppButton("Start");
		btnDone = new AppButton("Done");
		btnPause = new AppButton("Pause");
		btnReset = new AppButton("Reset");
		btnHelp = new AppButton("Help");
		
		btnDone.setDisable(true);
		btnPause.setDisable(true);
		btnReset.setDisable(true);
		
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
		
		// Setup info window.
		final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        chrtVel = new LineChart<Number, Number>(xAxis, yAxis);
        chrtVel.setAnimated(false);
		
		seriesVel = new XYChart.Series<Number, Number>();
		seriesVel.setName("Velocity over Time");
		chrtVel.getData().add(seriesVel);
		
		elapsedTime = new SimpleLongProperty();
		timeUntilGraph = new SimpleLongProperty();
		previousPos = new SimpleFloatProperty();
		
		winInfo.getChildren().addAll(chrtVel);
		
		// Help window.
		lblHelp = new Label();
		winHelp.setPrefHeight(WINDOW_HEIGHT / 16);
		winHelp.getChildren().add(lblHelp);
		
		// Add all the panes to the main window.
		final HBox separator = new HBox();
		separator.setPrefHeight(7 * WINDOW_HEIGHT / 16);
		separator.getChildren().addAll(winButt, winInfo);
		
		winProj.getChildren().addAll(winDisplay, separator, winHelp);
		
		isPaused = true;
		
		return winProj;
	}
	
	// Re-paint the scene in order to update the position of objects during the animation.
	private static void redrawScene() {
		HBox separator = new HBox();
		separator.setPrefHeight(7 * WINDOW_HEIGHT / 16);
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
		
		// Initialize position of the ball.
		initialTime = System.currentTimeMillis();
		
		Point2D initialPos = new Point2D(40, WINDOW_HEIGHT / 2);
		Point2D initialVel = new Point2D(initVel * Math.cos(launchAngle * DEG_TO_RAD), -initVel * Math.sin(launchAngle * DEG_TO_RAD));
		Image ballImg = new Image(ClsMain.resourceLoader("Sphere.png"));
		
		cannonBall = new Ball(initialPos, initialVel, ballImg);
		cannonBall.update();
		
		Group dispGroup = new Group(cannonBall.getImageView());
		winDisplay.getChildren().clear();
		winDisplay.getChildren().add(dispGroup);
		redrawScene();
		
		// Get acceleration vector.
		Point2D acceleration = new Point2D(0, gravityConst);
		
		// Disable start button and enable the rest.
		btnStart.setDisable(true);
		btnDone.setDisable(false);
		btnPause.setDisable(false);
		btnReset.setDisable(false);
		
		lblHelp.setText("Staring the animation.");
		
		// Generate main animation loop.
		isPaused = false;
		mainLoop = new AnimationTimer() {

			@Override
			public void handle(long now) {
				// TODO: Fix initialTime.
				if (!isPaused) {
					// Graph the current data.
					elapsedTime.setValue(System.currentTimeMillis() - initialTime);
					if (timeUntilGraph.getValue() < elapsedTime.getValue()) {
						timeUntilGraph.add(400);
						
						FloatProperty position = new SimpleFloatProperty();
						position.setValue((WINDOW_HEIGHT / 2) - cannonBall.getPosition().getY());
						
						NumberBinding velocity = position.subtract(previousPos).divide(400);
						
						seriesVel.getData().add(new XYChart.Data<Number, Number>(elapsedTime.getValue(), velocity.getValue()));
						previousPos = position;
					}
					
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
	
	// User presses btnDone.
	public static void doBtnDone() {
		// Clear all animation data.
		winDisplay.getChildren().clear();
		seriesVel.getData().clear();
		previousPos.setValue(0);
		
		// Enable start button and disable rest.
		btnStart.setDisable(false);
		btnDone.setDisable(true);
		btnPause.setDisable(true);
		btnReset.setDisable(true);
		
		mainLoop.stop();
	}
	
	// User presses btnPause.
	public static void doBtnPause() {
		isPaused = !isPaused;
		
		if (isPaused) {
			btnPause.setText("Resume");
			btnDone.setDisable(true);
			mainLoop.stop();
		} else {
			btnPause.setText("Pause");
			btnDone.setDisable(false);
			mainLoop.start();
		}
	}
	
	// User presses btnReset.
	public static void doBtnReset() {
		doBtnDone();
		doBtnStart();
	}
	
	// User presses btnReset.
	public static void doBtnHelp() {
		// Displays an information alert.
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(null);
		alert.setHeaderText(null);
		alert.setContentText("This animation will launch a cannon ball at a specified angle and velocity."
				+ NEWLINE + "The user may also select the gravitational constant to affect the magnitude of the ball's downward acceleration.");

		alert.showAndWait();
	}
}
