package pkg_Coll;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class Cart extends pkg_main.PhysicalObject {
	private boolean moving;
	private final float mass;
	
	public boolean isMoving() {
		return moving;
	}

	public final void setMoving(boolean moving) {
		this.moving = moving;
	}
	
	public float getMass() {
		return mass;
	}

	public Cart(Point2D position, Point2D velocity, Image image, float mass) {
		super(position, velocity, image);
		
		this.mass = mass;
		this.setMoving(false);
		this.imageView.setFitWidth(100);
		this.imageView.setFitHeight(100);
	}

	@Override
	/**
	 * Applies a downward force on the ball, modifying its acceleration.
	 * @see pkg_temp.PhysicalObject#applyForce(javafx.geometry.Point2D)
	 **/
	public void applyForce(Point2D force) {
		this.setAcceleration(this.getAcceleration().add(force));
	}
}
