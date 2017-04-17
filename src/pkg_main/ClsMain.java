//TODO: CHECK PROGRAM FUNCTION
/** Program Function; To generate a series of animations relating to various physics topics.
*** Creator; Paul Gaudnik, Mark Jarjour, Michael Luger
*** Submission Date; April 18th, 2017
*** Date Last Modified; April 17th, 2017
**/

package pkg_main;

import java.io.File;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class ClsMain extends Application implements IConstants {
	
	protected static Pane winMain = new VBox();
	protected static Scene sceneMain = new Scene(winMain, WINDOW_WIDTH, WINDOW_HEIGHT);
	
	// Menu bar.
	public static MenuBar mainMenu = new MenuBar();
	
	public static Menu menuMech = new Menu("Mechanics");
	public static AppMenuItem menuProj = new AppMenuItem("Projectile Motion");
	public static AppMenuItem menuColl = new AppMenuItem("Collisions");
	
	public static Menu menuWav = new Menu("Waves, Optics & Modern Physics");
	public static AppMenuItem menuStr = new AppMenuItem("Waves on a String");
	public static AppMenuItem menuOsci = new AppMenuItem("Oscillation");
	
	public static Menu menuEM = new Menu("Electricity & Magnetism");
	public static AppMenuItem menuEle = new AppMenuItem("Electrostatic Force");
	
	public static Menu menuProg = new Menu("Help");
	public static AppMenuItem menuCred = new AppMenuItem("Credits");
	public static AppMenuItem menuExit = new AppMenuItem("Exit Program");
	public static AppMenuItem menuWIP = new AppMenuItem("In Construction");

	@Override
	public void start(Stage primaryStage) {
		
		// Create the welcome message.
		Label lblGreeting = new Label();
		lblGreeting.setText("Welcome to Integrative Multimedia Programming.");
		lblGreeting.setTextAlignment(TextAlignment.CENTER);
		lblGreeting.setTranslateX(500);
		lblGreeting.setTranslateY(500);
		
		// Add the menu components to the main window.
		menuMech.getItems().addAll(menuProj, menuColl, menuWIP);
		menuWav.getItems().addAll(menuStr, menuOsci, menuWIP);
		menuEM.getItems().addAll(menuEle, menuWIP);
		menuProg.getItems().addAll(menuCred, menuExit);
		mainMenu.getMenus().addAll(menuMech, menuWav, menuEM, menuProg);
		
		winMain.getChildren().addAll(mainMenu, lblGreeting);
		
		winMain.setPrefWidth(800);

		// Title the window and display it to the user.
		sceneMain.getStylesheets().add(resourceLoader("style.css")); 
		primaryStage.setTitle("Integrative Project");
		primaryStage.setScene(sceneMain);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	// Set the main window to point to a different scene.
	public static void updatePane(Pane newPane) {
		winMain.getChildren().clear();
		
		winMain.getChildren().addAll(mainMenu, newPane);
	}
	
	// Returns a file from ../ProjectDirectory/Assets/
	public static String resourceLoader(String filename) {
		return new File("Assets/" + filename).toURI().toString();
	}
	
	// Throw an alert that the following formula is in construction.
	public static void inConstruction(String formulaName) {
		String name = EMP_STR;
		
		if (formulaName == null) {
			name = "following";
		} else {
			name = formulaName;
		}
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("In Construction");
		alert.setHeaderText(null);
		alert.setContentText("The " + name + " formula is under construction.");

		alert.showAndWait();
	}
}
