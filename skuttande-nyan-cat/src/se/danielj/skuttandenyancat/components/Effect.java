package se.danielj.skuttandenyancat.components;

import com.artemis.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class Effect extends Component {
	private ParticleEffect particleEffect;
	
	public Effect(String guid) {
		particleEffect = new ParticleEffect();
	    particleEffect.load(Gdx.files.internal("effects/" + guid), 
	            Gdx.files.internal("effects"));
	    particleEffect.start();
	}
	
	public ParticleEffect getParticleEffect() {
		return particleEffect;
	}
	
	public void dispose() {
		particleEffect.dispose();
	}
}
