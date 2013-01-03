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

public class PoleRemoverSystem extends IntervalEntitySystem {
	@Mapper
	ComponentMapper<Position> pm;
	@Mapper
	ComponentMapper<Size> sm;

	public PoleRemoverSystem(float interval) {
		super(Aspect.getEmpty(), interval);
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		ImmutableBag<Entity> groupEntitiesB;
		groupEntitiesB = world.getManager(GroupManager.class).getEntities(Constants.Groups.POLE);
		for (int b = 0; groupEntitiesB.size() > b; b++) {
			Position p = pm.get(groupEntitiesB.get(b));
			Size s = sm.get(groupEntitiesB.get(b));
			if (p.getX() + s.getWidth() / 2 < -Constants.FRAME_WIDTH / 2) {
				world.deleteEntity(groupEntitiesB.get(b));
				break;
			}
		}
	}
}
