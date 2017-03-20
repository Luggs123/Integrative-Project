package pkg_EM;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class Particle extends pkg_temp.PhysicalObject{
	private boolean selected = false;
	private double mass;
	private double charge;
	
	public Particle(Point2D position, Point2D velocity, Point2D acceleration, Image image) {
		super(position, velocity, acceleration, image);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void applyForce(Point2D force) {
		// TODO Auto-generated method stub
		
	}
	
	public Point2D attract(Particle p2) {
		return null;
		// TODO Write attract method
		
	}

}
