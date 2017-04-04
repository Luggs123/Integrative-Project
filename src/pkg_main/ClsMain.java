//TODO: CHECK PROGRAM FUNCTION
/*** Program Function; To create and display a list of given friends.
*** Creator; Paul Gaudnik, Mark Jarjour, Michael Luger
*** Submission Date; November 15th, 2016
*** Date Last Modified; October 19th, 2016
***/

package pkg_main;

import java.io.File;

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
	
	protected static Pane winMain = new VBox();
	protected static Scene sceneMain = new Scene(winMain, WINDOW_WIDTH, WINDOW_HEIGHT);
	
	// Menu bar.
	public static MenuBar mainMenu = new MenuBar();
	
	public static Menu menuMech = new Menu("Mechanics");
	public static AppMenuItem menuProj = new AppMenuItem("Projectile Motion");
	
	
	public static Menu menuWav = new Menu("Waves, Optics & Modern Physics");
	public static Menu menuEM = new Menu("Electricity & Magnetism");
	public static Menu menuProg = new Menu("Program");
	
	public static AppMenuItem test = new AppMenuItem("Test");
	
	// Set the main window to point to a different scene.
	public static void updatePane(Pane newPane) {
		winMain.getChildren().clear();
		
		winMain.getChildren().addAll(mainMenu, newPane);
	}

	@Override
	public void start(Stage primaryStage) {
		
		// Create the welcome message.
		Label lblGreeting = new Label();
		lblGreeting.setText("Welcome to Integrative Multimedia Programming.");
		lblGreeting.setTextAlignment(TextAlignment.CENTER);
		lblGreeting.setTranslateX(500);
		lblGreeting.setTranslateY(500);
		
		// Add the menu components to the main window.
		initilizeMenuBar();
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
	
	private static void initilizeMenuBar() {
		menuMech.getItems().addAll(menuProj, test);
		mainMenu.getMenus().addAll(menuMech, menuWav, menuEM, menuProg);
	}
	
	// Returns a file from ../ProjectDirectory/Assets/
	public static String resourceLoader(String filename) {
		return new File("Assets/" + filename).toURI().toString();
	}
}
