package se.danielj.skuttandenyancat.misc;

public class Energy {

	private static float energy;
	
	public static void init() {
		energy = 100;
	}
	
	public static void setEnergy(float energy) {
		if (energy < 0) {
			Energy.energy = 0;
		} else if (energy > 100) {
			Energy.energy = 100;
		} else {
			Energy.energy = energy;
		}
	}

	public static float getEnergy() {
		return energy;
	}
	
	public static void addEnergy(float energy) {
		setEnergy(getEnergy() + energy);
	}
	
	public static void subtractEnergy(float energy) {
		setEnergy(getEnergy() - energy);
	}
	
	public static boolean hasEnergy() {
		return getEnergy() > 0;
	}
}
