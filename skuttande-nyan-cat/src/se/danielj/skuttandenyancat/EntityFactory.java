package se.danielj.skuttandenyancat;

import se.danielj.skuttandenyancat.components.ParallaxBackground;
import se.danielj.skuttandenyancat.components.Position;
import se.danielj.skuttandenyancat.components.Sprite;
import se.danielj.skuttandenyancat.components.Velocity;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.math.MathUtils;

public class EntityFactory {
	public static Entity createBackground(World world, float x, float y) {
		Entity e = world.createEntity();
		
		Position position = new Position();
		position.setX(x);
		position.setY(y);
		e.addComponent(position);
		
		Velocity velocity = new Velocity();
		velocity.setX(-50);
		e.addComponent(velocity);
		
		e.addComponent(new ParallaxBackground());
		
		Sprite sprite = new Sprite();
        sprite.name = "background";
        sprite.scaleX = sprite.scaleY = 4;
        sprite.layer = Sprite.Layer.BACKGROUND;
        e.addComponent(sprite);

		return e;
	}
	
	public static Entity createBackground2(World world, float x, float y) {
		Entity e = world.createEntity();
		
		Position position = new Position();
		position.setX(x);
		position.setY(y);
		e.addComponent(position);
		
		Velocity velocity = new Velocity();
		velocity.setX(-100);
		e.addComponent(velocity);
		
		e.addComponent(new ParallaxBackground());
		
		Sprite sprite = new Sprite();
        sprite.name = "background-city";
        sprite.scaleX = sprite.scaleY = 4;
        sprite.layer = Sprite.Layer.ACTORS_3;
        e.addComponent(sprite);

		return e;
	}
	
	public static Entity createNyanCat(World world, float x, float y) {
		Entity e = world.createEntity();
		
		Position position = new Position();
		position.setX(x);
		position.setY(y);
		e.addComponent(position);
		
		Velocity velocity = new Velocity();
		velocity.setX(0);
		e.addComponent(velocity);
		
		Sprite sprite = new Sprite();
        sprite.name = "nyan-cat";
        sprite.scaleX = sprite.scaleY = 2;
        sprite.layer = Sprite.Layer.ACTORS_3;
        e.addComponent(sprite);

		return e;
	}
	
	public static Entity createPole(World world, float x, float y) {
		Entity e = world.createEntity();
		
		Position position = new Position();
		position.setX(x);
		position.setY(y);
		e.addComponent(position);
		
		Velocity velocity = new Velocity();
		velocity.setX(0);
		e.addComponent(velocity);
		
		Sprite sprite = new Sprite();
        sprite.name = "pole";
        sprite.scaleX = sprite.scaleY = 3;
        sprite.layer = Sprite.Layer.ACTORS_3;
        e.addComponent(sprite);
        
		return e;
	}
}
