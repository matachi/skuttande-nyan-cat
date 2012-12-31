package se.danielj.skuttandenyancat.systems;

import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ParticleEffectSystem extends VoidEntitySystem {
	
	private SpriteBatch batch;
	private Camera camera;
	private ParticleEffect particleEffect;

	public ParticleEffectSystem(Camera camera) {
		this.camera = camera;
	}
	
	@Override
	protected void initialize() {
		batch = new SpriteBatch();
		particleEffect = new ParticleEffect();
	    particleEffect.load(Gdx.files.internal("effects/pixel2"), 
	            Gdx.files.internal("effects"));
	    particleEffect.setPosition(100, 100);
	    particleEffect.start();
	}
	
	@Override
	protected void begin() {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
	}
	
	@Override
	protected void processSystem() {
	    particleEffect.draw(batch, Gdx.graphics.getDeltaTime());
	    if (particleEffect.isComplete()) {
	    	particleEffect.start();
	    }
	}
	
	protected void end() {
		batch.end();
	}

}
