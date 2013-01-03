package se.danielj.skuttandenyancat;

import se.danielj.skuttandenyancat.misc.Constants;
import se.danielj.skuttandenyancat.systems.CameraSystem;
import se.danielj.skuttandenyancat.systems.CollisionSystem;
import se.danielj.skuttandenyancat.systems.EffectSystem;
import se.danielj.skuttandenyancat.systems.GravitySystem;
import se.danielj.skuttandenyancat.systems.MovementSystem;
import se.danielj.skuttandenyancat.systems.ParallaxBackgroundSystem;
import se.danielj.skuttandenyancat.systems.PlayerInputSystem;
import se.danielj.skuttandenyancat.systems.PoleRemoverSystem;
import se.danielj.skuttandenyancat.systems.PoleSpawnerSystem;
import se.danielj.skuttandenyancat.systems.SpriteRenderSystem;

import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game implements ApplicationListener {
	private OrthographicCamera camera;
	
	private World world;
	
	private SpriteRenderSystem spriteRenderSystem;
	private EffectSystem particleEffectSystem;
	
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
		world.setSystem(new PlayerInputSystem());
		world.setSystem(new GravitySystem());
		world.setSystem(new MovementSystem());
		world.setSystem(new CollisionSystem());
		world.setSystem(new PoleSpawnerSystem(2));
		world.setSystem(new PoleRemoverSystem(1));
		world.setSystem(new CameraSystem(camera));
		
		SpriteBatch batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);
		
		particleEffectSystem = world.setSystem(new EffectSystem(batch), true);
		spriteRenderSystem = world.setSystem(new SpriteRenderSystem(camera, batch), true);
		world.initialize();
		EntityFactory.createBackground(world, 0, 0).addToWorld();
		EntityFactory.createBackground(world, 2399, 0).addToWorld();
		EntityFactory.createBackground2(world, 0, -200).addToWorld();
		EntityFactory.createBackground2(world, 2399, -200).addToWorld();
		EntityFactory.createNyanCat(world, -100, 200).addToWorld();
		EntityFactory.createPole(world, 0, -200).addToWorld();
		EntityFactory.createPole(world, 800, -200).addToWorld();
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		camera.update();

		float delta = Gdx.graphics.getDeltaTime();
		
		world.setDelta(delta);
		world.process();
	    
		spriteRenderSystem.process();
		particleEffectSystem.process();
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
