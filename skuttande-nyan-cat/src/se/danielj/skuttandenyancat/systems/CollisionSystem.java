package se.danielj.skuttandenyancat.systems;

import se.danielj.skuttandenyancat.EntityFactory;
import se.danielj.skuttandenyancat.components.Player;
import se.danielj.skuttandenyancat.components.Position;
import se.danielj.skuttandenyancat.components.Size;
import se.danielj.skuttandenyancat.components.Velocity;
import se.danielj.skuttandenyancat.misc.Constants;
import se.danielj.skuttandenyancat.misc.State;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.managers.GroupManager;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;

public class CollisionSystem extends EntitySystem {
	@Mapper
	ComponentMapper<Position> pm;
	@Mapper
	ComponentMapper<Size> sm;
	@Mapper
	ComponentMapper<Velocity> vm;

	private Bag<CollisionPair> collisionPairs;
	
//	private 

	@SuppressWarnings("unchecked")
	public CollisionSystem() {
		super(Aspect.getAspectForAll(Position.class, Size.class));
	}

	@Override
	public void initialize() {
		collisionPairs = new Bag<CollisionPair>();

		collisionPairs.add(new CollisionPair(Constants.Groups.CAT,
				Constants.Groups.POLE, new CollisionHandler() {
					@Override
					public void handleCollision(Entity cat, Entity pole) {
						// Cat
						Position pc = pm.get(cat);
						Size sc = sm.get(cat);
						Velocity vc = vm.get(cat);
						// Pole
						Position pp = pm.get(pole);
						Size sp = sm.get(pole);

						vc.setY(0);
						pc.setY(sc.getHeight() / 2 + pp.getY() + sp.getHeight()
								/ 2);
						if (!State.isRunning()) {
							EntityFactory.createEffect(world, pc.getX(),
									pc.getY() - sc.getHeight() / 2)
									.addToWorld();
							State.setRunning(true);
						}
						running = true;
					}
				}));
	}
	
	private boolean running;

	private float prevX = 0;
	private float prevY = 0;
	
	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		running = false;
		for (int i = 0; collisionPairs.size() > i; i++) {
			collisionPairs.get(i).checkForCollisions();
		}
		if (!running) {
			State.setRunning(false);
		}
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}
	
	@Override
	protected void inserted(Entity e) {
		
	}

	private class CollisionPair {
		private ImmutableBag<Entity> groupEntitiesA;
		private ImmutableBag<Entity> groupEntitiesB;
		private CollisionHandler handler;

		public CollisionPair(String group1, String group2,
				CollisionHandler handler) {
			groupEntitiesA = world.getManager(GroupManager.class).getEntities(
					group1);
			groupEntitiesB = world.getManager(GroupManager.class).getEntities(
					group2);
			this.handler = handler;
		}

		public void checkForCollisions() {
			for (int a = 0; groupEntitiesA.size() > a; a++) {
				for (int b = 0; groupEntitiesB.size() > b; b++) {
					Entity entityA = groupEntitiesA.get(a);
					Entity entityB = groupEntitiesB.get(b);
					if (collisionExists(entityA, entityB)) {
						handler.handleCollision(entityA, entityB);
					}
				}
			}
		}

		private boolean collisionExists(Entity e1, Entity e2) {
			Position p1 = pm.get(e1);
			Position p2 = pm.get(e2);

			Size s1 = sm.get(e1);
			Size s2 = sm.get(e2);

			if (p1.getX() + s1.getWidth() / 2 < p2.getX() - s2.getWidth() / 2) {
				return false;
			}

			if (p1.getX() - s1.getWidth() / 2 > p2.getX() + s2.getWidth() / 2) {
				return false;
			}

			if (p1.getY() + s1.getHeight() / 2 < p2.getY() - s2.getHeight() / 2) {
				return false;
			}

			if (p1.getY() - s1.getHeight() / 2 > p2.getY() + s2.getHeight() / 2) {
				return false;
			}

			return true;
		}
	}

	private interface CollisionHandler {
		void handleCollision(Entity a, Entity b);
	}

}
