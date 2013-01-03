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
		size.setWidth(33 * 2);
		size.setHeight(31 * 2);
		e.addComponent(size);
		
		Gravity gravity = new Gravity();
		gravity.setForce(-1000);
		e.addComponent(gravity);
		
		Velocity velocity = new Velocity();
		velocity.setX(0);
		e.addComponent(velocity);
		
		Sprite sprite = new Sprite();
        sprite.name = "nyan-cat";
        sprite.scaleX = sprite.scaleY = 2;
        sprite.offset = 19 * 2;
        sprite.layer = Sprite.Layer.ACTORS_2;
        e.addComponent(sprite);
        
        Player player = new Player();
        e.addComponent(player);
        
        world.getManager(GroupManager.class).add(e, Constants.Groups.CAT);

		return e;
	}
	
	public static Entity createPole(World world, float x, float y) {
		Entity e = world.createEntity();
		
		Position position = new Position();
		position.setX(x);
		position.setY(y);
		e.addComponent(position);
		
		Size size = new Size();
		size.setWidth(81 * 3);
		size.setHeight(125 * 3);
		e.addComponent(size);
		
		Velocity velocity = new Velocity();
		velocity.setX(-300);
		e.addComponent(velocity);
		
		Sprite sprite = new Sprite();
        sprite.name = "pole";
        sprite.scaleX = sprite.scaleY = 3;
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
		velocity.setX(-300);
		e.addComponent(velocity);
		
		Effect effect = new Effect("pixel2");
		e.addComponent(effect);
		System.out.println("AJAJAJAJ");
        
		return e;
	}
}
