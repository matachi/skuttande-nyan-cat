package se.danielj.skuttandenyancat.systems;

import se.danielj.skuttandenyancat.EntityFactory;
import se.danielj.skuttandenyancat.misc.Constants;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.IntervalEntitySystem;
import com.artemis.utils.ImmutableBag;

public class StarSpawnerSystem extends IntervalEntitySystem {

	public StarSpawnerSystem(float interval) {
		super(Aspect.getEmpty(), interval);
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		float xMod = 100 * (float) Math.random();
		float yMod = Constants.FRAME_HEIGHT * (float) Math.random() - Constants.FRAME_HEIGHT / 2;
		EntityFactory.createStar(world, Constants.FRAME_WIDTH / 2 + xMod + 16, yMod * Constants.ZOOM).addToWorld();
	}
}
