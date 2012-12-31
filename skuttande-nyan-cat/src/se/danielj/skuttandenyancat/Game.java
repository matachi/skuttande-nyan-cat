package se.danielj.skuttandenyancat;

import se.danielj.skuttandenyancat.misc.Constants;
import se.danielj.skuttandenyancat.systems.MovementSystem;
import se.danielj.skuttandenyancat.systems.ParallaxBackgroundSystem;
import se.danielj.skuttandenyancat.systems.ParticleEffectSystem;
import se.danielj.skuttandenyancat.systems.SpriteRenderSystem;

import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Game implements ApplicationListener {
	private OrthographicCamera camera;
	
	private World world;
	
	private SpriteRenderSystem spriteRenderSystem;
	private ParticleEffectSystem particleEffectSystem;
	
	@Override
	public void create() {		
		
		float w = Gdx.graphics.getWidth();
		//float h = Gdx.graphics.getHeight();
		
		//camera = new OrthographicCamera(1, h/w);
		camera = new OrthographicCamera(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
		//camera.translate(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		//camera.translate(Constants.FRAME_WIDTH / 2, Constants.FRAME_HEIGHT / 2);
		//camera.translate(Constants.FRAME_WIDTH / 2, 0);
		//camera.translate(0, 360);
		
		world = new World();
		world.setManager(new GroupManager());
		world.setSystem(new ParallaxBackgroundSystem(Constants.FRAME_WIDTH));
		world.setSystem(new MovementSystem());
		
		particleEffectSystem = world.setSystem(new ParticleEffectSystem(camera), true);
		spriteRenderSystem = world.setSystem(new SpriteRenderSystem(camera), true);
		world.initialize();
		EntityFactory.createBackground(world, 0, 0).addToWorld();
		EntityFactory.createBackground(world, 2399, 0).addToWorld();
//		EntityFactory.createBackground(world, 2560, 0).addToWorld();
		EntityFactory.createBackground2(world, 0, -200).addToWorld();
		EntityFactory.createBackground2(world, 2399, -200).addToWorld();
		EntityFactory.createNyanCat(world, -100, 0).addToWorld();
		EntityFactory.createPole(world, -100, 0).addToWorld();
//		EntityFactory.createBackground2(world, 2560, -200).addToWorld();
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		camera.update();

		float delta = Gdx.graphics.getDeltaTime();
		
		world.setDelta(delta);
//		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
//			for (int i = 0; 10 > i; i++) {
//				world.process();
//			}
//		}
		world.process();
	    
		spriteRenderSystem.process();
		particleEffectSystem.process();
//		healthRenderSystem.process();
//		hudRenderSystem.process();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
}
