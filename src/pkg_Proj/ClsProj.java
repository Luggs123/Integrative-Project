package pkg_Proj;

import javafx.animation.AnimationTimer;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
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
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import pkg_main.AppButton;
import pkg_main.AppTextField;
import pkg_main.ClsMain;
import pkg_main.HelpLabel;

public class ClsProj implements IProjectile, pkg_main.IConstants {
	
	// Animation Properties
	public static AnimationTimer mainLoop;
	private static Ball cannonBall;
	private static boolean isPaused;
	private static long initialTime;
	private static long pauseTime;
	
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
	private static HelpLabel lblHelp;
	
	// Charts
	private static LineChart<Number, Number> chrtVel;
	private static XYChart.Series<Number, Number> seriesVel;
	private static long elapsedTime;
	private static long timeUntilGraph;
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
		
		// Setup animation window.
		winDisplay.setPrefWidth(WINDOW_WIDTH);
		winDisplay.setPrefHeight(WINDOW_HEIGHT / 2);
		
		// Setup button window.
		Label lblAng = new Label("Angle of Launch (Degrees):");
		Label lblGrav = new Label("Gravitational Constant (m/s^2): ");
		Label lblVel = new Label("Initial Velocity (m/s): ");
		lblAng.setTextAlignment(TextAlignment.RIGHT);
		lblGrav.setTextAlignment(TextAlignment.RIGHT);
		lblVel.setTextAlignment(TextAlignment.RIGHT);
		lblAng.setTextFill(Color.WHITE);
		lblGrav.setTextFill(Color.WHITE);
		lblVel.setTextFill(Color.WHITE);

		txtAng = new AppTextField("Angle of Launch");
		txtGrav = new AppTextField("Gravitational Constant");
		txtVel = new AppTextField("Initial Velocity");
		
		btnStart = new AppButton("Start");
		btnDone = new AppButton("Done");
		btnPause = new AppButton("Pause");
		btnReset = new AppButton("Reset");
		btnHelp = new AppButton("Help");
		
		btnDone.setDisable(true);
		btnPause.setDisable(true);
		btnReset.setDisable(true);
		
		// Add buttons and labels to winButt.
		VBox vLabels = new VBox(35);
		VBox vFields = new VBox(30);
		VBox vButtons = new VBox(20);
		
		vLabels.setAlignment(Pos.CENTER_RIGHT);
		vLabels.setPadding(new Insets(15));
		vLabels.getChildren().addAll(lblAng, lblGrav, lblVel);
		
		vFields.setAlignment(Pos.CENTER);
		vFields.getChildren().addAll(txtAng, txtGrav, txtVel);
		
		HBox buttonLayout1 = new HBox(15);
		HBox buttonLayout2 = new HBox(15);
		buttonLayout1.setAlignment(Pos.BOTTOM_CENTER);
		buttonLayout2.setAlignment(Pos.BOTTOM_CENTER);
		
		buttonLayout1.getChildren().addAll(btnStart, btnDone, btnPause);
		buttonLayout2.getChildren().addAll(btnReset, btnHelp);
		
		vButtons.setPadding(new Insets(10));
		vButtons.setAlignment(Pos.CENTER);
		vButtons.getChildren().addAll(buttonLayout1, buttonLayout2);
		
		winButt.setPrefWidth(5 * WINDOW_WIDTH / 8);
		winButt.getChildren().addAll(vLabels, vFields, vButtons);
		
		// Setup info window.
		final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time (Milliseconds)");
        yAxis.setLabel("Velocity (m/s)");
        
        chrtVel = new LineChart<Number, Number>(xAxis, yAxis);
        chrtVel.setAnimated(false);
        chrtVel.setCreateSymbols(false);
		
		seriesVel = new XYChart.Series<Number, Number>();
		chrtVel.getData().add(seriesVel);
		
		winInfo.setPrefWidth(3 * WINDOW_WIDTH / 8);
		winInfo.getChildren().addAll(chrtVel);
		
		// Help window.
		lblHelp = new HelpLabel(TITLE);
		lblHelp.setTextFill(Color.WHITE);
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
	
	// User presses btnStart.
	public static void doBtnStart() {
		int launchAngle = 0;
		float gravityConst = 0f;
		float initVel = 0f;
		
		// Get the user inputed values and return if any of the inputs are invalid.
		if (!txtAng.tryGetInt())
		{
			return;
		}
		
		launchAngle = Integer.parseInt(txtAng.getText());
		
		if (launchAngle < 0 || launchAngle > 90) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Input Value Error!");
			alert.setHeaderText(null);
			alert.setContentText("The value inputed for the Angle of Launch must be between 0 and 90 degrees.");

			alert.showAndWait();
			return;
		}
		
		if (!txtGrav.tryGetFloat())
		{
			return;
		}
		
		gravityConst = Float.parseFloat(txtGrav.getText());
		
		if (gravityConst <= 0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Input Value Error!");
			alert.setHeaderText(null);
			alert.setContentText("The value inputed for the Gravitational Constant must be a number greater than 0.");

			alert.showAndWait();
			return;
		}
		
		if (!txtVel.tryGetFloat())
		{
			return;
		}
		
		initVel = Float.parseFloat(txtVel.getText());
		
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
		seriesVel.getData().clear();
		
		Point2D initialPos = new Point2D(40, (WINDOW_HEIGHT / 2));
		Point2D initialVel = new Point2D(initVel * Math.cos(launchAngle * DEG_TO_RAD), -initVel * Math.sin(launchAngle * DEG_TO_RAD));
		Image ballImg = new Image(ClsMain.resourceLoader("ProjMotion/Sphere.png"));
		
		cannonBall = new Ball(initialPos, initialVel, ballImg);
		cannonBall.setPosition(cannonBall.getPosition().subtract(0, cannonBall.getImageView().getFitHeight()));
		cannonBall.update();
		
		// Initialize graph data.
		elapsedTime = 0;
		timeUntilGraph = 0;
		previousPos = new SimpleFloatProperty((float) (WINDOW_HEIGHT / 2 - cannonBall.getPosition().getY()));
		
		Group dispGroup = new Group(cannonBall.getImageView());
		winDisplay.getChildren().clear();
		winDisplay.getChildren().add(dispGroup);
		
		// Get acceleration vector.
		Point2D acceleration = new Point2D(0, gravityConst);
		
		// Disable start button and enable the rest.
		btnStart.setDisable(true);
		btnDone.setDisable(false);
		btnPause.setDisable(false);
		btnReset.setDisable(false);
		
		lblHelp.setHelpText(HELP_START);
		
		// Generate main animation loop.
		isPaused = false;
		mainLoop = new AnimationTimer() {

			@Override
			public void handle(long now) {
				// Check if the animation is paused before doing any calculations.
				if (!isPaused) {
					// Check if the ball has reached the bottom of the screen.
					if (cannonBall.getPosition().getY() > ((WINDOW_HEIGHT / 2) - (cannonBall.getImageView().getFitHeight()))) {
						lblHelp.setHelpText(HELP_COMPLETE);
						btnStart.setDisable(false);
						btnDone.setDisable(true);
						btnPause.setDisable(true);
						btnReset.setDisable(true);
						mainLoop.stop();
					} else {
						// Check if the ball has exceeded the screen's dimensions.
						if (cannonBall.getLowerBound() < 0 || cannonBall.getLeftBound() > WINDOW_WIDTH) {
							lblHelp.setHelpText(HELP_OOB);	
						} else if (lblHelp.getText().endsWith(HELP_OOB)) {
							lblHelp.setHelpText(HELP_OOB_RETURN);
						}
						
						// Apply gravitational acceleration.
						cannonBall.applyForce(acceleration);
						cannonBall.getImageView().setRotate(cannonBall.getImageView().getRotate() + cannonBall.getVelocity().getX() * 10);
						cannonBall.move();
						cannonBall.setAcceleration(Point2D.ZERO);
						cannonBall.update();
						
						// Graph the current data.
						elapsedTime = System.currentTimeMillis() - initialTime;
						
						if (timeUntilGraph < elapsedTime) {
							timeUntilGraph += GRAPHING_DELAY;
							
							// Get the instantaneous velocity.
							FloatProperty position = new SimpleFloatProperty();
							position.setValue((WINDOW_HEIGHT / 2) - cannonBall.getPosition().getY());
							
							NumberBinding velocity = position.subtract(previousPos).divide(GRAPHING_DELAY);
							XYChart.Data<Number, Number> dataPoint = new XYChart.Data<Number, Number>(elapsedTime, velocity.getValue().floatValue());
							
							seriesVel.getData().add(dataPoint);
							previousPos = position;
						}
					}
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
		previousPos.setValue(WINDOW_HEIGHT / 2);
		
		// Enable start button and disable the animation buttons.
		btnStart.setDisable(false);
		btnDone.setDisable(true);
		btnPause.setDisable(true);
		btnReset.setDisable(true);
		
		mainLoop.stop();
		lblHelp.setHelpText(HELP_DONE);
	}
	
	// User presses btnPause.
	public static void doBtnPause() {
		isPaused = !isPaused;
		
		if (isPaused) {
			btnPause.setText("Resume");
			btnDone.setDisable(true);
			mainLoop.stop();
			lblHelp.setHelpText(HELP_PAUSE);
			pauseTime = System.currentTimeMillis();
		} else {
			btnPause.setText("Pause");
			btnDone.setDisable(false);
			mainLoop.start();
			lblHelp.setHelpText(HELP_RESUME);
			initialTime += System.currentTimeMillis() - pauseTime;
		}
	}
	
	// User presses btnReset.
	public static void doBtnReset() {
		doBtnDone();
		doBtnStart();
		lblHelp.setHelpText(HELP_RESET);
	}
	
	// User presses btnHelp.
	public static void doBtnHelp() {
		// Displays an information alert.
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(null);
		alert.setHeaderText(null);
		alert.setContentText("This animation will launch a cannon ball at a specified angle and velocity."
				+ NEWLINE + NEWLINE + "The user may also select the gravitational constant to affect the magnitude of the ball's downward acceleration.");

		alert.showAndWait();
	}
}
