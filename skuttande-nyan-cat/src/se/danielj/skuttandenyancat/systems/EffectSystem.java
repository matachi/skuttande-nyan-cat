package se.danielj.skuttandenyancat.systems;

import se.danielj.skuttandenyancat.components.Effect;
import se.danielj.skuttandenyancat.components.Position;
import se.danielj.skuttandenyancat.misc.Constants;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.managers.GroupManager;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;

public class EffectSystem extends EntityProcessingSystem {
	@Mapper
	ComponentMapper<Position> pm;
	@Mapper
	ComponentMapper<Effect> em;

	private SpriteBatch batch;

	@SuppressWarnings("unchecked")
	public EffectSystem(SpriteBatch batch) {
		super(Aspect.getAspectForAll(Position.class, Effect.class));
		this.batch = batch;
	}

	@Override
	protected void begin() {
		batch.begin();
	}

	@Override
	protected void process(Entity e) {
		Position position = pm.get(e);
		Matrix4 m = new Matrix4();
		m.translate(position.getX(), position.getY(), 0);
		batch.setTransformMatrix(m);
		ParticleEffect particleEffect = em.get(e).getParticleEffect();
		particleEffect.draw(batch, world.getDelta());
		if (particleEffect.isComplete()) {
			particleEffect.dispose();
			world.deleteEntity(e);
		}
		m.translate(-position.getX(), -position.getY(), 0);
		batch.setTransformMatrix(m);
	}

	@Override
	protected void end() {
		batch.end();
	}

	public void dispose() {
		ImmutableBag<Entity> entities = world.getManager(GroupManager.class)
				.getEntities(Constants.Groups.EFFECT);
		for (int i = 0; i < entities.size(); ++i) {
			ParticleEffect particleEffect = em.get(entities.get(i))
					.getParticleEffect();
			particleEffect.dispose();
		}
	}
}
