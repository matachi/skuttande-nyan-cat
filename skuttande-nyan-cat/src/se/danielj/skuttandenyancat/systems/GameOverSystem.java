package se.danielj.skuttandenyancat.systems;

import se.danielj.skuttandenyancat.components.Player;
import se.danielj.skuttandenyancat.components.Position;
import se.danielj.skuttandenyancat.components.Size;
import se.danielj.skuttandenyancat.misc.Constants;
import se.danielj.skuttandenyancat.misc.GameController;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.managers.GroupManager;
import com.artemis.systems.IntervalEntitySystem;
import com.artemis.utils.ImmutableBag;

/**
 * This system checks if the cat is outside the screen and if the cat/player has
 * died.
 * 
 * @author matachi
 * 
 */
public class GameOverSystem extends IntervalEntitySystem {
	@Mapper
	ComponentMapper<Position> pm;
	@Mapper
	ComponentMapper<Size> sm;

	private Entity cat;
	private Position position;
	
	private GameController gameController;

	@SuppressWarnings("unchecked")
	public GameOverSystem(float interval, GameController gameController) {
		super(Aspect.getAspectForAll(Player.class), interval);
		this.gameController = gameController;
	}
	
	@Override
	protected void initialize() {
		ImmutableBag<Entity> e = world.getManager(GroupManager.class)
				.getEntities(Constants.Groups.CAT);
		if (!e.isEmpty()) {
			cat = e.get(0);
			position = pm.get(cat);
			cat = world.getManager(GroupManager.class)
					.getEntities(Constants.Groups.CAT).get(0);
			position = pm.get(cat);
		}
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> e) {
		if (position.getY() < -Constants.FRAME_HEIGHT) {
			gameController.gameOver();
		}
	}

	@Override
	protected void inserted(Entity e) {
		cat = e;
		position = pm.get(cat);
	}
}
