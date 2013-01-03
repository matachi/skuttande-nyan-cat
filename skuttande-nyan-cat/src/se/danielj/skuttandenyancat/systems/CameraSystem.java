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

	@Override
	protected void processSystem() {
//				camera.translate(0, zoom * 1000 *world.delta);
		if (State.isRunning()) {
			camera.zoom -= zoom * world.delta;
			System.out.println(camera.position.y);
			if (moveUp) {
				camera.translate(0, zoom * 1000 *world.delta);
				if (camera.position.y > 10) {
					moveUp = !moveUp;
				}
			} else {
				camera.translate(0, -zoom * 1000 * world.delta);
				if (camera.position.y < -10) {
					moveUp = !moveUp;
				}
			}
		} else {
			camera.zoom += zoom * 5 * world.delta;
			camera.position.y = 0;
		}
		if (camera.zoom < 0.8f) {
			camera.zoom = 0.8f;
			zoomIn = !zoomIn;
		} else if (camera.zoom >= 1) {
			camera.zoom = 1;
			zoomIn = !zoomIn;
		}
		camera.update();
	}

}
