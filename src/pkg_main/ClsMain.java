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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class ClsMain extends Application implements IConstants {
	
	// Main Scene
	private static Stage primaryStage = new Stage();
	
	protected static Pane winMain = new VBox();
	protected static Scene sceneMain = new Scene(winMain, 1200, 720);
	
	// Menu bar.
	public static MenuBar mainMenu = new MenuBar();
	
	public static Menu menuMech = new Menu("Mechanics");
	public static Menu menuWav = new Menu("Waves, Optics & Modern Physics");
	public static Menu menuEM = new Menu("Electricity & Magnetism");
	public static Menu menuProg = new Menu("Program");
	
	public static AppMenuItem test = new AppMenuItem("Test");
	
	// Set the primaryStage to point to a different scene.
	public static void updateScene(Scene newScene) {
		primaryStage.setScene(newScene);
	}

	@Override
	public void start(Stage stage) {
		
		// Create the welcome message.
		Label lblGreeting = new Label();
		lblGreeting.setText("Welcome to Integrative Multimedia Programming.");
		lblGreeting.setTextAlignment(TextAlignment.CENTER);
		lblGreeting.setTranslateX(500);
		lblGreeting.setTranslateY(500);
		
		
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
