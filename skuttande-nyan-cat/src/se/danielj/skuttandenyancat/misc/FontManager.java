package se.danielj.skuttandenyancat.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * 
 * @author Daniel Jonsson
 * @license GNU GPLv3
 *
 */
public class FontManager {

	private static BitmapFont consolaMonoBold;
	private static BitmapFont consolaMonoBoldHalf;
	private static Texture fontTexture;

	public static void init() {
		fontTexture = new Texture(
				Gdx.files.internal("fonts/ConsolaMono-Bold.png"));
		fontTexture.setFilter(TextureFilter.Linear,
				TextureFilter.MipMapLinearLinear);
		TextureRegion fontRegion = new TextureRegion(fontTexture);
		consolaMonoBold = new BitmapFont(
				Gdx.files.internal("fonts/ConsolaMono-Bold.fnt"), fontRegion,
				false);
		consolaMonoBoldHalf = new BitmapFont(
				Gdx.files.internal("fonts/ConsolaMono-Bold.fnt"), fontRegion,
				false);
		consolaMonoBoldHalf.scale(0.5f);
	}

	public static BitmapFont getFont() {
		return consolaMonoBold;
	}

	public static BitmapFont getFontHalf() {
		return consolaMonoBoldHalf;
	}

	public static void dispose() {
		consolaMonoBold.dispose();
		consolaMonoBoldHalf.dispose();
		fontTexture.dispose();
	}
}
