package se.danielj.skuttandenyancat.misc;

public class State {

	private static boolean running;
	
	public static boolean isRunning() {
		return running;
	}
	
	public static void setRunning(boolean running) {
		State.running = running;
	}
}
