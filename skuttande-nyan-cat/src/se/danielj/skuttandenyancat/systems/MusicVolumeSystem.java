package se.danielj.skuttandenyancat.systems;

import se.danielj.skuttandenyancat.misc.MusicManager;

import com.artemis.systems.VoidEntitySystem;

/**
 * 
 * @author Daniel Jonsson
 * @license GNU GPLv3
 *
 */
public class MusicVolumeSystem extends VoidEntitySystem {

	private float volume;
	
	private int mod;
	
	@Override
	protected void initialize() {
		volume = 0;
		mod = 1;
		MusicManager.setVolume(volume);
	}

	@Override
	protected void processSystem() {
		volume += mod * world.getDelta() / 100;
		if (volume > 0.2) {
			volume = 0.2f;
		} else if (volume < 0) {
			volume = 0;
			MusicManager.play(false);
		}
		MusicManager.setVolume(volume);
	}
	
	public void play() {
		mod = 1;
		MusicManager.play(true);
	}
	
	public void stop() {
		mod = -5;
	}
	
	public void stopCompletely() {
		MusicManager.play(false);
		volume = 0;
	}
}
