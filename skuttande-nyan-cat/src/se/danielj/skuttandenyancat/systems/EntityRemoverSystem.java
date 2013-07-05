package se.danielj.skuttandenyancat.systems;

import se.danielj.skuttandenyancat.components.Position;
import se.danielj.skuttandenyancat.components.Size;
import se.danielj.skuttandenyancat.misc.Constants;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.managers.GroupManager;
import com.artemis.systems.IntervalEntitySystem;
import com.artemis.utils.ImmutableBag;

/**
 * This system removes poles and stars that have moved passed the left edge of
 * the screen and aren't visible anymore.
 * 
 * @author Daniel Jonsson
 * @license GNU GPLv3
 * 
 */
public class EntityRemoverSystem extends IntervalEntitySystem {
	@Mapper
	ComponentMapper<Position> pm;
	@Mapper
	ComponentMapper<Size> sm;

	public EntityRemoverSystem(float interval) {
		super(Aspect.getEmpty(), interval);
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> e) {
		removeEntities(world.getManager(GroupManager.class).getEntities(Constants.Groups.POLE));
		removeEntities(world.getManager(GroupManager.class).getEntities(Constants.Groups.STAR));
	}
	
	private void removeEntities(ImmutableBag<Entity> entities) {
		for (int b = 0; entities.size() > b; b++) {
			Position p = pm.get(entities.get(b));
			Size s = sm.get(entities.get(b));
			if (p.getX() + s.getWidth() / 2 < -Constants.FRAME_WIDTH / 2) {
				world.deleteEntity(entities.get(b));
				break;
			}
		}
	}
}
