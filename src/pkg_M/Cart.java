package pkg_M;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class Cart extends pkg_main.PhysicalObject {

	public Cart(Point2D position, Point2D velocity, Image image) {
		super(position, velocity, image);
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
