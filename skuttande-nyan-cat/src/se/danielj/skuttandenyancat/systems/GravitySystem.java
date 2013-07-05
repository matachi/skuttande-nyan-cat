package se.danielj.skuttandenyancat.systems;

import se.danielj.skuttandenyancat.components.Gravity;
import se.danielj.skuttandenyancat.components.Velocity;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

/**
 * 
 * @author Daniel Jonsson
 * @license GNU GPLv3
 *
 */
public class GravitySystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<Gravity> gm;
	@Mapper ComponentMapper<Velocity> vm;

	@SuppressWarnings("unchecked")
	public GravitySystem() {
		super(Aspect.getAspectForAll(Gravity.class, Velocity.class));
	}

	@Override
	protected void process(Entity e) {
		Gravity gravity  = gm.get(e);
		Velocity velocity = vm.get(e);

		velocity.setY(velocity.getY() + gravity.getForce() * world.delta);
	}
}
