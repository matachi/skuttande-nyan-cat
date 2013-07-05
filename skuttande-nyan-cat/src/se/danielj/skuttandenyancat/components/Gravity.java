package se.danielj.skuttandenyancat.components;

import com.artemis.Component;

/**
 * 
 * @author Daniel Jonsson
 * @license GNU GPLv3
 *
 */
public class Gravity extends Component {
	private float force;

	public float getForce() {
		return force;
	}

	public void setForce(float force) {
		this.force = force;
	}
}
