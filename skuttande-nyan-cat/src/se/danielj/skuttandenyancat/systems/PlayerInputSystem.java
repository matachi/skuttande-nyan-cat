package se.danielj.skuttandenyancat.systems;

import se.danielj.skuttandenyancat.components.Player;
import se.danielj.skuttandenyancat.components.Velocity;
import se.danielj.skuttandenyancat.misc.Constants;
import se.danielj.skuttandenyancat.misc.Energy;
import se.danielj.skuttandenyancat.misc.GameController;
import se.danielj.skuttandenyancat.misc.State;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;

public class PlayerInputSystem extends EntityProcessingSystem implements
		InputProcessor {
	@Mapper
	ComponentMapper<Velocity> vm;
	
	private GameController gameController;

	/**
	 * Store locally if the jump button has been triggered. This variable is
	 * used later when the system is processed.
	 */
	private boolean jump;
	
	@SuppressWarnings("unchecked")
	public PlayerInputSystem(InputMultiplexer inputMultiplexer, GameController gameController) {
		super(Aspect.getAspectForAll(Velocity.class, Player.class));
		inputMultiplexer.addProcessor(this);
		this.gameController = gameController;
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void process(Entity e) {
		Velocity velocity = vm.get(e);
		if (jump) {
			if (Energy.hasEnergy()) {
				velocity.setY(velocity.getY() + Constants.JUMP_FORCE * world.delta * Constants.ZOOM);
				Energy.subtractEnergy(20 * world.delta);
			}
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Input.Keys.SPACE) {
			jump = true;
			State.setRunning(false);
//		} else if (keycode == Input.Keys.ESCAPE) {
//			Gdx.app.exit();
		} else if (keycode == Input.Keys.BACK) {
			gameController.back();
		}

		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Input.Keys.SPACE) {
			jump = false;
		}

		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		jump = true;
		State.setRunning(false);
		return true;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		jump = false;
		return true;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

}
