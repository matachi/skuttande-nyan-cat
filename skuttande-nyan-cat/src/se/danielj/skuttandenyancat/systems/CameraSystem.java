package se.danielj.skuttandenyancat.systems;

import se.danielj.skuttandenyancat.misc.State;

import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
public class CameraSystem extends VoidEntitySystem {
	
	private OrthographicCamera camera;
	
	public CameraSystem(OrthographicCamera camera) {
		this.camera = camera;
	}
	
	private float zoom = 0.1f;
	private boolean zoomIn = true;
	private boolean moveUp = true;
	private boolean moveRight = true;

	@Override
	protected void processSystem() {
//				camera.translate(0, zoom * 1000 *world.delta);
		if (State.isRunning()) {
			camera.zoom -= zoom * 3 * world.delta;
//			System.out.println(camera.position.y);
			if (moveUp) {
				camera.translate(0, zoom * 1000 * world.delta);
				if (camera.position.y > 10) {
					moveUp = !moveUp;
				}
			} else {
				camera.translate(0, -zoom * 1000 * world.delta);
				if (camera.position.y < -10) {
					moveUp = !moveUp;
				}
			}
			if (moveRight) {
				camera.translate(zoom * 2000 * world.delta, 0);
				if (camera.position.x > 10) {
					moveRight = !moveRight;
				}
			} else {
				camera.translate(-zoom * 1000 * world.delta, 0);
				if (camera.position.x < -10) {
					moveRight = !moveRight;
				}
			}
		} else {
			camera.zoom += zoom * 5 * world.delta;
			camera.position.y = 0;
			camera.position.x = 0;
		}
		if (camera.zoom < 0.6f) {
			camera.zoom = 0.6f;
			zoomIn = !zoomIn;
		} else if (camera.zoom >= 1) {
			camera.zoom = 1;
			zoomIn = !zoomIn;
		}
		camera.update();
	}

}
