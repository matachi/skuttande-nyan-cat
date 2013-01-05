package se.danielj.skuttandenyancat.systems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import se.danielj.skuttandenyancat.components.Position;
import se.danielj.skuttandenyancat.components.Sprite;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpriteRenderSystem extends EntitySystem {
	@Mapper
	ComponentMapper<Position> pm;
	@Mapper
	ComponentMapper<Sprite> sm;

	private TextureAtlas textureAtlas;
	private SpriteBatch batch;
	private OrthographicCamera camera;

	private Map<String, Renderer> renderers;

	private List<Entity> sortedEntities;
	private Comparator<Entity> sortedEntityComparator;

	@SuppressWarnings("unchecked")
	public SpriteRenderSystem(OrthographicCamera camera, SpriteBatch batch) {
		super(Aspect.getAspectForAll(Position.class, Sprite.class));
		this.camera = camera;
		this.sortedEntities = new ArrayList<Entity>();
		this.sortedEntityComparator = new Comparator<Entity>() {
			@Override
			public int compare(Entity e1, Entity e2) {
				Sprite s1 = sm.get(e1);
				Sprite s2 = sm.get(e2);
				if (s1.layer.getLayerId() > s2.layer.getLayerId()) {
					return 1;
				} else {
					return -1;
				}
			}
		};
		this.batch = batch;
	}

	@Override
	protected void initialize() {
		textureAtlas = new TextureAtlas(
				Gdx.files.internal("sprites/sprites.atlas"),
				Gdx.files.internal("sprites"));
		renderers = new HashMap<String, Renderer>();

		JSONParser parser = new JSONParser();
		JSONArray array = null;
		try {
			array = (JSONArray) parser.parse(Gdx.files.internal(
					"sprites/sprites.json").readString());
		} catch (ParseException e1) {
			e1.printStackTrace();
			System.exit(0);
		}

		// Walk through the sprites
		Iterator<?> spriteIterator = array.iterator();
		while (spriteIterator.hasNext()) {
			JSONObject s = (JSONObject) spriteIterator.next();

			String guid = (String) s.get("guid");
			Boolean animated = (Boolean) toBoolean(s.get("animated"));
			if (animated) {
				Integer tiles = toInt(s.get("tiles"));
				Float delay = toFloat(s.get("delay"));
				renderers.put(guid, createAnimationRenderer(textureAtlas.findRegion(guid), tiles, delay, false));
			} else {
				renderers.put(guid, new SpriteRenderer(textureAtlas.findRegion(guid)));
			}
		}

		sortedEntities = new ArrayList<Entity>();
	}

	private Renderer createAnimationRenderer(AtlasRegion atlasRegion,
			int width, float delay, boolean loopBack) {
		TextureRegion[][] tmp = atlasRegion.split(atlasRegion.packedWidth
				/ width, atlasRegion.packedHeight);
		if (loopBack) {
			width += width - 2;
		}
		TextureRegion[] walkFrames = new TextureRegion[width];
		int add = 1;
		int frame = 0;
		for (int i = 0; i < width; ++i) {
			walkFrames[i] = tmp[0][frame];
			if (i == tmp[0].length - 1) {
				add = -add;
			}
			frame += add;
		}
		Animation a = new Animation(delay, walkFrames);
		return new AnimationRenderer(a);
	}

	private static Integer toInt(Object obj) {
		if (obj != null) {
			return Integer.valueOf(((Long) obj).toString());
		} else {
			return null;
		}
	}

	private static Float toFloat(Object obj) {
		if (obj != null) {
			return Float.valueOf(((Double) obj).toString());
		} else {
			return null;
		}
	}

	private static Boolean toBoolean(Object obj) {
		if (obj != null) {
			return Boolean.valueOf((String) obj);
		} else {
			return null;
		}
	}

	@Override
	protected void begin() {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		Collections.sort(sortedEntities, sortedEntityComparator);
		for (int i = 0; sortedEntities.size() > i; i++) {
			process(sortedEntities.get(i));
		}
	}

	public interface Renderer {
		public void render(Entity e);
	}

	public class SpriteRenderer implements Renderer {
		AtlasRegion spriteRegion;

		public SpriteRenderer(AtlasRegion spriteRegion) {
			this.spriteRegion = spriteRegion;
		}

		public void render(Entity e) {
			Position position = pm.getSafe(e);
			Sprite sprite = sm.get(e);

			batch.setColor(sprite.r, sprite.g, sprite.b, sprite.a);

			float posX = position.getX()
					- (spriteRegion.getRegionWidth() / 2 * sprite.scaleX) - sprite.offset;
			float posY = position.getY()
					- (spriteRegion.getRegionHeight() / 2 * sprite.scaleX);
			batch.draw(spriteRegion, posX, posY, 0, 0,
					spriteRegion.getRegionWidth(),
					spriteRegion.getRegionHeight(), sprite.scaleX,
					sprite.scaleY, sprite.rotation);
			// GdxUtils.drawCentered(batch, spriteRegion, position.x,
			// position.y);
		}
	}

	public class AnimationRenderer implements Renderer {
		private float time = 0;
		private Animation spriteRegion;

		public AnimationRenderer(Animation spriteRegion) {
			this.spriteRegion = spriteRegion;
		}

		public void render(Entity e) {
	    
			Position position = pm.getSafe(e);
			Sprite sprite = sm.get(e);

			batch.setColor(sprite.r, sprite.g, sprite.b, sprite.a);

			time += world.getDelta();

			TextureRegion currentFrame = spriteRegion.getKeyFrame(time, true);

			float posX = position.getX()
					- (currentFrame.getRegionWidth() / 2 * sprite.scaleX) - sprite.offset;
			float posY = position.getY()
					- (currentFrame.getRegionHeight() / 2 * sprite.scaleX);
			batch.draw(currentFrame, posX, posY, 0, 0,
					currentFrame.getRegionWidth(),
					currentFrame.getRegionHeight(), sprite.scaleX,
					sprite.scaleY, sprite.rotation);
		}
	}

	protected void process(Entity e) {
		Sprite sprite = sm.get(e);
		renderers.get(sprite.name).render(e);
	}

	protected void end() {
		batch.end();
	}

	@Override
	protected void inserted(Entity e) {
		sortedEntities.add(e);
	}

	@Override
	protected void removed(Entity e) {
		sortedEntities.remove(e);
	}

}
