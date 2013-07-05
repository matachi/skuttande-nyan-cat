package se.danielj.skuttandenyancat;

import se.danielj.skuttandenyancat.misc.FontManager;
import se.danielj.skuttandenyancat.misc.MusicManager;
import se.danielj.skuttandenyancat.misc.SoundEffectsManager;
import se.danielj.skuttandenyancat.misc.SpriteManager;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;

/**
 * 
 * @author Daniel Jonsson
 * @license GNU GPLv3
 *
 */
public class AppController implements Controller {

	private ApplicationListener currentScene;
	private ApplicationListener menuScene;
	private ApplicationListener creditsScene;
	private Game gameScene;
	private InputMultiplexer menuInputMultiplexer;
	private InputMultiplexer gameInputMultiplexer;
	private InputMultiplexer creditsInputMultiplexer;

	@Override
	public void create() {
		SpriteManager.init();
		FontManager.init();
		MusicManager.init();
		SoundEffectsManager.init();
        menuInputMultiplexer = new InputMultiplexer();
		menuScene = new MainMenu(this, menuInputMultiplexer);
		menuScene.create();
        gameInputMultiplexer = new InputMultiplexer();
		gameScene = new Game(this, gameInputMultiplexer);
		gameScene.create();
        creditsInputMultiplexer = new InputMultiplexer();
		creditsScene = new Credits(this, creditsInputMultiplexer);
		creditsScene.create();
		setScene(Scene.MAIN_MENU);
        Gdx.input.setCatchBackKey(true);
	}

	@Override
	public void resize(int width, int height) {
		currentScene.resize(width, height);
	}

	@Override
	public void render() {
		currentScene.render();
	}

	@Override
	public void pause() {
		currentScene.pause();
	}

	@Override
	public void resume() {
		currentScene.resume();
	}

	@Override
	public void dispose() {
		menuScene.dispose();
		creditsScene.dispose();
		gameScene.dispose();
		SpriteManager.dispose();
		FontManager.dispose();
		MusicManager.dispose();
		SoundEffectsManager.dispose();
	}

	@Override
	public void setScene(Scene scene) {
		switch (scene) {
		case GAME:
			currentScene = gameScene;
			if (!gameScene.isRunning()) {
				gameScene.reset();
			}
			Gdx.input.setInputProcessor(gameInputMultiplexer);
			break;
		case MAIN_MENU:
			currentScene = menuScene;
			Gdx.input.setInputProcessor(menuInputMultiplexer);
			break;
		case CREDITS:
			currentScene = creditsScene;
			Gdx.input.setInputProcessor(creditsInputMultiplexer);
			break;
		case EXIT:
			Gdx.app.exit();
			break;
		}
	}
}
