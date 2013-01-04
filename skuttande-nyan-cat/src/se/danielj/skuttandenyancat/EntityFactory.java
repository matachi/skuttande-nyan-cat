package se.danielj.skuttandenyancat;

import se.danielj.skuttandenyancat.components.Effect;
import se.danielj.skuttandenyancat.components.Gravity;
import se.danielj.skuttandenyancat.components.ParallaxBackground;
import se.danielj.skuttandenyancat.components.Player;
import se.danielj.skuttandenyancat.components.Position;
import se.danielj.skuttandenyancat.components.Size;
import se.danielj.skuttandenyancat.components.Sprite;
import se.danielj.skuttandenyancat.components.Velocity;
import se.danielj.skuttandenyancat.misc.Constants;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;

public class EntityFactory {
	public static Entity createBackground(World world, float x, float y) {
		Entity e = world.createEntity();
		
		Position position = new Position();
		position.setX(x);
		position.setY(y);
		e.addComponent(position);
		
		Velocity velocity = new Velocity();
		velocity.setX(-25 * Constants.ZOOM);
		e.addComponent(velocity);
		
		e.addComponent(new ParallaxBackground());
		
		Sprite sprite = new Sprite();
        sprite.name = "background";
        sprite.scaleX = sprite.scaleY = Constants.ZOOM * 2;
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
		velocity.setX(-50 * Constants.ZOOM);
		e.addComponent(velocity);
		
		e.addComponent(new ParallaxBackground());
		
		Sprite sprite = new Sprite();
        sprite.name = "background-city";
        sprite.scaleX = sprite.scaleY = Constants.ZOOM * 2;
        sprite.layer = Sprite.Layer.ACTORS_1;
        e.addComponent(sprite);

		return e;
	}
	
	public static Entity createNyanCat(World world, float x, float y) {
		Entity e = world.createEntity();
		
		Position position = new Position();
		position.setX(x);
		position.setY(y);
		e.addComponent(position);
		
		Size size = new Size();
		size.setWidth((int)(33 * Constants.ZOOM));
		size.setHeight((int)(31 * Constants.ZOOM));
		e.addComponent(size);
		
		Gravity gravity = new Gravity();
		gravity.setForce(-500 * Constants.ZOOM);
		e.addComponent(gravity);
		
		Velocity velocity = new Velocity();
		velocity.setX(0);
		e.addComponent(velocity);
		
		Sprite sprite = new Sprite();
        sprite.name = "nyan-cat";
        sprite.scaleX = sprite.scaleY = Constants.ZOOM;
        sprite.offset = 19 * Constants.ZOOM;
        sprite.layer = Sprite.Layer.ACTORS_2;
        e.addComponent(sprite);
        
        Player player = new Player();
        e.addComponent(player);
        
        world.getManager(GroupManager.class).add(e, Constants.Groups.CAT);

		return e;
	}
	
	public static Entity createPole(World world, float x, float y) {
		float zoom = 1.5f;
		
		Entity e = world.createEntity();
		
		Position position = new Position();
		position.setX(x);
		position.setY(y);
		e.addComponent(position);
		
		Size size = new Size();
		size.setWidth((int)(81 * zoom * Constants.ZOOM));
		size.setHeight((int)(125 * zoom * Constants.ZOOM));
		e.addComponent(size);
		
		Velocity velocity = new Velocity();
		velocity.setX(-150 * Constants.ZOOM);
		e.addComponent(velocity);
		
		Sprite sprite = new Sprite();
        sprite.name = "pole";
        sprite.scaleX = sprite.scaleY = Constants.ZOOM * zoom;
        sprite.layer = Sprite.Layer.ACTORS_3;
        e.addComponent(sprite);
        
        world.getManager(GroupManager.class).add(e, Constants.Groups.POLE);
        
		return e;
	}
	
	public static Entity createEffect(World world, float x, float y) {
		Entity e = world.createEntity();
		
		Position position = new Position();
		position.setX(x);
		position.setY(y);
		e.addComponent(position);
		
		Velocity velocity = new Velocity();
		velocity.setX(-150 * Constants.ZOOM);
		e.addComponent(velocity);
		
		Effect effect = new Effect("pixel2");
		e.addComponent(effect);
		System.out.println("AJAJAJAJ");
        
		return e;
	}
}
