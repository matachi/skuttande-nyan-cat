package se.danielj.skuttandenyancat;

import se.danielj.skuttandenyancat.Controller.Scene;
import se.danielj.skuttandenyancat.misc.Constants;
import se.danielj.skuttandenyancat.misc.FontManager;
import se.danielj.skuttandenyancat.misc.SpriteManager;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * 
 * @author Daniel Jonsson
 * @license GNU GPLv3
 *
 */
public class MainMenu implements ApplicationListener, InputProcessor {
	private OrthographicCamera camera;
	
	private Controller controller;
	
	private AtlasRegion background;
	private AtlasRegion menuButtonAtlas;
	private AtlasRegion menuButtonPressedAtlas;
	
	private SpriteBatch batch;
	
	private Stage stage;
	
	private BitmapFont font;
	
	private InputMultiplexer inputMultiplexer;
	
	public MainMenu(Controller controller, InputMultiplexer inputMultiplexer) {
		this.controller = controller;
		this.inputMultiplexer = inputMultiplexer;
	}

	@Override
	public void create() {
		camera = new OrthographicCamera(Constants.FRAME_WIDTH * 2, Constants.FRAME_HEIGHT * 2);
		
        background = SpriteManager.getSprite("main-menu-background");
        menuButtonAtlas = SpriteManager.getSprite("pause-menu-button");
		menuButtonPressedAtlas = SpriteManager.getSprite("pause-menu-button-pressed");
        
        batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);
		Matrix4 m = new Matrix4();
		m.translate(-Constants.FRAME_WIDTH, -Constants.FRAME_HEIGHT, 0);
		batch.setTransformMatrix(m);
		
		font = FontManager.getFont();
		
		stage = new Stage();
		stage.setViewport(Constants.FRAME_WIDTH * 2, Constants.FRAME_HEIGHT * 2, false);
        inputMultiplexer.addProcessor(stage);
		stage.addActor(new MenuButton("Play", 0, new MenuButtonListener() {
			@Override
			protected void action() {
				controller.setScene(Scene.GAME);
			}
		}));
		stage.addActor(new MenuButton("Credits", 1, new MenuButtonListener() {
			@Override
			protected void action() {
				controller.setScene(Scene.CREDITS);
			}
		}));
		stage.addActor(new MenuButton("Exit", 2, new MenuButtonListener() {
			@Override
			protected void action() {
				controller.setScene(Scene.EXIT);
			}
		}));
		
		inputMultiplexer.addProcessor(this);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void render() {
		batch.begin();
		batch.draw(background, 0, 0, 1280, 720);
		batch.end();
		stage.draw();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		stage.cancelTouchFocus();
		stage.clear();
		stage.dispose();
		batch.dispose();
	}
	
	private class MenuButton extends Group {
		TextButton button;
		public MenuButton(String label, int menuNumber, InputListener inputListener) {
			final TextButtonStyle style = new TextButtonStyle();
			style.font = font;
			style.up = new TextureRegionDrawable(menuButtonAtlas);
			style.down = new TextureRegionDrawable(menuButtonPressedAtlas);
			style.fontColor = new Color(1, 1, 1, 1);
			
			button = new TextButton(label, style);
			button.setSize(424, 84);
			button.setPosition(800, 370 - (84 + 28) * menuNumber);
			this.addListener(inputListener);
			this.addActor(button);
		}
		public TextButton getButton() {
			return button;
		}
	}
	
	protected abstract class MenuButtonListener extends InputListener {
		private boolean pressed = false;
		@Override
		public boolean touchDown(InputEvent event, float x, float y,
				int pointer, int button) {
			pressed = true;
			return true;
		}
		@Override
		public void touchUp(InputEvent event, float x, float y,
				int pointer, int button) {
			if (pressed)
				action();
		}
		@Override
		public void touchDragged(InputEvent event, float x, float y,
				int pointer) {
			pressed = ((MenuButton)event.getListenerActor()).getButton().isPressed();
		}
		protected abstract void action();
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Input.Keys.BACK) {
			controller.setScene(Scene.EXIT);
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
