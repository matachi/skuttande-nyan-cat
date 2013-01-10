package se.danielj.skuttandenyancat.misc;

import com.badlogic.gdx.ApplicationListener;

public interface GameController extends ApplicationListener {

	public void gameOver();
	
	public void exit();
	
	public void restart();
	
	public void continueGame();
	
	public void back();
}
