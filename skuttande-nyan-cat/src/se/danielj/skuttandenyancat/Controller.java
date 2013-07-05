package se.danielj.skuttandenyancat;

import com.badlogic.gdx.ApplicationListener;

/**
 * 
 * @author Daniel Jonsson
 * @license GNU GPLv3
 *
 */
public interface Controller extends ApplicationListener {
	public enum Scene {
		MAIN_MENU, GAME, CREDITS, EXIT
	};

	public void setScene(Scene scene);
}
