package pkg_Fld;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class EFieldPoint {
	public EFieldPoint(Point2D position) {
		super();
		this.position = position;
	}
	
	public double getPotential() {
		return potential;
	}
	public void setPotential(double potential) {
		this.potential = potential;
	}
	public Color getColour() {
		return colour;
	}
	public void setColour(Color colour) {
		this.colour = colour;
	}
	public Point2D getPosition() {
		return position;
	}

	private Point2D position;
	private double potential;
	private Color colour;
}
