package pkg_Coll;

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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
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

public class ClsColl implements ICollisions, pkg_main.IConstants {
	
	// Animation Properties
	public static AnimationTimer mainLoop;
	private static Cart cart1;
	private static Cart cart2;
	private static boolean isPaused;
	private static long initialTime;
	private static long pauseTime;
	
	// Windows
	private static VBox winColl;
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
	private static AppTextField txtM1;
	private static AppTextField txtM2;
	private static AppTextField txtVel;
	
	
	// Radio Buttons
	private static ToggleGroup rdbtnGroup;
	private static RadioButton rdbtnEla;
	private static RadioButton rdbtnInEla;
	
	// Labels
	private static HelpLabel lblHelp;
	
	// Charts
	private static LineChart<Number, Number> chrtVel;
	private static XYChart.Series<Number, Number> seriesCart1;
	private static XYChart.Series<Number, Number> seriesCart2;
	private static long elapsedTime;
	private static long timeUntilGraph;
	private static FloatProperty previousPosCart1;
	private static FloatProperty previousPosCart2;
	
	public static Pane drawScene() {
		
		// Main VBox.
		winColl = new VBox();
		
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
		Label lblM1 = new Label("Mass of Cart 1 (kg): ");
		Label lblM2 = new Label("Mass of Cart 2 (kg): ");
		Label lblVel = new Label("Initial Velocity (m/s): ");
		lblM1.setTextAlignment(TextAlignment.RIGHT);
		lblM2.setTextAlignment(TextAlignment.RIGHT);
		lblVel.setTextAlignment(TextAlignment.RIGHT);
		lblM1.setTextFill(Color.WHITE);
		lblM2.setTextFill(Color.WHITE);
		lblVel.setTextFill(Color.WHITE);

		txtM1 = new AppTextField("Cart 1 Mass");
		txtM2 = new AppTextField("Cart 2 Mass");
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
		vLabels.getChildren().addAll(lblM1, lblM2, lblVel);
		
		vFields.setAlignment(Pos.CENTER);
		vFields.getChildren().addAll(txtM1, txtM2, txtVel);
		
		rdbtnGroup = new ToggleGroup();
		rdbtnEla = new RadioButton("Elastic");
		rdbtnEla.setToggleGroup(rdbtnGroup);
		rdbtnEla.setSelected(true);
		rdbtnInEla = new RadioButton("Inelastic");
		rdbtnInEla.setToggleGroup(rdbtnGroup);
		
		HBox buttonLayout1 = new HBox(15);
		HBox buttonLayout2 = new HBox(15);
		HBox buttonLayout3 = new HBox();
		buttonLayout1.setAlignment(Pos.BOTTOM_CENTER);
		buttonLayout2.setAlignment(Pos.BOTTOM_CENTER);
		buttonLayout3.setAlignment(Pos.BOTTOM_CENTER);
		
		buttonLayout1.getChildren().addAll(btnStart, btnDone, btnPause);
		buttonLayout2.getChildren().addAll(btnReset, btnHelp);
		buttonLayout3.getChildren().addAll(rdbtnEla, rdbtnInEla);
		
		vButtons.setPadding(new Insets(10));
		vButtons.setAlignment(Pos.CENTER);
		vButtons.getChildren().addAll(buttonLayout1, buttonLayout2, buttonLayout3);
		
		winButt.setPrefWidth(5 * WINDOW_WIDTH / 8);
		winButt.getChildren().addAll(vLabels, vFields, vButtons);
		
		// Setup info window.
		final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time (Milliseconds)");
        yAxis.setLabel("Velocity (m/s)");
        
        chrtVel = new LineChart<>(xAxis, yAxis);
        chrtVel.setAnimated(false);
        chrtVel.setCreateSymbols(false);
		
		seriesCart1 = new XYChart.Series<>();
		seriesCart2 = new XYChart.Series<>();
		chrtVel.getData().addAll(seriesCart1, seriesCart2);
		
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
		
		winColl.getChildren().addAll(winDisplay, separator, winHelp);
		
		isPaused = true;
		
		return winColl;
	}
	
	// User presses btnStart.
	public static void doBtnStart() {
		float massCart1 = 0f;
		float massCart2 = 0f;
		float initVel = 0f;
		
		// Get the user inputed values and return if any of the inputs are invalid.
		if (!txtM1.tryGetFloat()) {
			return;
		}
		
		massCart1 = Float.parseFloat(txtM1.getText());
		
		if (massCart1 <= 0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Input Value Error!");
			alert.setHeaderText(null);
			alert.setContentText("The value inputed for the Mass of Cart 1 must be a positive number.");

			alert.showAndWait();
			return;
		}
		
		if (!txtM2.tryGetFloat()) {
			return;
		}
		
		massCart2 = Float.parseFloat(txtM2.getText());
		
		if (massCart2 <= 0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Input Value Error!");
			alert.setHeaderText(null);
			alert.setContentText("The value inputed for the Mass of Cart 2 must be a positive number.");

			alert.showAndWait();
			return;
		}
		
		if (!txtVel.tryGetFloat()) {
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
		seriesCart1.getData().clear();
		seriesCart2.getData().clear();
		
		Point2D initialPos = new Point2D(0, (WINDOW_HEIGHT / 2));
		Point2D initialVel = new Point2D(initVel, 0);
		Image ballImg = new Image(ClsMain.resourceLoader("Collisions/Cart.png"));
		
		cart1 = new Cart(initialPos, initialVel, ballImg, massCart1);
		cart1.setPosition(cart1.getPosition().subtract(0, cart1.getImageView().getFitHeight() * 0.75));
		cart1.update();
		
		cart2 = new Cart(initialPos, Point2D.ZERO, ballImg, massCart2);
		cart2.setVelocity(new Point2D((initVel * cart1.getMass()) / cart2.getMass(), 0));
		cart2.setPosition(cart2.getPosition().subtract(-WINDOW_WIDTH / 2, cart2.getImageView().getFitHeight() * 0.75));
		cart2.update();
		
		// Initialize graph data.
		elapsedTime = 0;
		timeUntilGraph = 0;
		previousPosCart1 = new SimpleFloatProperty(0f);
		previousPosCart2 = new SimpleFloatProperty(WINDOW_WIDTH / 2);
		
		Group dispGroup = new Group(cart1.getImageView(), cart2.getImageView());
		winDisplay.getChildren().clear();
		winDisplay.getChildren().add(dispGroup);
		
		// Disable start button and enable the rest.
		btnStart.setDisable(true);
		btnDone.setDisable(false);
		btnPause.setDisable(false);
		btnReset.setDisable(false);
		
		lblHelp.setHelpText(HELP_START);
		
		// Generate main animation loop.
		isPaused = false;
		cart1.setMoving(true);
		mainLoop = new AnimationTimer() {

			@Override
			public void handle(long now) {
				// Check if the animation is paused before doing any calculations.
				if (!isPaused) {
					
					// Check which cart is moving.
					if (cart1.isMoving() ^ cart2.isMoving()) {
						if (cart1.isMoving()) {
							
							// Check if cart 1 has collided with cart 2.
							if (cart1.getRightBound() > cart2.getLeftBound()) {
								if (rdbtnEla.isSelected()) {
									lblHelp.setHelpText(HELP_COLLIDE_ELASTIC);
									cart1.setMoving(false);
									cart2.setMoving(true);
								} else {
									// Apply momentum to both carts based on their velocity and masses.
									lblHelp.setHelpText(HELP_COLLIDE_INELASTIC);
									cart2.setMoving(true);
									Point2D newVel = new Point2D(cart1.getVelocity().getX() * cart1.getMass()
											/ (cart1.getMass() + cart2.getMass()), 0);
									cart1.setVelocity(newVel);
									cart2.setVelocity(newVel);
								}
								
								return;
							}
							
							// Move cart 1.
							cart1.move();
							cart1.update();
						} else if (cart2.isMoving()) {
							// Check if cart 2 has exited the screen.
							if (cart2.getLeftBound() > WINDOW_WIDTH) {
								lblHelp.setHelpText(HELP_COMPLETE);
								btnStart.setDisable(false);
								btnDone.setDisable(true);
								btnPause.setDisable(true);
								btnReset.setDisable(true);
								mainLoop.stop();
							}
							
							// Move cart 2.
							cart2.move();
							cart2.update();
						}
					} else if (cart1.isMoving() && cart2.isMoving()) {
						// Check if cart 1 has exited the screen.
						if (cart1.getLeftBound() > WINDOW_WIDTH) {
							lblHelp.setHelpText(HELP_COMPLETE);
							btnStart.setDisable(false);
							btnDone.setDisable(true);
							btnPause.setDisable(true);
							btnReset.setDisable(true);
							mainLoop.stop();
						}
						
						// Move cart 1 and cart 2 at the same time.
						cart1.move();
						cart1.update();
						cart2.move();
						cart2.update();
					}
					
					// Graph velocity on chart.
					elapsedTime = System.currentTimeMillis() - initialTime;
					
					if (timeUntilGraph < elapsedTime) {
						if (cart1.isMoving()) {
							timeUntilGraph += GRAPHING_DELAY;
							// Get the instantaneous velocity.
							FloatProperty positionCart1 = new SimpleFloatProperty();
							positionCart1.setValue(cart1.getPosition().getX());
							
							NumberBinding velocityCart1 = positionCart1.subtract(previousPosCart1).divide(GRAPHING_DELAY);
							
							
							XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(elapsedTime, velocityCart1.getValue().floatValue());
							
							seriesCart1.getData().add(dataPoint);
							previousPosCart1 = positionCart1;
						} else if (cart2.isMoving()) {
							timeUntilGraph += GRAPHING_DELAY;
							// Get the instantaneous velocity.
							FloatProperty positionCart2 = new SimpleFloatProperty();
							positionCart2.setValue(cart2.getPosition().getX());
							
							NumberBinding velocityCart2 = positionCart2.subtract(previousPosCart2).divide(GRAPHING_DELAY);
							
							
							XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(elapsedTime, velocityCart2.getValue().floatValue());
							
							seriesCart2.getData().add(dataPoint);
							previousPosCart2 = positionCart2;
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
		seriesCart1.getData().clear();
		seriesCart2.getData().clear();
		previousPosCart1.setValue(0);
		previousPosCart2.setValue(WINDOW_WIDTH / 2);
		
		// Enable start button and disable the animation buttons.
		btnStart.setDisable(false);
		btnDone.setDisable(true);
		btnPause.setDisable(true);
		btnReset.setDisable(true);
		
		mainLoop.stop();
		lblHelp.setText(HELP_DONE);
	}
	
	// User presses btnPause.
	public static void doBtnPause() {
		isPaused = !isPaused;
		
		if (isPaused) {
			btnPause.setText("Resume");
			btnDone.setDisable(true);
			mainLoop.stop();
			lblHelp.setText(HELP_PAUSE);
			pauseTime = System.currentTimeMillis();
		} else {
			btnPause.setText("Pause");
			btnDone.setDisable(false);
			mainLoop.start();
			lblHelp.setText(HELP_RESUME);
			initialTime += System.currentTimeMillis() - pauseTime;
		}
	}
	
	// User presses btnReset.
	public static void doBtnReset() {
		doBtnDone();
		doBtnStart();
		lblHelp.setText(HELP_RESET);
	}
	
	// User presses btnReset.
	public static void doBtnHelp() {
		// Displays an information alert.
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(null);
		alert.setHeaderText(null);
		alert.setContentText("This animation demonstrates the transfer of momentum between two carts undergoing elastic and inelastic collision."
				+ NEWLINE + NEWLINE + "The elastic collision will cause cart 1's momentum to be transferred to cart 2."
				+ NEWLINE + NEWLINE + "The inelastic collision will cause cart 1 to chain to cart 2, with both their masses now affecting velocity.");

		alert.showAndWait();
	}
}