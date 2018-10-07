package com.asteroid.game;

import com.artemis.World;
import com.artemis.WorldConfigurationBuilder;
import com.asteroid.shared.asset.AssetModule;
import com.asteroid.shared.asset.GdxAssetModule;
import com.asteroid.shared.asset.loader.PolygonLoader;
import com.asteroid.game.factory.Particle;
import com.asteroid.game.factory.ParticleEffectFactory;
import com.asteroid.game.factory.PlayerFactory;
import com.asteroid.shared.net.NetworkModule;
import com.asteroid.shared.net.NetworkModuleImpl;
import com.asteroid.shared.screen.ScreenModule;
import com.asteroid.shared.screen.ScreenModuleImpl;
import com.asteroid.game.screen.TestScreen;
import com.asteroid.game.system.BulletCollisionResolveSystem;
import com.asteroid.game.system.BulletSystem;
import com.asteroid.game.system.CollisionSystem;
import com.asteroid.game.system.DamageSystem;
import com.asteroid.game.system.DebugSystem;
import com.asteroid.game.system.ExplosionSystem;
import com.asteroid.game.system.HealthSystem;
import com.asteroid.game.system.InputSystem;
import com.asteroid.game.system.LabelRenderSystem;
import com.asteroid.game.system.MovementSystem;
import com.asteroid.game.system.net.NetworkInputSystem;
import com.asteroid.game.system.net.NetworkSystem;
import com.asteroid.game.system.ParticleRenderSystem;
import com.asteroid.game.system.PlayerCollisionResolveSystem;
import com.asteroid.game.system.PlayerDeathSystem;
import com.asteroid.game.system.RemoveSystem;
import com.asteroid.game.system.SpawnSystem;
import com.asteroid.game.system.TextureRenderSystem;
import com.asteroid.game.system.TrailSystem;
import com.asteroid.game.system.WaitForSpawnSystem;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import lombok.var;

public class Asteroid implements GameContext, ApplicationListener {

    private SpriteBatch spriteBatch;

	private OrthographicCamera camera;

	private ExtendViewport viewport;

	private World world;

	private ScreenModule screenModule;

	private AssetModule assetModule;

	private NetworkModule networkModule;

	@Override
	public void create() {
		createBatch();
		createAssets();
		createCameraAndViewport();
		createNetwork();
		createEngine();
        createScreens();
	}

	private void createNetwork() {
		networkModule = new NetworkModuleImpl();
	}

	private void createAssets() {
		assetModule = new GdxAssetModule();
		assetModule.setLoader(Polygon.class, new PolygonLoader(new InternalFileHandleResolver()));
		assetModule.prepare(Constants.Assets.SKIN, Skin.class);
		assetModule.prepare(Constants.Assets.PLAYER_POLYGON, Polygon.class);
		assetModule.prepare(Constants.Assets.LASER_POLYGON, Polygon.class);
		assetModule.prepare(Constants.Assets.EXPLOSION_PARTICLE, ParticleEffect.class, Constants.Assets.PARTICLE_EFFECT_PARAMS);
		assetModule.prepare(Constants.Assets.TRAIL_PARTICLE, ParticleEffect.class, Constants.Assets.PARTICLE_EFFECT_PARAMS);
		assetModule.loadAll();
	}

	private void createCameraAndViewport() {
		camera = new OrthographicCamera();
		viewport = new ExtendViewport(Constants.World.WIDTH, Constants.World.HEIGHT, camera);
	}

	private void createEngine() {
		var font32 = assetModule.getFontFromSkin(Constants.Assets.SKIN, Constants.Assets.FONT_32);

		var particleFactory = new ParticleEffectFactory();
		particleFactory.register(Particle.PLAYER_EXPLOSION, assetModule.get(Constants.Assets.EXPLOSION_PARTICLE));
		particleFactory.register(Particle.PLAYER_TRAIL, assetModule.get(Constants.Assets.TRAIL_PARTICLE));

		TextureRegion playerTexture = assetModule.getTextureFromSkin(Constants.Assets.SKIN, Constants.Assets.PLAYER_TEXTURE);
		Polygon playerPolygon = assetModule.get(Constants.Assets.PLAYER_POLYGON);
		PlayerFactory playerFactory = new PlayerFactory(playerTexture, playerPolygon);

		var config = new WorldConfigurationBuilder()
				.with(new InputSystem(),
					  new NetworkSystem(),
					  new NetworkInputSystem(),
					  new BulletSystem(),
					  new MovementSystem(),
					  new CollisionSystem(),
					  new BulletCollisionResolveSystem(),
					  new PlayerCollisionResolveSystem(),
					  new DamageSystem(),
					  new HealthSystem(),
					  new ExplosionSystem(),
					  new TrailSystem(),
					  new PlayerDeathSystem(),
					  new WaitForSpawnSystem(),
					  new SpawnSystem(),
					  new RemoveSystem(),
					  new ParticleRenderSystem(),
					  new LabelRenderSystem(),
					  new TextureRenderSystem(),
					  new DebugSystem()
				).build()
				.register(spriteBatch)
				.register(particleFactory)
				.register(playerFactory)
				.register(networkModule)
				.register("font_32", font32);

		world = new World(config);
		playerFactory.setWorld(world);
	}

	private void createBatch() {
		spriteBatch = new SpriteBatch();
	}

	private void createScreens() {
		screenModule = new ScreenModuleImpl();
		screenModule.setScreen(new TestScreen(this));
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
		screenModule.resize(width, height);
	}

	@Override
	public void render() {
		float delta = Gdx.graphics.getDeltaTime();
		screenModule.update(delta);

		Gdx.gl.glClearColor(0.25f, 0.25f, 0.25f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		spriteBatch.setProjectionMatrix(camera.combined);

		world.setDelta(delta);
		world.process();
		screenModule.render(spriteBatch);
	}

	@Override
	public void pause() {
		screenModule.pause();
	}

	@Override
	public void resume() {
		screenModule.resume();
	}

	@Override
	public void dispose() {
		screenModule.dispose();
		assetModule.dispose();
	}

	@Override
	public AssetModule getAssetModule() {
		return assetModule;
	}

	@Override
    public World getWorld() {
        return world;
    }
}