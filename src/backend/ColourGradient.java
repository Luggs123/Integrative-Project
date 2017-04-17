package backend;

import javafx.scene.paint.Color;

public class ColourGradient {
	/**
	 * The starting colour of the gradient.
	 */
	private Color initColour;
	
	/**
	 * The ending colour of the gradient.
	 */
	private Color finalColour;
	
	// The differences in Red, Green, and Blue components between the starting and ending colours.
	private double diffRed;
	private double diffGreen;
	private double diffBlue;
	
	/**
	 * Custom constructor for a Gradient.
	 * @param initColour The starting colour of the gradient.
	 * @param finalColour The ending colour of the gradient.
	 */
	public ColourGradient(Color initColour, Color finalColour) {
		this.initColour = initColour;
		this.finalColour = finalColour;
		
		this.diffRed = finalColour.getRed() - initColour.getRed();
		this.diffGreen = finalColour.getGreen() - initColour.getGreen();
		this.diffBlue = finalColour.getBlue() - initColour.getBlue();
	}
	
	/**
	 * Gets any point in the gradient.
	 * @param frac The distance in the gradient, from 0.0 to 1.0.
	 */
	public Color getColour(double frac) {
		if (frac < 0) {
			return this.initColour;
		} else if (frac > 1) {
			return this.finalColour;
		} else {
			return new Color(this.initColour.getRed() + this.diffRed * frac, this.initColour.getGreen() + this.diffGreen * frac,
					this.initColour.getBlue() + this.diffBlue * frac, 1.0);
		}
	}
}
