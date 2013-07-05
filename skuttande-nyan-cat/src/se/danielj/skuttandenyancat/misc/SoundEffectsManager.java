package se.danielj.skuttandenyancat.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * 
 * @author Daniel Jonsson
 * @license GNU GPLv3
 *
 */
public class SoundEffectsManager {

	private static Sound starSound;
	private static Sound landingSound;
	
	public static void init() {
	      starSound = Gdx.audio.newSound(Gdx.files.internal("soundeffects/star.wav"));
	      landingSound = Gdx.audio.newSound(Gdx.files.internal("soundeffects/land.wav"));
	}
	
	public static void star() {
		starSound.play();
	}
	
	public static void landingSound() {
		landingSound.play();
	}
	
	public static void dispose() {
		starSound.dispose();
		landingSound.dispose();
	}
}
