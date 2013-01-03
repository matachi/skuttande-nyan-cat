package se.danielj.skuttandenyancat.systems;

import se.danielj.skuttandenyancat.components.Player;
import se.danielj.skuttandenyancat.components.Position;
import se.danielj.skuttandenyancat.components.Velocity;
import se.danielj.skuttandenyancat.misc.State;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class PlayerInputSystem extends EntityProcessingSystem implements
		InputProcessor {
	@Mapper
	ComponentMapper<Position> pm;
	@Mapper
	ComponentMapper<Velocity> vm;

	private boolean jump;
	
	@SuppressWarnings("unchecked")
	public PlayerInputSystem() {
		super(Aspect.getAspectForAll(Position.class, Velocity.class,
				Player.class));
	}

	@Override
	protected void initialize() {
		Gdx.input.setInputProcessor(this);
	}

	@Override
	protected void process(Entity e) {
//		Position position = pm.get(e);
		Velocity velocity = vm.get(e);

		if (jump) {
			velocity.setY(velocity.getY() + 2000 * world.delta);
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Input.Keys.SPACE) {
			jump = true;
			State.setRunning(false);
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
		return false;
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
