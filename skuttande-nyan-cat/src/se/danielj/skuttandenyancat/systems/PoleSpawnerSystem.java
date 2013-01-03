package se.danielj.skuttandenyancat.systems;

import se.danielj.skuttandenyancat.EntityFactory;
import se.danielj.skuttandenyancat.misc.Constants;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.IntervalEntitySystem;
import com.artemis.utils.ImmutableBag;

public class PoleSpawnerSystem extends IntervalEntitySystem {

	public PoleSpawnerSystem(float interval) {
		super(Aspect.getEmpty(), interval);
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		EntityFactory.createPole(world, Constants.FRAME_WIDTH, -200).addToWorld();
	}
}
