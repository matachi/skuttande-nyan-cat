package se.danielj.skuttandenyancat.systems;

import se.danielj.skuttandenyancat.misc.Constants;
import se.danielj.skuttandenyancat.misc.Energy;
import se.danielj.skuttandenyancat.misc.Score;

import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class HudRenderSystem extends VoidEntitySystem {

	private SpriteBatch batch;
	private OrthographicCamera camera;
	private BitmapFont font;
	
	private Stage stage;
	
	private AtlasRegion pixel;
	private AtlasRegion pauseButton;
	
	private Label score;

	public HudRenderSystem(SpriteBatch batch, OrthographicCamera camera) {
		this.batch = batch;
		this.camera = camera;
	}

	@Override
	protected void initialize() {
		Texture fontTexture = new Texture(
				Gdx.files.internal("fonts/ConsolaMono-Bold.png"));
		fontTexture.setFilter(TextureFilter.Linear,
				TextureFilter.MipMapLinearLinear);
		TextureRegion fontRegion = new TextureRegion(fontTexture);
		font = new BitmapFont(Gdx.files.internal("fonts/ConsolaMono-Bold.fnt"),
				fontRegion, false);
//		font.setUseIntegerPositions(false);
		
        TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("sprites/sprites.atlas"), Gdx.files.internal("sprites"));
        for (AtlasRegion r : textureAtlas.getRegions()) {
        	if (r.name.equals("pixel")) {
	            pixel = r;
        	} else if (r.name.equals("pause")) {
        		pauseButton = r;
        	}
        }
        
        stage = new Stage();
        stage.setViewport(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT, true);
//        stage.setViewport(Constants.FRAME_WIDTH, 500, true);
        InputMultiplexer i = new InputMultiplexer();
        i.addProcessor(stage);
        i.addProcessor(Gdx.input.getInputProcessor());
        Gdx.input.setInputProcessor(i);
        
        TextButtonStyle style = new TextButtonStyle();
        style.up = new TextureRegionDrawable(pauseButton);
        style.down = new TextureRegionDrawable(pixel);
        style.font = font;
        style.fontColor = new Color(1, 0, 0, 1);
//        TextButton b = new TextButton("Exit", style);
        Button b = new Button(style);
//        b.setBounds(0, 0, 100, 100);
//        b.pad(0);
//        b.padTop(-40);
//        b.padLeft(0);
        b.setSize(30, 30);
        b.setPosition(15, 10);
//        b.setScale(0.5f);
		b.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				System.out.println("down");
				return true;
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				System.out.println("up");
			}
		});
        stage.addActor(b);
        
        LabelStyle style2 = new LabelStyle();
        style2.font = font;
        score = new Label("test", style2);
        score.setSize(800, 200);
        score.setPosition(20, Constants.FRAME_HEIGHT - 150);
        stage.addActor(score);
	}
	
	@Override
	protected void processSystem() {
		float x = (-(Constants.FRAME_WIDTH / 2) + 20) * camera.zoom + camera.position.x;
		float y = (Constants.FRAME_HEIGHT / 2) * camera.zoom + camera.position.y;
		batch.begin();
//		batch.setColor(1, 1, 1, 0.5f);
//		font.setScale(0.5f * camera.zoom * Constants.ZOOM);
//		font.draw(batch, "SCORE: " + Score.getTotalScore(), x, y);
		score.setText("SCORE: " + Score.getTotalScore());
		if (Score.getScore() > 0) {
			float zoom = (1 + camera.zoom * Score.getScore() / 2000)
					* Constants.ZOOM;
//			font.setScale(zoom);
			font.draw(batch, Integer.toString(Score.getScore()), 0, 0);
		}
		
		y = -(Constants.FRAME_HEIGHT / 2) * camera.zoom + camera.position.y;
		float scale = Energy.getEnergy() / 100;
		batch.setColor(1, 1, 1, 0.3f);
		batch.draw(pixel, x, y + 50 * camera.zoom, 20 * camera.zoom, 200 * camera.zoom);
		batch.setColor(1 - scale, scale, 0f, 0.5f);
		batch.draw(pixel, x + 2 * camera.zoom, y + 52 * camera.zoom, 16 * camera.zoom, 196 * scale * camera.zoom);
		batch.end();
		
		stage.act(world.delta);
		stage.draw();
	}
}
