package se.danielj.skuttandenyancat.systems;

import se.danielj.skuttandenyancat.components.ParallaxBackground;
import se.danielj.skuttandenyancat.components.Position;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.IntervalEntityProcessingSystem;

public class ParallaxBackgroundSystem extends IntervalEntityProcessingSystem {
	@Mapper
	ComponentMapper<Position> pm;

	private float width;

	@SuppressWarnings("unchecked")
	public ParallaxBackgroundSystem(float width) {
		super(Aspect.getAspectForAll(ParallaxBackground.class, Position.class),
				1);
		this.width = width;
	}

	@Override
	protected void process(Entity e) {
		Position position = pm.get(e);

		if (position.getX() < -2399) {
			position.setX(position.getX() + 2 * 2399 + 1);
		}
	}

}
