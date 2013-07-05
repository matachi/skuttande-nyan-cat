package se.danielj.skuttandenyancat.misc;

import com.badlogic.gdx.ApplicationListener;

/**
 * 
 * @author Daniel Jonsson
 * @license GNU GPLv3
 *
 */
public interface GameController extends ApplicationListener {

	public void gameOver();
	
	public void exit();
	
	public void restart();
	
	public void continueGame();
	
	public void back();
}
