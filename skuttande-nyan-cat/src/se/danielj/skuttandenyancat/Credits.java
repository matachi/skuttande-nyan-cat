package se.danielj.skuttandenyancat;

import se.danielj.skuttandenyancat.Controller.Scene;
import se.danielj.skuttandenyancat.misc.Constants;
import se.danielj.skuttandenyancat.misc.FontManager;
import se.danielj.skuttandenyancat.misc.SpriteManager;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Credits implements ApplicationListener, InputProcessor {
	private OrthographicCamera camera;
	
	private Controller controller;
	
	private AtlasRegion logo;
	private AtlasRegion menuButtonAtlas;
	private AtlasRegion menuButtonPressedAtlas;
	
	private SpriteBatch batch;
	
	private Stage stage;
	
	private BitmapFont font;
	private BitmapFont buttonFont;
	
	private Label text;
	
	private InputMultiplexer inputMultiplexer;
	
	public Credits(Controller controller, InputMultiplexer inputMultiplexer) {
		this.controller = controller;
		this.inputMultiplexer = inputMultiplexer;
	}

	@Override
	public void create() {
		camera = new OrthographicCamera(Constants.FRAME_WIDTH * 2, Constants.FRAME_HEIGHT * 2);
		
        logo = SpriteManager.getSprite("1gam-logo");
        menuButtonAtlas = SpriteManager.getSprite("pause-menu-button");
		menuButtonPressedAtlas = SpriteManager.getSprite("pause-menu-button-pressed");
		
        batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);
		Matrix4 m = new Matrix4();
		m.translate(-Constants.FRAME_WIDTH, -Constants.FRAME_HEIGHT, 0);
		batch.setTransformMatrix(m);
		
		font = FontManager.getFontHalf();
		buttonFont = FontManager.getFont();
		
		stage = new Stage();
		stage.setViewport(Constants.FRAME_WIDTH * 2, Constants.FRAME_HEIGHT * 2, false);
        inputMultiplexer.addProcessor(stage);
		stage.addActor(new MenuButton("Return", 0, new MenuButtonListener() {
			@Override
			protected void action() {
				controller.setScene(Scene.MAIN_MENU);
			}
		}));
		
        LabelStyle labelStyle = new LabelStyle();
        font.setScale(0.5f);
		labelStyle.font = font;
		String textString = "Skuttande Nyan Cat was made during January 2013 for One Game A Month\n\n\n\n\nProgramming & graphics: Daniel \"MaTachi\" Jonsson, www.danielj.se\n\nJava libraries: LibGDX & Artemis\nFonts: Consola Mono & Intuitive\nBackground music: Nyanyanyanyanyanyanya! by daniwellP\nSoftware used: Eclipse, GIMP, Aseprite & MyPaint on Ubuntu\nSound effects editor used: as3sfxr";
		text = new Label(textString, labelStyle);
        text.setPosition(60, 180);
        
        inputMultiplexer.addProcessor(this);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(logo, (1280 - 384) / 2, 500, 384, 104);
		text.draw(batch, 1);
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
		stage.dispose();
		batch.dispose();
	}
	
	private class MenuButton extends Group {
		TextButton button;
		public MenuButton(String label, int menuNumber, InputListener inputListener) {
			final TextButtonStyle style = new TextButtonStyle();
			style.font = buttonFont;
			style.up = new TextureRegionDrawable(menuButtonAtlas);
			style.down = new TextureRegionDrawable(menuButtonPressedAtlas);
			style.fontColor = new Color(1, 1, 1, 1);
			
			button = new TextButton(label, style);
			button.setSize(424, 84);
			button.setPosition(100, 50 - (84 + 28) * menuNumber);
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
			controller.setScene(Scene.MAIN_MENU);
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
