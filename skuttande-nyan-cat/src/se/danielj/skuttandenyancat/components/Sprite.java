package se.danielj.skuttandenyancat.components;

import com.artemis.Component;

/**
 * 
 * @author Daniel Jonsson
 * @license GNU GPLv3
 *
 */
public class Sprite extends Component {
	public enum Layer {
		DEFAULT, BACKGROUND_1, BACKGROUND_2, ACTORS_1, ACTORS_2, ACTORS_3, PARTICLES;

		public int getLayerId() {
			return ordinal();
		}
	}

	public enum State {
		STANDING_LEFT, STANDING_RIGHT, STANDING_UP, STANDING_DOWN, WALKING_LEFT, WALKING_RIGHT, WALKING_UP, WALKING_DOWN
	}

	public String name;
	public float scaleX = 1;
	public float scaleY = 1;
	public float rotation;
	public float offset = 0;
	public float r = 1;
	public float g = 1;
	public float b = 1;
	public float a = 1;
	public Layer layer = Layer.DEFAULT;
	public State state = State.STANDING_DOWN;

}
