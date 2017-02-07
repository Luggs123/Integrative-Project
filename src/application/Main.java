package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Main extends Application {

	static Random random = new Random();

	Pane playfield;

	List<Mover> allMovers = new ArrayList<>();

	AnimationTimer gameLoop;

	Scene scene;

	@Override
	public void start(Stage primaryStage) {

		// create containers
		BorderPane root = new BorderPane();

		// playfield for our sprites
		playfield = new Pane();
		playfield.setPrefSize(Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);

		// entire game as layers
		Pane layerPane = new Pane();

		layerPane.getChildren().addAll(playfield);

		root.setCenter(layerPane);

		scene = new Scene(root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);

		primaryStage.setScene(scene);
		primaryStage.show();

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
				for (Mover m1 : allMovers) {
					for (Mover m2 : allMovers) {

						if (m1 == m2)
							continue;

						// calculate attraction
						Point2D force = m1.attract(m2);

						// apply attraction
						m2.applyForce(force);

					};
				};

				// move
				allMovers.forEach(Mover::move);

				// update in fx scene
				allMovers.forEach(Mover::display);

			}
		};

		gameLoop.start();

	}

	private void addMovers() {

		// random location
		double x = random.nextDouble() * playfield.getWidth();
		double y = random.nextDouble() * playfield.getHeight();

		// create sprite data
		Point2D location = new Point2D(x, y);
		Point2D velocity = new Point2D(0, 0);
		Point2D acceleration = new Point2D(0, 0);
		double mass = random.nextDouble() * 10 + 10;

		// create sprite and add to layer
		Mover mover = new Mover(location, velocity, acceleration, mass);

		// register sprite
		allMovers.add(mover);

		// add this node to layer
		playfield.getChildren().add(mover);

	}

	public static void main(String[] args) {
		launch(args);
	}
}

class Mover extends Circle {

	Point2D location;
	Point2D velocity;
	Point2D acceleration;

	double mass;

	double maxSpeed = Settings.MOVER_MAX_SPEED;

	public Mover(Point2D location, Point2D velocity, Point2D acceleration, double mass) {

		this.location = location;
		this.velocity = velocity;
		this.acceleration = acceleration;
		this.mass = mass;

		// initialize view depending on mass
		setRadius( mass);

		// create view
		setStroke(Color.BLUE);
		setFill(Color.BLUE.deriveColor(1, 1, 1, 0.3));

	}

	public void applyForce(Point2D force) {

		Point2D f = new Point2D( force.getX(), force.getY());
		f = f.multiply(1/mass);
		
		acceleration = acceleration.add(f);
	}

	public void move() {

		// set velocity depending on acceleration
		velocity = velocity.add(acceleration);

		// limit velocity to max speed
		double mag = velocity.magnitude();
		if( mag > Settings.MOVER_MAX_SPEED) {
			velocity = velocity.normalize();
			velocity = velocity.multiply(mag);
		}

		// change location depending on velocity
		location = location.add(velocity);

		// clear acceleration
		acceleration = Point2D.ZERO;
	}

	public Point2D attract(Mover m) {

		// force direction
		Point2D force = location.subtract(m.location);
		double distance = force.magnitude();
		
		// constrain movement
		distance = constrain(distance, Settings.ATTRACTION_DISTANCE_MIN, Settings.ATTRACTION_DISTANCE_MAX);
		
		force = force.normalize();

		// force magnitude
		double strength = (Settings.GRAVITATIONAL_CONSTANT * mass * m.mass) / (distance * distance);
		force = force.multiply(strength);

		return force;
	}

	public static double constrain(double value, double min, double max) {

		if (value < min)
			return min;

		if (value > max)
			return max;

		return value;
	}
	
	public void display() {
		setCenterX( location.getX());
		setCenterY( location.getY());
	}	
}

class Settings {

	public static double SCENE_WIDTH = 1280;
	public static double SCENE_HEIGHT = 720;

	public static int MOVER_COUNT = 600;

	public static double MOVER_MAX_SPEED = 20;

	// ensure that attraction is applied with at least min and max
	// we don't want it to be too weak or too strong
	public static double ATTRACTION_DISTANCE_MIN = 5;
	public static double ATTRACTION_DISTANCE_MAX = 25.0;

	// Univeral Gravitational Constant; real world: 6.67428E10-11;
	public static double GRAVITATIONAL_CONSTANT = 0.004;

}