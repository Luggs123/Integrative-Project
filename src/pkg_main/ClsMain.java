//TODO: CHECK PROGRAM FUNCTION
/*** Program Function; To create and display a list of given friends.
*** Creator; Paul Gaudnik, Mark Jarjour, Michael Luger
*** Submission Date; November 15th, 2016
*** Date Last Modified; October 19th, 2016
***/

package pkg_main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class ClsMain extends Application implements IConstants {
	
	// Main Scene
	static private Stage primaryStage = new Stage();
	
	static protected VBox winMain = new VBox();
	static protected Scene sceneMain = new Scene(winMain, 1200, 720);
	
	// Menu bar.
	static public MenuBar mainMenu = new MenuBar();
	
	static public Menu menuMech = new Menu("Mechanics");
	static public Menu menuWav = new Menu("Waves, Optics & Modern Physics");
	static public Menu menuEM = new Menu("Electricity & Magnetism");
	static public Menu menuProg = new Menu("Program");
	
	static public AppMenuItem test = new AppMenuItem("Test");
	
	// Set the primaryStage to point to a different scene.
	public static void updateScene(Scene newScene) {
		primaryStage.setScene(newScene);
	}

	@Override
	public void start(Stage stage) {
		
		// Create the welcome message.
		Label lblGreeting = new Label();
		lblGreeting.setText("Welcome to the Integrative Multimedia Programming.");
		lblGreeting.setTextAlignment(TextAlignment.CENTER);
		
		
		// Add the menu components to the main window.
		menuMech.getItems().add(test);
		mainMenu.getMenus().addAll(menuMech, menuWav, menuEM, menuProg);
		winMain.getChildren().addAll(mainMenu, lblGreeting);
		
		winMain.setPrefWidth(800);

		// Title the window and display it to the user.
		primaryStage.setTitle("Integrative Project");
		primaryStage.setScene(sceneMain);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
