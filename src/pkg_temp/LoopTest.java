package pkg_temp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

// Test field for a game loop which updates every frame.
public class LoopTest extends Application {

	static Random random = new Random();

	Pane playfield;
	Scene scene;
	Label framelbl;

	List<AccelerationCircle> allMovers = new ArrayList<>();

	AnimationTimer gameLoop;
	
	// Frame rate stuff.
	// TODO: Create an object for frame rate updating.
	private final long[] frameTimes = new long[100];
    private int frameTimeIndex = 0;
    private boolean arrayFilled = false;

	@Override
	public void start(Stage primaryStage) {
		AccelerationCircle.class.getField("idfk") = "";
		BorderPane root = new BorderPane();
		playfield = new Pane();
		playfield.setPrefSize(1280, 720);

		framelbl = new Label();
		
		Pane layerPane = new Pane(playfield, framelbl);
		root.setCenter(layerPane);

		scene = new Scene(root, 1280, 720);
		primaryStage.setScene(scene);
		primaryStage.showAndWait();

		// add content
		prepareGame();

		// run animation loop
		startGame();

	}

	private void prepareGame() {

		// add sprites
		for (int i = 0; i < Settings.MOVER_COUNT; i++) {
			addMovers();
		}

	}

	private void startGame() {

		// start game
		gameLoop = new AnimationTimer() {

			@Override
			public void handle(long now) {

				// force: attraction
				for (AccelerationCircle m1 : allMovers) {
					for (AccelerationCircle m2 : allMovers) {

						if (m1 == m2)
							continue;

						// calculate attraction
						Point2D force = m1.attract(m2);

						// apply attraction
						m2.applyForce(force);

					};
				};

				// move
				allMovers.forEach(AccelerationCircle::move);

				// update in fx scene
				allMovers.forEach(AccelerationCircle::display);

				long oldFrameTime = frameTimes[frameTimeIndex];
                frameTimes[frameTimeIndex] = now;
                frameTimeIndex = (frameTimeIndex + 1) % frameTimes.length;
                if (frameTimeIndex == 0) {
                    arrayFilled = true ;
                }
                
                if (arrayFilled) {
                    long elapsedNanos = now - oldFrameTime ;
                    long elapsedNanosPerFrame = elapsedNanos / frameTimes.length ;
                    double frameRate = 1_000_000_000.0 / elapsedNanosPerFrame ;
                    framelbl.setText(String.format("Current frame rate: %.3f", frameRate));
                }
			}
		};

		gameLoop.start();

	}

	private void addMovers() {

		// random location
		Double x = random.nextDouble() * playfield.getWidth();
		double y = random.nextDouble() * playfield.getHeight();

		// create sprite data
		Point2D location = new Point2D(x, y);
		Point2D velocity = new Point2D(0, 0);
		Point2D acceleration = new Point2D(0, 0);
		double mass = random.nextDouble() * 10 + 10;

		// create sprite and add to layer
		AccelerationCircle mover = new AccelerationCircle(location, velocity, acceleration, mass);

		// register sprite
		allMovers.add(mover);

		// add this node to layer
		playfield.getChildren().add(mover);

	}

	public static void main(String[] args) {
		launch(args);
	}
}
