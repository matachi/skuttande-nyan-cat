package se.danielj.skuttandenyancat;

import se.danielj.skuttandenyancat.Controller.Scene;
import se.danielj.skuttandenyancat.misc.Constants;
import se.danielj.skuttandenyancat.misc.Energy;
import se.danielj.skuttandenyancat.misc.GameController;
import se.danielj.skuttandenyancat.misc.Score;
import se.danielj.skuttandenyancat.misc.State;
import se.danielj.skuttandenyancat.systems.CameraSystem;
import se.danielj.skuttandenyancat.systems.CollisionSystem;
import se.danielj.skuttandenyancat.systems.EffectSystem;
import se.danielj.skuttandenyancat.systems.EntityRemoverSystem;
import se.danielj.skuttandenyancat.systems.GameOverSystem;
import se.danielj.skuttandenyancat.systems.GravitySystem;
import se.danielj.skuttandenyancat.systems.HudRenderSystem;
import se.danielj.skuttandenyancat.systems.MovementSystem;
import se.danielj.skuttandenyancat.systems.MusicVolumeSystem;
import se.danielj.skuttandenyancat.systems.ParallaxBackgroundSystem;
import se.danielj.skuttandenyancat.systems.PlayerInputSystem;
import se.danielj.skuttandenyancat.systems.PoleSpawnerSystem;
import se.danielj.skuttandenyancat.systems.SpriteRenderSystem;
import se.danielj.skuttandenyancat.systems.StarSpawnerSystem;

import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game implements GameController {
	private OrthographicCamera camera;
	
	private World world;
	
	private SpriteRenderSystem spriteRenderSystem;
	private HudRenderSystem hudRenderSystem;
	private EffectSystem particleEffectSystem;
	private MusicVolumeSystem musicVolumeSystem;
	
	private RunningState currentState;
	private RunningState drawState;
	private RunningState pauseState;
	
	private Controller controller;
	
	private InputMultiplexer inputMultiplexer;
	
	private boolean running;
	
	private SpriteBatch batch;
	
	public Game(Controller controller, InputMultiplexer inputMultiplexer) {
		this.controller = controller;
		this.inputMultiplexer = inputMultiplexer;
		running = false;
	}
	
	@Override
	public void create() {		
		inputMultiplexer.clear();
		
		camera = new OrthographicCamera(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
		
		Energy.init();
		Score.init();
		State.init();
		
		world = new World();
		world.setManager(new GroupManager());
		world.setSystem(new ParallaxBackgroundSystem(1200));
		world.setSystem(new PlayerInputSystem(inputMultiplexer, this));
		world.setSystem(new GravitySystem());
		world.setSystem(new MovementSystem());
		world.setSystem(new CollisionSystem());
		world.setSystem(new PoleSpawnerSystem(2));
		world.setSystem(new EntityRemoverSystem(1));
		world.setSystem(new StarSpawnerSystem(5.2f));
		world.setSystem(new CameraSystem(camera));
		world.setSystem(new GameOverSystem(1, this));
		
		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);
		
		particleEffectSystem = world.setSystem(new EffectSystem(batch), true);
		spriteRenderSystem = world.setSystem(new SpriteRenderSystem(camera, batch), true);
		hudRenderSystem = world.setSystem(new HudRenderSystem(this, inputMultiplexer), true);
		musicVolumeSystem = world.setSystem(new MusicVolumeSystem(), true);
		musicVolumeSystem.play();
		
		world.initialize();
		
		EntityFactory.createBackground(world, 0, 0).addToWorld();
		EntityFactory.createBackground(world, 1200 * Constants.ZOOM - 5, 0).addToWorld();
		EntityFactory.createBackground2(world, 0, -100 * Constants.ZOOM).addToWorld();
		EntityFactory.createBackground2(world, 1200 * Constants.ZOOM - 5, -100 * Constants.ZOOM).addToWorld();
		EntityFactory.createNyanCat(world, -50 * Constants.ZOOM, 100 * Constants.ZOOM).addToWorld();
		EntityFactory.createPole(world, 0, -100 * Constants.ZOOM).addToWorld();
		EntityFactory.createPole(world, 400 * Constants.ZOOM, -100 * Constants.ZOOM).addToWorld();
		
		drawState = new Draw();
		pauseState = new Pause();
		currentState = drawState;
		
		running = true;
	}

	@Override
	public void render() {
		currentState.process();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
		currentState = pauseState;
		hudRenderSystem.showPauseMenu(true);
		musicVolumeSystem.stop();
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		particleEffectSystem.dispose();
		hudRenderSystem.dispose();
		batch.dispose();
	}

	@Override
	public void continueGame() {
		currentState = drawState;
		hudRenderSystem.showPauseMenu(false);
		musicVolumeSystem.play();
	}
	
	@Override
	public void gameOver() {
		currentState = pauseState;
		hudRenderSystem.showGameOverMenu(true);
		running = false;
		musicVolumeSystem.stop();
	}

	@Override
	public void exit() {
		musicVolumeSystem.stopCompletely();
		controller.setScene(Scene.MAIN_MENU);
		particleEffectSystem.dispose();
	}
	
	@Override
	public void back() {
		if (running) {
			pause();
			musicVolumeSystem.stopCompletely();
			controller.setScene(Scene.MAIN_MENU);
		} else {
			exit();
		}
	}

	@Override
	public void restart() {
		dispose();
		create();
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public void reset() {
		restart();
	}
	
	private interface RunningState {
		public void process();
	}
	
	private class Draw implements RunningState {
		public void process() {
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

			camera.update();

			float delta = Gdx.graphics.getDeltaTime();

			world.setDelta(delta);
			world.process();

			spriteRenderSystem.process();
			particleEffectSystem.process();
			hudRenderSystem.process();
			musicVolumeSystem.process();
		}
	}
	
	private class Pause implements RunningState {
		public void process() {
			world.setDelta(0);
			spriteRenderSystem.process();
			particleEffectSystem.process();
			hudRenderSystem.process();
			world.setDelta(Gdx.graphics.getDeltaTime());
			musicVolumeSystem.process();
		}
	}
}
