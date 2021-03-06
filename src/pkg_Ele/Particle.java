package pkg_Ele;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import pkg_main.PhysicalObject;

public class Particle extends PhysicalObject {
	private boolean selected;
	private double mass;
	private double charge;
	
	public Particle(Point2D position, Image image, double charge, double mass) {
		super(position, image);
		this.charge = charge;
		this.mass = mass;
		selected = false;
	}

	@Override
	public void applyForce(Point2D force) {
		this.setAcceleration(this.getAcceleration().add(force.multiply(1.0 / this.getMass())));
	}
	
	/**
	 * Applies an attraction onto an object based on the distance from another specified object.
	 * @param m Another object that inherits PhysicalObject.
	 * @return A {@link Point2D} object.
	 */
	public Point2D attract(Particle m) {

		// Force direction.
		Point2D force = this.getPosition().subtract(m.getPosition());
		double distance = force.magnitude();
		
		// Constrain movement.
		distance = constrain(distance, 25, 300);
		
		force = force.normalize();

		// Magnitude of the force.
		double strength = (this.getCharge() * m.getCharge()) / (distance * distance);
		force = force.multiply(strength);

		return force;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public double getCharge() {
		return charge;
	}

	public void setCharge(double charge) {
		this.charge = charge;
	}

	public double getMass() {
		return mass;
	}

}
