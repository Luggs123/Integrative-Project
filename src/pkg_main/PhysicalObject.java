package pkg_main;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

public abstract class PhysicalObject extends Circle {
	/**
	 * The position of the object.
	 **/
	private Point2D position;
	
	/**
	 * The velocity of the object.
	 **/
	private Point2D velocity;
	
	/**
	 * The acceleration of the object.
	 **/
	private Point2D acceleration;
	
	/**
	 * The image that will represent the object.
	 **/
	private Image image;
	protected ImageView imageView;

	public PhysicalObject(Point2D position, Point2D velocity, Image image) {
		this.position = position;
		this.velocity = velocity;
		this.acceleration = Point2D.ZERO;
		this.image = image;
		
		this.imageView = new ImageView(image);
		this.imageView.setFitWidth(50);
		this.imageView.setFitHeight(50);
	}
	
	public PhysicalObject(Point2D position, Image image) {
		this(position, Point2D.ZERO, image);
	}

	public Point2D getPosition() {
		return position;
	}

	public void setPosition(Point2D position) {
		this.position = position;
	}

	public Point2D getVelocity() {
		return velocity;
	}

	public void setVelocity(Point2D velocity) {
		this.velocity = velocity;
	}

	public Point2D getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(Point2D acceleration) {
		this.acceleration = acceleration;
	}

	public Image getImage() {
		return image;
	}
	
	public ImageView getImageView() {
		return imageView;
	}

	
	// Retrieve the coordinates of the object's boundaries.
	public float getLeftBound() {
		return (float) (this.getPosition().getX() - this.getImageView().getFitWidth() / 2);
	}

	public float getRightBound() {
		return (float) (this.getPosition().getX() + this.getImageView().getFitWidth() / 2);
	}

	public float getLowerBound() {
		return (float) (this.getPosition().getY() + this.getImageView().getFitHeight() / 2);
	}

	public float getUpperBound() {
		return (float) (this.getPosition().getY() - this.getImageView().getFitHeight() / 2);
	}

	/**
	 * Applies an acceleration force onto the object.
	 * @param force The vector force being applied.
	 */
	public abstract void applyForce(Point2D force);



	/**
	 * Constrains a value between a given minimum and maximum.
	 * @param value The value to constrain.
	 * @param min The minimum value.
	 * @param max The maximum value.
	 * @return The constrained value.
	 */
	public static double constrain(double value, double min, double max) {
		if (value < min) {
			return min;
		}
		else if (value > max) {
			return max;
		}

		return value;
	}
	
	/**
	 * Update the position of the object based on the acceleration and current velocity.
	 */
	public void move() {
		// Set velocity depending on acceleration.
		this.setVelocity(this.getVelocity().add(acceleration));
		
		// Change location depending on velocity.
		this.setPosition(this.getPosition().add(velocity));
	}
	
	/**
	 * Update the position of the object's image on a screen relative to its current position.
	 */
	public void update() {
		// Update circle position.
		this.setCenterX(this.getPosition().getX());
		this.setCenterY(this.getPosition().getY());
		
		// Update image position.
		this.getImageView().setLayoutX(this.getPosition().getX() - this.getImageView().getFitWidth() / 2);
		this.getImageView().setLayoutY(this.getPosition().getY() - this.getImageView().getFitHeight() / 2);
	}
}

class Settings {
	public static int MOVER_COUNT = 2;

	public static double MOVER_MAX_SPEED = 500;

	// ensure that attraction is applied with at least min and max
	// we don't want it to be too weak or too strong
	public static double ATTRACTION_DISTANCE_MIN = 5;
	public static double ATTRACTION_DISTANCE_MAX = 25.0;

	// Universal Gravitational Constant; real world: 6.67428E10-11;
	public static double GRAVITATIONAL_CONSTANT = 2;

}
