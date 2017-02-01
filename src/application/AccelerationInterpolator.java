package application;

import javafx.animation.Interpolator;

public class AccelerationInterpolator extends Interpolator{
	private double acceleration;
	private double initialVelocity;
	
	public AccelerationInterpolator(double acc, double initVelocity) {
		this.acceleration = acc;
		this.initialVelocity = initVelocity;
	}
	
	public AccelerationInterpolator(double acc) {
		this(acc, 0);
	}
	
	@Override
	protected double curve(double t) {
		return (1.0/2.0) * acceleration * t * t + initialVelocity * t;
	}

}
