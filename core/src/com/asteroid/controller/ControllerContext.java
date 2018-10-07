package com.asteroid.controller;

import com.asteroid.shared.asset.AssetModule;
import com.asteroid.shared.net.NetworkModule;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

public interface ControllerContext {

    Viewport getViewport();

    SpriteBatch getSpriteBatch();

    AssetModule getAssetModule();

    NetworkModule getNetworkModule();
}
