package se.danielj.skuttandenyancat.systems;

import se.danielj.skuttandenyancat.components.ParallaxBackground;
import se.danielj.skuttandenyancat.components.Position;
import se.danielj.skuttandenyancat.misc.Constants;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.IntervalEntityProcessingSystem;

public class ParallaxBackgroundSystem extends IntervalEntityProcessingSystem {
	@Mapper
	ComponentMapper<Position> pm;

	/**
	 * Which X position the background need to be less than before it will be
	 * moved 2 times it width to the right.
	 */
	private float limit;

	/**
	 * How far the background will be moved to the right, when it has passed the
	 * left edge of the screen.
	 */
	private float pushBack;

	@SuppressWarnings("unchecked")
	public ParallaxBackgroundSystem(float backgroundWidth) {
		super(Aspect.getAspectForAll(ParallaxBackground.class, Position.class),
				1);
		limit = -Constants.FRAME_WIDTH / 2 - backgroundWidth / 2 * Constants.ZOOM;
		pushBack = 2 * backgroundWidth * Constants.ZOOM - 10;
	}

	@Override
	protected void process(Entity e) {
		Position position = pm.get(e);
		if (position.getX() < limit) {
			position.setX(position.getX() + pushBack);
		}
	}
}
