package com.asteroid.controller;

import com.asteroid.controller.screen.TestScreen;
import com.asteroid.game.Constants;
import com.asteroid.shared.asset.AssetModule;
import com.asteroid.shared.asset.GdxAssetModule;
import com.asteroid.shared.net.NetworkModule;
import com.asteroid.shared.net.NetworkModuleImpl;
import com.asteroid.shared.net.packet.PlayerJoinGame;
import com.asteroid.shared.net.packet.PlayerMove;
import com.asteroid.shared.screen.ScreenModule;
import com.asteroid.shared.screen.ScreenModuleImpl;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.esotericsoftware.kryonet.Client;

public class Controller implements ControllerContext, ApplicationListener {

    private NetworkModuleImpl networkModule;

    private ScreenModule screenModule;

    private AssetModule assetModule;

    private SpriteBatch batch;

    private ExtendViewport viewport;

    @Override
    public void create() {
        createBatch();
        createCameraAndViewport();
        createAssets();
        createNetwork();
        createScreen();
    }

    private void createAssets() {
        assetModule = new GdxAssetModule();
        assetModule.prepare(Constants.Assets.SKIN, Skin.class);
        assetModule.loadAll();
    }

    private void createBatch() {
        batch = new SpriteBatch();
    }

    private void createCameraAndViewport() {
        viewport = new ExtendViewport(1280, 720, new OrthographicCamera());
    }

    private void createNetwork() {
        networkModule = new NetworkModuleImpl();
    }

    private void createScreen() {
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
        Gdx.gl.glClearColor(0.25f, 0.25f, 0.25f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        screenModule.update(Gdx.graphics.getDeltaTime());
        screenModule.render(batch);
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
    }

    @Override
    public Viewport getViewport() {
        return viewport;
    }

    @Override
    public SpriteBatch getSpriteBatch() {
        return batch;
    }

    @Override
    public AssetModule getAssetModule() {
        return assetModule;
    }

    @Override
    public NetworkModule getNetworkModule() {
        return networkModule;
    }
}