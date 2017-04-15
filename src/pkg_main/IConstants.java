package pkg_main;

// Interface storing all globally used constants for the application.
public interface IConstants {
	
	// Strings
	final String EMP_STR = "";
	final String NEWLINE = System.getProperty("line.separator");
	
	// Help messages.
	final String HELP_START = "The animation has started.";
	final String HELP_PAUSE = "The animation is paused.";
	final String HELP_RESUME = "The animation has been unpaused.";
	final String HELP_DONE = "The animation was stopped.";
	final String HELP_RESET = "The animation was reset.";
	
	// Integers
	final int WINDOW_WIDTH = 1200;
	final int WINDOW_HEIGHT = 720;
	final int BTN_WIDTH = 64;
}
