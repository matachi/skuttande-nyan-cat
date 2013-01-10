package se.danielj.skuttandenyancat;

import com.badlogic.gdx.ApplicationListener;

public interface Controller extends ApplicationListener {
	public enum Scene {
		MAIN_MENU, GAME, CREDITS, EXIT
	};

	public void setScene(Scene scene);
}
