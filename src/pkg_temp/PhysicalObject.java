package pkg_temp;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
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

	public PhysicalObject(Point2D position, Point2D velocity, Point2D acceleration, Image image) {
		this.position = position;
		this.velocity = velocity;
		this.acceleration = acceleration;
		this.image = image;
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

	/**
	 * Applies an acceleration force onto the object.
	 * @param force The vector force being applied.
	 */
	public abstract void applyForce(Point2D force);

//	/**
//	 * Applies an attraction onto an object based on the distance from another specified object.
//	 * @param m Another object that inherits PhysicalObject.
//	 * @return A {@link Point2D} object.
//	 */
//	public Point2D attract(PhysicalObject m) {
//
//		// Force direction.
//		Point2D force = this.location.subtract(m.location);
//		double distance = force.magnitude();
//		
//		// Constrain movement.
//		distance = constrain(distance, Settings.ATTRACTION_DISTANCE_MIN, Settings.ATTRACTION_DISTANCE_MAX);
//		
//		force = force.normalize();
//
//		// Magnitude of the force.
//		double strength = (Settings.GRAVITATIONAL_CONSTANT * mass * m.mass) / (distance * distance);
//		force = force.multiply(strength);
//
//		return force;
//	}

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

		// Limit velocity to max speed.
		double mag = this.getVelocity().magnitude();
		
		if (mag > Settings.MOVER_MAX_SPEED) {
			mag = constrain(mag, 0, Settings.MOVER_MAX_SPEED);
			this.setVelocity(this.getVelocity().normalize());
			this.setVelocity(this.getVelocity().multiply(mag));
		}
		
		// Change location depending on velocity.
		this.setPosition(this.getVelocity().add(velocity));

		// Clear the acceleration.
		this.setAcceleration(Point2D.ZERO);
	}
	
	/**
	 * Update the position of the object on a screen relative to its current position.
	 */
	public void update() {
		super.setCenterX(this.getPosition().getX());
		super.setCenterY(this.getPosition().getY());
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
