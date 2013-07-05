package se.danielj.skuttandenyancat;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * 
 * @author Daniel Jonsson
 * @license GNU GPLv3
 *
 */
public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Skuttande Nyan Cat";
		cfg.useGL20 = true;
		cfg.width = 640 * 2;
		cfg.height = 360 * 2;
		
		new LwjglApplication(new AppController(), cfg);
	}
}
