package backend;

public class Settings {
	public static int MOVER_COUNT = 2;

	public static double MOVER_MAX_SPEED = 500;

	// ensure that attraction is applied with at least min and max
	// we don't want it to be too weak or too strong
	public static double ATTRACTION_DISTANCE_MIN = 5;
	public static double ATTRACTION_DISTANCE_MAX = 25.0;

	// Universal Gravitational Constant; real world: 6.67428E10-11;
	public static double GRAVITATIONAL_CONSTANT = 2;

}
