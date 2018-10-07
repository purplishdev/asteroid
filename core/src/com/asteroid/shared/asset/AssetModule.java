package com.asteroid.game.asset;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface AssetModule {

    <T, P extends AssetLoaderParameters<T>> void setLoader (Class<T> type, AssetLoader<T, P> loader);

    <T> void prepare(String name, Class<T> clazz);

    <T> void prepare(String name, Class<T> clazz, AssetLoaderParameters<T> parameter);

    <T> T get(String name);

    BitmapFont getFontFromSkin(String skinName, String fontName);

    TextureRegion getTextureFromSkin(String skinName, String textureName);

    TextureAtlas getSkinAtlas(String skinName);

    void loadAll();

    void dispose();
}