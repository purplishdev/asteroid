package com.asteroid.game.screen;

import com.artemis.World;
import com.asteroid.game.Constants;
import com.asteroid.game.GameContext;
import com.asteroid.shared.asset.AssetModule;
import com.asteroid.game.factory.BulletFactory;
import com.asteroid.game.factory.PlayerFactory;
import com.asteroid.game.system.InputSystem;
import com.asteroid.shared.screen.AbstractGameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;

import lombok.var;

public class TestScreen extends AbstractGameScreen {

    private final World world;

    private final InputSystem inputSystem;

    private final AssetModule assetModule;

    private int player;

    private BulletFactory bulletFactory;

    private PlayerFactory playerFactory;

    public TestScreen(GameContext game) {
        world = game.getWorld();
        assetModule = game.getAssetModule();
        inputSystem = world.getSystem(InputSystem.class);

        addInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                switch (keycode) {
                    case Input.Keys.F12:
                        goFullScreenMode();
                        return true;

                    case Input.Keys.SPACE:
                        fireBullet();
                        return true;

                    case Input.Keys.G:
                        createRandomPlayer();
                        return true;

                    default:
                        return super.keyDown(keycode);
                }
            }
        });
    }

    @Override
    public void create() {
        bulletFactory = createBulletFactory(world);
        playerFactory = createPlayerFactory();
        playerFactory.setWorld(world);
        player = playerFactory.create("Player", 150.0f, 150.0f, 15.0f);
    }

    @Override
    public void update(float delta) {
        handleInput();
    }

    private PlayerFactory createPlayerFactory() {
        TextureRegion playerTexture = assetModule.getTextureFromSkin(Constants.Assets.SKIN, Constants.Assets.PLAYER_TEXTURE);
        Polygon playerPolygon = assetModule.get(Constants.Assets.PLAYER_POLYGON);
        return new PlayerFactory(playerTexture, playerPolygon);
    }

    private BulletFactory createBulletFactory(World world) {
        TextureRegion bulletTexture = assetModule.getTextureFromSkin(Constants.Assets.SKIN, Constants.Assets.LASER_TEXTURE);
        Polygon bulletPolygon = assetModule.get(Constants.Assets.LASER_POLYGON);
        return new BulletFactory(world, bulletTexture, bulletPolygon);
    }

    private void fireBullet() {
        bulletFactory.createGroup(player,
                                  -40.0f, 45.0f, 15.0f,
                                  -5.0f, 0.0f, 0.0f,
                                  -40.0f, -45.0f, -15.0f);
    }

    private void goFullScreenMode() {
        var displayMode = Gdx.graphics.getDisplayMode(Gdx.graphics.getPrimaryMonitor());
        if (Gdx.graphics.setFullscreenMode(displayMode)) {
            Gdx.graphics.setFullscreenMode(displayMode);
        } else {
            System.out.println("Full screen mode failed!");
        }
    }

    private void createRandomPlayer() {
        playerFactory.create("Dummy",
                     MathUtils.random(0, Constants.World.WIDTH),
                     MathUtils.random(0, Constants.World.HEIGHT),
                     MathUtils.random(0, 180));
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            inputSystem.move(player, Constants.Player.ACCELERATION_SPEED);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            inputSystem.move(player, Constants.Player.DECELERATION_SPEED);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            inputSystem.rotate(player, Constants.Player.ROTATION_SPEED);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            inputSystem.rotate(player, -Constants.Player.ROTATION_SPEED);
        }
    }
}