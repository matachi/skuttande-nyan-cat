package se.danielj.skuttandenyancat.systems;

import java.util.HashMap;

import se.danielj.skuttandenyancat.misc.Constants;
import se.danielj.skuttandenyancat.misc.Score;

import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class HudRenderSystem extends VoidEntitySystem {
	
	private HashMap<String, AtlasRegion> regions;
    private TextureAtlas textureAtlas;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private BitmapFont font;
    
    public HudRenderSystem(SpriteBatch batch, OrthographicCamera camera) {
        this.batch = batch;
        this.camera = camera;
    }

	@Override
	protected void initialize() {
//		regions = new HashMap<String, AtlasRegion>();
//        textureAtlas = new TextureAtlas(Gdx.files.internal("textures/pack"), Gdx.files.internal("textures"));
//        for (AtlasRegion r : textureAtlas.getRegions()) {
//                regions.put(r.name, r);
//        }

        //batch = new SpriteBatch();

        Texture fontTexture = new Texture(Gdx.files.internal("fonts/ConsolaMono-Bold.png"));
        fontTexture.setFilter(TextureFilter.Linear, TextureFilter.MipMapLinearLinear);
        TextureRegion fontRegion = new TextureRegion(fontTexture);
        font = new BitmapFont(Gdx.files.internal("fonts/ConsolaMono-Bold.fnt"), fontRegion, false);
        font.setUseIntegerPositions(false);
	}
	
	@Override
    protected void begin() {
//            batch.setProjectionMatrix(camera.combined);
            batch.begin();
    }

    @Override
    protected void processSystem() {
            batch.setColor(1, 1, 1, 1);
            font.setScale(1f * camera.zoom);
            font.draw(batch, "SCORE: " + Score.getTotalScore(), (-(Constants.FRAME_WIDTH / 2) + 20) * camera.zoom + camera.position.x, (Constants.FRAME_HEIGHT / 2) * camera.zoom + camera.position.y);
            if (Score.getScore() > 0) {
            	float zoom  = 2 + camera.zoom * Score.getScore() / 1000;
	            font.setScale(zoom);
	            font.draw(batch, Integer.toString(Score.getScore()), 0, 0);
            }
    }
   
    @Override
    protected void end() {
            batch.end();
    }

}
