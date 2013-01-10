package se.danielj.skuttandenyancat.systems;

import se.danielj.skuttandenyancat.misc.Constants;
import se.danielj.skuttandenyancat.misc.Energy;
import se.danielj.skuttandenyancat.misc.FontManager;
import se.danielj.skuttandenyancat.misc.GameController;
import se.danielj.skuttandenyancat.misc.Score;
import se.danielj.skuttandenyancat.misc.SpriteManager;

import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class HudRenderSystem extends VoidEntitySystem {

	private Texture fontTexture;
	
	/**
	 * Font for the total score
	 */
	private BitmapFont font;
	
	/**
	 * Font for the counting score, which is visible when you are running on a
	 * pole
	 */
	private BitmapFont scoreFont;
	
	private Stage stage;
	
	private AtlasRegion pixel;
	private AtlasRegion pauseButtonAtlas;
	private AtlasRegion pauseButtonPressedAtlas;
	private AtlasRegion menuButtonAtlas;
	private AtlasRegion menuButtonPressedAtlas;
	
	private Group totalScoreGroup;
	private Label totalScore;
	private LabelStyle totalScoreLabelStyle;
	private static final float totalScoreX = 20;
	private static final float totalScoreY = Constants.FRAME_HEIGHT - 50;
	private Group scoreGroup;
	private Label score;
	private LabelStyle scoreLabelStyle;
	private static final float scoreX = 400;
	private static final float scoreY = Constants.FRAME_HEIGHT - 250;
	
	private Button pauseButton;
	
	private PauseMenu pauseMenu;
	private GameOverMenu gameOverMenu;
	
	private GameController gameController;
	
	private enum TextState {
		COUNTING, MOVING, HIDDEN
	};
	private TextState currentScoreState;
	
	private MoveToAction animationAction;

	public HudRenderSystem(GameController gameController, InputMultiplexer inputMultiplexer) {
		this.gameController = gameController;
        stage = new Stage();
        inputMultiplexer.addProcessor(0, stage);
	}

	@Override
	protected void initialize() {
		fontTexture = new Texture(
				Gdx.files.internal("fonts/ConsolaMono-Bold.png"));
		fontTexture.setFilter(TextureFilter.Linear,
				TextureFilter.MipMapLinearLinear);
		TextureRegion fontRegion = new TextureRegion(fontTexture);
//		font = new BitmapFont(Gdx.files.internal("fonts/ConsolaMono-Bold.fnt"),
//				fontRegion, false);
		font = FontManager.getFontHalf();
		scoreFont = new BitmapFont(Gdx.files.internal("fonts/ConsolaMono-Bold.fnt"),
				fontRegion, false);
		
		pixel = SpriteManager.getSprite("pixel");
		pauseButtonAtlas = SpriteManager.getSprite("pause");
		pauseButtonPressedAtlas = SpriteManager.getSprite("pause-pressed");
		menuButtonAtlas = SpriteManager.getSprite("pause-menu-button");
		menuButtonPressedAtlas = SpriteManager.getSprite("pause-menu-button-pressed");
        
        stage.setViewport(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT, true);
        
        TextButtonStyle style = new TextButtonStyle();
        style.up = new TextureRegionDrawable(pauseButtonAtlas);
        style.down = new TextureRegionDrawable(pauseButtonPressedAtlas);
        style.font = font;
        style.fontColor = new Color(1, 0, 0, 1);
        pauseButton = new Button(style);
        pauseButton.setSize(30, 30);
        pauseButton.setPosition(15, 10);
		pauseButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				gameController.pause();
				return true;
			}
		});
        stage.addActor(pauseButton);
        
        totalScoreLabelStyle = new LabelStyle();
        totalScoreGroup = new Group();
        font.setScale(0.5f);
        totalScoreLabelStyle.font = font;
        totalScore = new Label("Score: 0", totalScoreLabelStyle);
        totalScore.setScale(0.1f);
        totalScore.setPosition(totalScoreX, totalScoreY);
        totalScoreGroup.addActor(totalScore);
        stage.addActor(totalScoreGroup);
        
        scoreLabelStyle = new LabelStyle();
        scoreLabelStyle.font = scoreFont;
        scoreGroup = new Group();
        score = new Label("", scoreLabelStyle);
        score.setPosition(scoreX, scoreY);
        scoreGroup.addActor(score);
        stage.addActor(scoreGroup);
        
        stage.addActor(new EnergyBarActor());
        
        animationAction = new MoveToAction();
        animationAction.setPosition(totalScoreX, totalScoreY + 50);
        animationAction.setDuration(0.5f);
        
        currentScoreState = TextState.HIDDEN;
        
        pauseMenu = new PauseMenu();
        stage.addActor(pauseMenu);
        pauseMenu.setVisible(false);
        
        gameOverMenu = new GameOverMenu();
        stage.addActor(gameOverMenu);
        gameOverMenu.setVisible(false);
	}
	
	@Override
	protected void processSystem() {
		// Total score
		totalScore.setText("Score: " + Score.getTotalScore());
		
		// Score
		if (Score.getScore() > 0) {
			// remove the animation if the text is still moving
			if (currentScoreState == TextState.MOVING) {
				resetAnimationAction();
			}
			float zoom = (0.5f + Score.getScore() / 1400f)
					* Constants.ZOOM;
			scoreLabelStyle.font.setScale(zoom);
			score.setText(Integer.toString(Score.getScore()));
			score.setVisible(true);
	        currentScoreState = TextState.COUNTING;
		} else {
			if (currentScoreState == TextState.MOVING && animationAction.getTime() > 0.45f) {
				// if the animation action is done, reset the score text
				currentScoreState = TextState.HIDDEN;
				resetAnimationAction();
			} else if (currentScoreState == TextState.COUNTING) {
				// if it was counting, add the animation action
				score.addAction(animationAction);
				currentScoreState = TextState.MOVING;
			}
		}
		
		// Draw
		stage.act(world.delta);
		stage.draw();
	}
	
	private void resetAnimationAction() {
		score.removeAction(animationAction);
		animationAction.reset();
		animationAction.setPosition(totalScoreX, totalScoreY + 50);
		animationAction.setDuration(0.5f);
		score.setPosition(scoreX, scoreY);
		score.setVisible(false);
	}
	
	private class EnergyBarActor extends Actor {
		@Override
		public void draw(SpriteBatch batch, float parentAlpha) {
			float scale = Energy.getEnergy() / 100;
			batch.setColor(1, 1, 1, 0.3f);
			batch.draw(pixel, 20, 70, 20, 200);
			batch.setColor(1 - scale, scale, 0f, 0.5f);
			batch.draw(pixel, 22, 72, 16, 196 * scale);
		}
	}
	
	private class GameOverMenu extends Menu {
		public GameOverMenu() {
			super();
			
			// Menu background
			Image i = new Image(pixel);
			i.setPosition(200, (Constants.FRAME_HEIGHT - 182) / 2 + 42 + 14);
			i.setSize(Constants.FRAME_WIDTH - 400, 182 - 42 - 14);
			Group g = new Group();
			g.setColor(1, 1, 1, 0.5f);
			g.addActor(i);
			this.addActor(g);
			
			this.addActor(new MenuLabel("Game Over"));
			
			// Menu buttons
			this.addActor(new MenuButton("Restart", 1, new MenuButtonListener() {
				@Override
				protected void action() {
					gameController.restart();
				}
			}));
			this.addActor(new MenuButton("Exit", 2, new MenuButtonListener() {
				@Override
				protected void action() {
					gameController.exit();
				}
			}));
		}
	}
	
	private class PauseMenu extends Menu {
		public PauseMenu() {
			super();
			
			// Menu background
			Image i = new Image(pixel);
			i.setPosition(200, (Constants.FRAME_HEIGHT - 182) / 2);
			i.setSize(Constants.FRAME_WIDTH - 400, 182);
			Group g = new Group();
			g.setColor(1, 1, 1, 0.5f);
			g.addActor(i);
			this.addActor(g);
			
			this.addActor(new MenuLabel("Game Paused"));
			
			// Menu buttons
			this.addActor(new MenuButton("Resume", 1, new MenuButtonListener() {
				@Override
				protected void action() {
					gameController.continueGame();
				}
			}));
			this.addActor(new MenuButton("Restart", 2, new MenuButtonListener() {
				@Override
				protected void action() {
					gameController.restart();
				}
			}));
			this.addActor(new MenuButton("Exit", 3, new MenuButtonListener() {
				@Override
				protected void action() {
					gameController.exit();
				}
			}));
		}
	}
	
	private class Menu extends Group {
		/**
		 * A menu button.
		 * 
		 * @author matachi
		 * 
		 */
		protected class MenuButton extends Group {
			TextButton button;
			public MenuButton(String label, int menuNumber, InputListener inputListener) {
				final TextButtonStyle style = new TextButtonStyle();
				style.font = font;
				style.up = new TextureRegionDrawable(menuButtonAtlas);
				style.down = new TextureRegionDrawable(menuButtonPressedAtlas);
				style.fontColor = new Color(1, 1, 1, 1);
				
				button = new TextButton(label, style);
				button.setSize(212, 42);
				button.setPosition((Constants.FRAME_WIDTH - 212) / 2, (Constants.FRAME_HEIGHT + 182) / 2 - (42 + 14) * menuNumber);
				this.addListener(inputListener);
				this.addActor(button);
			}
			public TextButton getButton() {
				return button;
			}
		}
		
		protected class MenuLabel extends Group {
			TextButton button;
			public MenuLabel(String label) {
				final TextButtonStyle style = new TextButtonStyle();
				style.font = font;
				style.fontColor = new Color(1, 1, 1, 1);
				
				button = new TextButton(label, style);
				button.setSize(212, 42);
				button.setPosition((Constants.FRAME_WIDTH - 212) / 2, (Constants.FRAME_HEIGHT + 182) / 2);
				this.addActor(button);
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
	}
	
	public void showPauseMenu(boolean visible) {
		pauseMenu.setVisible(visible);
		pauseButton.setVisible(!visible);
	}
	
	public void showGameOverMenu(boolean visible) {
		gameOverMenu.setVisible(visible);
		pauseButton.setVisible(!visible);
	}
	
	public void dispose() {
		fontTexture.dispose();
		scoreFont.dispose();
		stage.dispose();
	}
}
