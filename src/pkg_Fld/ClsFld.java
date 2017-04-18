package pkg_Fld;

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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import pkg_main.AppButton;
import pkg_main.AppTextField;
import pkg_main.ClsMain;
import pkg_Ele.*;

public class ClsFld implements pkg_main.IConstants, IElectrostatic {

	// Animation Properties
	public static AnimationTimer mainLoop;
	private static boolean isPaused;
	private static List<Particle> particles = new LinkedList<>();
	private static List<Particle> initialParticles = new LinkedList<>();
//	private static List<Rectangle> pixelImgs = new LinkedList<>();
	private static boolean hasSelectedParticle = false;
	private static boolean hasSelectedPixel = false;
	private static int selectedParticle = 0;
	private static int selectedPixel = 0;
	private static double mouseX;
	private static double mouseY;
	private static float eleConst = 0f;
	private static List<EFieldPoint> pixels = new LinkedList<>();

	// Windows
	private static VBox winFld;
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
	private static AppTextField txtMinusColour;
	private static AppTextField txtPlusColour;
	private static AppTextField txtCharge;

	// Labels
	private static Label lblHelp;

	// Data
	private static Label selectedPotential = new Label();
	private static Label selectedCharge = new Label();

	// Charge image.
	static Image chargeImg = new Image(ClsMain.resourceLoader("EleForce/Charge.png"));

	public static Pane drawScene() {

		// Main VBox.
		winFld = new VBox();

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
		
		for (int i = 0; i < WINDOW_HEIGHT * WINDOW_WIDTH / 2; i++) {
			pixels.add(new EFieldPoint(new Point2D(i % WINDOW_WIDTH, i / WINDOW_WIDTH)));
//			pixelImgs.add(new Rectangle(i % WINDOW_WIDTH, i / WINDOW_WIDTH, 1, 1));
//			
//			winDisplay.getChildren().add(pixelImgs.get(i));
		}

		// Setup button window.
		Label lblEleConst = new Label("Coulomb's Constant: ");
		Label lblMinusColour = new Label("Small Potential Colour (Hex): ");
		Label lblPlusColour = new Label("High Potential Colour (Hex): ");
		Label lblCharge = new Label("Particle Charge: ");
		lblEleConst.setTextAlignment(TextAlignment.RIGHT);
		lblMinusColour.setTextAlignment(TextAlignment.RIGHT);
		lblPlusColour.setTextAlignment(TextAlignment.RIGHT);
		lblCharge.setTextAlignment(TextAlignment.RIGHT);
		lblEleConst.setTextFill(Color.WHITE);
		lblMinusColour.setTextFill(Color.WHITE);
		lblPlusColour.setTextFill(Color.WHITE);
		lblCharge.setTextFill(Color.WHITE);

		txtEleConst = new AppTextField("Coulomb's Constant");
		txtMinusColour = new AppTextField("Small Potential Colour");
		txtPlusColour = new AppTextField("Big Potential Colour");
		txtCharge = new AppTextField("Particle Charge");

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
		vLabels.getChildren().addAll(lblEleConst, lblCharge);

		vFields.setAlignment(Pos.CENTER);
		vFields.getChildren().addAll(txtEleConst, txtCharge);

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

		HBox chrgBox = new HBox();

		infoContents.getChildren().addAll(chrgBox);
		winInfo.getChildren().add(infoContents);

		// Help window.
		lblHelp = new Label();
		winHelp.setPrefHeight(WINDOW_HEIGHT / 16);
		winHelp.getChildren().add(lblHelp);

		// Add all the panes to the main window.
		final HBox separator = new HBox();
		separator.setPrefHeight(7 * WINDOW_HEIGHT / 16);
		separator.getChildren().addAll(winButt, winInfo);

		winFld.getChildren().addAll(winDisplay, separator, winHelp);

		isPaused = true;

		return winFld;
	}

	// Re-paint the scene in order to update the position of objects during the animation.
	private static void redrawScene() {
		HBox separator = new HBox();

		winInfo.getChildren().clear();
		VBox infoContents = new VBox();
		if (hasSelectedParticle) {
			selectedCharge.setText("Charge: " + particles.get(selectedParticle).getCharge());
			selectedPotential.setText(EMP_STR);
		} else if (hasSelectedPixel) {
			selectedCharge.setText(EMP_STR);
			selectedPotential.setText("Potential: " + pixels.get(selectedPixel).getPotential());
		}
		HBox chrgBox = new HBox();
		HBox potBox = new HBox();

		chrgBox.getChildren().add(selectedCharge);
		potBox.getChildren().add(selectedPotential);

		infoContents.setPadding(new Insets(20));
		infoContents.getChildren().addAll(chrgBox, potBox);
		winInfo.getChildren().add(infoContents);

		separator.setPrefHeight(7 * WINDOW_HEIGHT / 16);
		separator.getChildren().addAll(winButt, winInfo);

		Group dispGroup = new Group();
		winDisplay.getChildren().clear();
//		for (int i = 0; i < WINDOW_HEIGHT * WINDOW_WIDTH / 2; i++) {
//			pixelImgs.get(i).setFill(pixels.get(i).getColour());
//			
//			winDisplay.getChildren().add(pixelImgs.get(i));
//		}
		
		for (Particle p : particles) {
			dispGroup.getChildren().add(p.getImageView());
		}
		
		winDisplay.getChildren().add(dispGroup);

		winFld.getChildren().clear();
		winFld.getChildren().addAll(winDisplay, separator, winHelp);

		ClsMain.updatePane(winFld);
	}

	// User presses btnStart.
	public static void doBtnStart() {
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

		if (!txtMinusColour.tryGetColour()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Colour Hex Error!");
			alert.setHeaderText(null);
			alert.setContentText("The specified hex colour for small potentials is an invalid hex value." + 
					NEWLINE + "Use only numbers and lowercase letters.");

			alert.showAndWait();
			return;
		}
		
		if (!txtPlusColour.tryGetColour()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Colour Hex Error!");
			alert.setHeaderText(null);
			alert.setContentText("The specified hex colour for high potentials is an invalid hex value." + 
					NEWLINE + "Use only numbers and lowercase letters.");

			alert.showAndWait();
			return;
		}
		
		//Creates the colour gradient for this iteration of the program.
		ColourGradient gradient = new ColourGradient(hexToColour(txtMinusColour.getText()), hexToColour(txtPlusColour.getText()));

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
					double minPot = 0;
					double maxPot = 0;
					for (EFieldPoint e : pixels) {
						e.setPotential(0);
						for (Particle p : particles) {
							double dist = e.getPosition().subtract(p.getPosition()).magnitude();
							e.setPotential(e.getPotential() + eleConst * p.getCharge() / dist);
						}
						
						if (e.getPotential() < minPot) {
							minPot = e.getPotential();
						} else if (e.getPotential() > maxPot) {
							maxPot = e.getPotential();
						}
					}
					double diff = maxPot - minPot;
					for (EFieldPoint e : pixels) {
						e.setColour(gradient.getColour((e.getPotential() - minPot) / diff));
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
		hasSelectedParticle = false;
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
		alert.setContentText("This animation will display the potential of any point in space as a result of point charges." 
				+ NEWLINE + NEWLINE +  "The user may also select Coulomb's Constant to alter the magnitude of the potential.");

		alert.showAndWait();
	}

	//User presses btnAdd.
	public static void doBtnAdd() {
		btnAdd.setDisable(true);
		btnSelect.setDisable(false);
		btnRemove.setDisable(false);

		winDisplay.setOnMouseClicked((MouseEvent event) -> {
			if (!txtCharge.tryGetFloat()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Input Value Error!");
				alert.setHeaderText(null);
				alert.setContentText("The value inputed for the Particle Charge must be a number greater than 0.");

				alert.showAndWait();
				return;
			}

			mouseX = event.getX();
			mouseY = event.getY();

			boolean hasCollision = false;
			for (Particle p : particles) {
				if (p.getPosition().subtract(mouseX, mouseY).magnitude() < 25) {
					hasCollision = true;
				}
			}

			float addCharge = Float.parseFloat(txtCharge.getText());

			if (hasCollision) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Particle Placement Error!");
				alert.setHeaderText(null);
				alert.setContentText("The placement of the particle you specified cannot touch any other existing particle.");

				alert.showAndWait();
				return;
			}

			if (!(mouseX < 0 || mouseX > WINDOW_WIDTH || mouseY < 0 || mouseY > WINDOW_HEIGHT / 2 || hasCollision)) {
				particles.add(new Particle(new Point2D(mouseX, mouseY), chargeImg, addCharge, 0));
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
			hasSelectedParticle = false;
			for (Particle p : particles) {
				if (p.getPosition().subtract(event.getX(), event.getY()).magnitude() < 25) {
					hasSelectedParticle = true;
					selectedParticle = particles.indexOf(p);
				}
			}

			if (!hasSelectedParticle) {
				selectedPixel = (int) (event.getX() + WINDOW_WIDTH * event.getY());
				hasSelectedPixel = true;
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
					if (index == selectedParticle) {
						hasSelectedParticle = false;
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

	//Get a colour by its hex string.
	public static Color hexToColour(String hex) {
		int[] digVal = new int[6];
		for (int i = 0; i < 5; i++) {
			switch (hex.charAt(i)) {
			case '0': digVal[i] = 0; break;
			case '1': digVal[i] = 1; break;
			case '2': digVal[i] = 2; break;
			case '3': digVal[i] = 3; break;
			case '4': digVal[i] = 4; break;
			case '5': digVal[i] = 5; break;
			case '6': digVal[i] = 6; break;
			case '7': digVal[i] = 7; break;
			case '8': digVal[i] = 8; break;
			case '9': digVal[i] = 9; break;
			case 'a': digVal[i] = 10; break;
			case 'b': digVal[i] = 11; break;
			case 'c': digVal[i] = 12; break;
			case 'd': digVal[i] = 13; break;
			case 'e': digVal[i] = 14; break;
			case 'f': digVal[i] = 15; break;
			}
		}

		int red = digVal[0] * 16 + digVal[1];
		int green = digVal[2] * 16 + digVal[3];
		int blue = digVal[4] * 16 + digVal[5];

		return new Color(red / 255, green / 255, blue / 255, 0);
	}
}

