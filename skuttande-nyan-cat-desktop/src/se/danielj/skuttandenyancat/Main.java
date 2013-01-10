package se.danielj.skuttandenyancat;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "skuttande-nyan-cat";
		cfg.useGL20 = true;
		cfg.width = 640 * 2;
		cfg.height = 360 * 2;
		
		new LwjglApplication(new AppController(), cfg);
	}
}
