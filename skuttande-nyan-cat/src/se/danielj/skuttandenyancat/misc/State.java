package se.danielj.skuttandenyancat.misc;

/**
 * This class is used to keep track of if the cat is running or not.
 * 
 * @author Daniel Jonsson
 * @license GNU GPLv3
 * 
 */
public class State {

	private static boolean running;
	
	public static void init() {
		running = false;
	}

	public static boolean isRunning() {
		return running;
	}

	public static void setRunning(boolean running) {
		State.running = running;
	}
}
