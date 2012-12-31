package se.danielj.skuttandenyancat.systems;

import se.danielj.skuttandenyancat.components.Position;
import se.danielj.skuttandenyancat.components.Velocity;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

public class MovementSystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<Position> pm;
	@Mapper ComponentMapper<Velocity> vm;

	@SuppressWarnings("unchecked")
	public MovementSystem() {
		super(Aspect.getAspectForAll(Position.class, Velocity.class));
	}

	@Override
	protected void process(Entity e) {
		Position position = pm.get(e);
		Velocity velocity = vm.get(e);

		position.setX(position.getX() + velocity.getX() * world.delta);
		position.setY(position.getY() + velocity.getY() * world.delta);
	}

}
