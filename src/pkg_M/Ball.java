package pkg_M;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class Ball extends pkg_temp.PhysicalObject {

	public Ball(Point2D position, Image image) {
		super(position, image);
	}

	@Override
	/**
	 * Applies a downward force on the ball, modifying its acceleration.
	 * @see pkg_temp.PhysicalObject#applyForce(javafx.geometry.Point2D)
	 **/
	public void applyForce(Point2D force) {
		
	}

}
