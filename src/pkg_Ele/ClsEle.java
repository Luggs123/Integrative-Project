package pkg_Ele;

import java.util.LinkedList;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import pkg_main.AppButton;
import pkg_main.AppTextField;
import pkg_main.ClsMain;

public class ClsEle implements pkg_main.IConstants, IElectrostatic {

	// Animation Properties
	public static AnimationTimer mainLoop;
	private static boolean isPaused;
	private static List<Particle> particles = new LinkedList<>();
	private static List<Particle> initialParticles = new LinkedList<>();
	private static boolean hasSelected = false;
	private static int selected = 0;
	private static double mouseX;
	private static double mouseY;
	private static float eleConst = 0f;

	// Windows
	private static VBox winEle;
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
	public static AppButton btnAdd;
	public static AppButton btnSelect;
	public static AppButton btnRemove;

	// Text Fields
	private static AppTextField txtEleConst;
	private static AppTextField txtCharge;
	private static AppTextField txtMass;

	// Labels
	private static Label lblHelp;

	// Data
	private static Label selectedVelocity = new Label();
	private static Label selectedAcceleration = new Label();
	private static Label selectedCharge = new Label();
	private static Label selectedMass = new Label();

	// Charge image.
	static Image chargeImg = new Image(ClsMain.resourceLoader("EleForce/Charge.png"));

	public static Pane drawScene() {

		// Main VBox.
		winEle = new VBox();

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
		Label lblEleConst = new Label("Coulomb's Constant: ");
		Label lblCharge = new Label("Particle Charge: ");
		Label lblMass = new Label("Mass: ");
		lblEleConst.setTextAlignment(TextAlignment.RIGHT);
		lblCharge.setTextAlignment(TextAlignment.RIGHT);
		lblMass.setTextAlignment(TextAlignment.RIGHT);
		lblEleConst.setTextFill(Color.WHITE);
		lblCharge.setTextFill(Color.WHITE);
		lblMass.setTextFill(Color.WHITE);

		//Text fields
		txtEleConst = new AppTextField("Coulomb's Constant");
		txtCharge = new AppTextField("Particle Charge");
		txtMass = new AppTextField("Mass");

		//Buttons
		btnStart = new AppButton("Start");
		btnDone = new AppButton("Done");
		btnPause = new AppButton("Pause");
		btnReset = new AppButton("Reset");
		btnHelp = new AppButton("Help");
		btnAdd = new AppButton("Add");
		btnSelect = new AppButton("Select");
		btnRemove = new AppButton("Remove");

		btnDone.setDisable(true);
		btnPause.setDisable(true);
		btnReset.setDisable(true);
		btnSelect.setDisable(true);
		btnRemove.setDisable(true);

		// Add buttons and labels to winButt.
		VBox vLabels = new VBox(30);
		VBox vFields = new VBox(30);
		VBox vButtons = new VBox(20);

		vLabels.setAlignment(Pos.CENTER_RIGHT);
		vLabels.setPadding(new Insets(15));
		vLabels.getChildren().addAll(lblEleConst, lblCharge, lblMass);

		vFields.setAlignment(Pos.CENTER);
		vFields.getChildren().addAll(txtEleConst, txtCharge, txtMass);

		HBox buttonLayout1 = new HBox();
		HBox buttonLayout2 = new HBox();
		HBox buttonLayout3 = new HBox();
		buttonLayout1.setAlignment(Pos.BOTTOM_CENTER);
		buttonLayout2.setAlignment(Pos.BOTTOM_CENTER);
		buttonLayout3.setAlignment(Pos.BOTTOM_CENTER);

		buttonLayout1.getChildren().addAll(btnStart, btnDone, btnPause);
		buttonLayout2.getChildren().addAll(btnReset, btnHelp);
		buttonLayout3.getChildren().addAll(btnAdd, btnSelect, btnRemove);		

		vButtons.setPadding(new Insets(80));
		vButtons.getChildren().addAll(buttonLayout1, buttonLayout2, buttonLayout3);

		winButt.setPrefWidth(WINDOW_WIDTH * 0.75);
		winButt.getChildren().addAll(vLabels, vFields, vButtons);

		// Setup info window.
		winInfo.setPrefWidth(WINDOW_WIDTH * 0.25);
		VBox infoContents = new VBox();

		HBox velBox = new HBox();
		HBox accBox = new HBox();
		HBox chrgBox = new HBox();
		HBox massBox = new HBox();

		infoContents.getChildren().addAll(velBox, accBox, chrgBox, massBox);
		winInfo.getChildren().add(infoContents);

		// Help window.
		lblHelp = new Label();
		winHelp.setPrefHeight(WINDOW_HEIGHT / 16);
		winHelp.getChildren().add(lblHelp);

		// Add all the panes to the main window.
		final HBox separator = new HBox();
		separator.setPrefHeight(7 * WINDOW_HEIGHT / 16);
		separator.getChildren().addAll(winButt, winInfo);

		winEle.getChildren().addAll(winDisplay, separator, winHelp);

		isPaused = true;

		return winEle;
	}

	// Re-paint the scene in order to update the position of objects during the animation.
	private static void redrawScene() {
		HBox separator = new HBox();

		winInfo.getChildren().clear();
		VBox infoContents = new VBox();
		if (hasSelected) {
			selectedVelocity.setText("Velocity: " + particles.get(selected).getVelocity().getX() * 1000 / 1 / 1000.0 + 
					", " + particles.get(selected).getVelocity().getY() * 1000 / 1 / 1000.0);
			selectedAcceleration.setText("Acceleration: " + particles.get(selected).getAcceleration().getX() + 
					", " + particles.get(selected).getAcceleration().getY());
			selectedCharge.setText("Charge: " + particles.get(selected).getCharge());
			selectedMass.setText("Mass: " + particles.get(selected).getMass());
		}
		HBox velBox = new HBox();
		HBox accBox = new HBox();
		HBox chrgBox = new HBox();
		HBox massBox = new HBox();

		velBox.getChildren().add(selectedVelocity);
		accBox.getChildren().add(selectedAcceleration);
		chrgBox.getChildren().add(selectedCharge);
		massBox.getChildren().add(selectedMass);

		infoContents.setPadding(new Insets(20));
		infoContents.getChildren().addAll(velBox, accBox, chrgBox, massBox);
		winInfo.getChildren().add(infoContents);

		separator.setPrefHeight(7 * WINDOW_HEIGHT / 16);
		separator.getChildren().addAll(winButt, winInfo);

		Group dispGroup = new Group();
		winDisplay.getChildren().clear();
		for (Particle p : particles) {
			dispGroup.getChildren().add(p.getImageView());
		}
		winDisplay.getChildren().add(dispGroup);

		winEle.getChildren().clear();
		winEle.getChildren().addAll(winDisplay, separator, winHelp);

		ClsMain.updatePane(winEle);
	}

	// User presses btnStart.
	public static void doBtnStart() {
		//Save copy of original particles for reset.
		initialParticles = particles;

		if (particles.isEmpty()) {
			return;
		}

		if (!txtEleConst.tryGetFloat())
		{
			return;
		}

		eleConst = Float.parseFloat(txtEleConst.getText());

		if (eleConst <= 0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Input Value Error!");
			alert.setHeaderText(null);
			alert.setContentText("The value inputed for Coulomb's Constant must be a number greater than 0.");

			alert.showAndWait();
			return;
		}

		redrawScene();

		// Disable start button and enable the rest.
		btnStart.setDisable(true);
		btnDone.setDisable(false);
		btnPause.setDisable(false);
		btnReset.setDisable(false);

		lblHelp.setText(HELP_START);

		// Generate main animation loop.
		isPaused = false;
		mainLoop = new AnimationTimer() {

			@Override
			public void handle(long now) {
				// Check if the animation is paused before doing any calculations.
				if (!isPaused) {
					// Apply all forces.
					for (Particle p : particles) {
						p.setAcceleration(Point2D.ZERO);
						for (Particle r : particles) {
							p.applyForce(p.attract(r).multiply(eleConst));
						}
						p.move();
					}
					updateAll();
					redrawScene();
				}
			}
		};

		mainLoop.start();
	}

	// User presses btnDone.
	public static void doBtnDone() {
		// Clear all animation data.
		hasSelected = false;
		particles.clear();
		winDisplay.getChildren().clear();

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
		} else {
			btnPause.setText("Pause");
			btnDone.setDisable(false);
			mainLoop.start();
			lblHelp.setText(HELP_RESUME);
		}
	}

	// User presses btnReset.
	public static void doBtnReset() {
		doBtnDone();
		
		//Return to initial state.
		particles = initialParticles;
		
		doBtnStart();
		lblHelp.setText(HELP_RESET);
	}

	// User presses btnHelp.
	public static void doBtnHelp() {
		// Displays an information alert.
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(null);
		alert.setHeaderText(null);
		alert.setContentText("This animation will display the interaction of many charged particles undergoing electrostatic force." 
				+ NEWLINE + NEWLINE +  "The user may also select Coulomb's Constant to magnify the aforementioned forces.");

		alert.showAndWait();
	}

	//User presses btnAdd.
	public static void doBtnAdd() {
		btnAdd.setDisable(true);
		btnSelect.setDisable(false);
		btnRemove.setDisable(false);

		winDisplay.setOnMouseClicked((MouseEvent event) -> {
			// Check for input errors.
			if (!txtCharge.tryGetFloat()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Input Value Error!");
				alert.setHeaderText(null);
				alert.setContentText("The value inputed for the Particle Charge must be a number greater than 0.");

				alert.showAndWait();
				return;
			}

			if (!txtMass.tryGetFloat() || Float.parseFloat(txtMass.getText()) < 0) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Input Value Error!");
				alert.setHeaderText(null);
				alert.setContentText("The value inputed for the Particle Mass must be a number.");

				alert.showAndWait();
				return;
			}

			mouseX = event.getX();
			mouseY = event.getY();
			
			//Check for mousing over a particle.
			boolean hasCollision = false;
			for (Particle p : particles) {
				if (p.getPosition().subtract(mouseX, mouseY).magnitude() < 25) {
					hasCollision = true;
				}
			}

			float addCharge = Float.parseFloat(txtCharge.getText());
			float addMass = Float.parseFloat(txtMass.getText());
			
			//Check for collisions.
			if (hasCollision) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Particle Placement Error!");
				alert.setHeaderText(null);
				alert.setContentText("The placement of the particle you specified cannot touch any other existing particle.");

				alert.showAndWait();
				return;
			}

			//Add particle to window.
			if (!(mouseX < 0 || mouseX > WINDOW_WIDTH || mouseY < 0 || mouseY > WINDOW_HEIGHT / 2 || hasCollision)) {
				particles.add(new Particle(new Point2D(mouseX, mouseY), chargeImg, addCharge, addMass));
			}
			btnRemove.setDisable(false);
			updateAll();
		});

		lblHelp.setText(IElectrostatic.HELP_ADD);
	}

	//User presses btnSelect.
	public static void doBtnSelect() {
		btnSelect.setDisable(true);
		btnAdd.setDisable(false);
		btnRemove.setDisable(false);

		winDisplay.setOnMouseClicked((MouseEvent event) -> {
			for (Particle p : particles) {
				if (p.getPosition().subtract(event.getX(), event.getY()).magnitude() < 25) {
					hasSelected = true;
					selected = particles.indexOf(p);
				}
			}
			redrawScene();
		});
		lblHelp.setText(IElectrostatic.HELP_SELECT);
	}

	//User presses btnRemove.
	public static void doBtnRemove() {
		btnRemove.setDisable(true);
		btnAdd.setDisable(false);
		btnSelect.setDisable(false);

		winDisplay.setOnMouseClicked((MouseEvent event) -> {
			for (Particle p : particles) {
				if (p.getPosition().subtract(event.getX(), event.getY()).magnitude() < 25) {
					int index = particles.indexOf(p);
					if (index == selected) {
						hasSelected = false;
					}
					particles.remove(index);
				}
			}
			redrawScene();
		});
		lblHelp.setText(IElectrostatic.HELP_REMOVE);
	}

	//Update all particles in the program.
	public static void updateAll() {
		for (Particle p : particles) {
			p.update();
		}
		redrawScene();
	}
}
