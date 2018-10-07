package com.asteroid.shared.asset;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import lombok.var;

public class GdxAssetModule implements AssetModule {

    private AssetManager assetManager;

    public GdxAssetModule() {
        assetManager = new AssetManager();
    }

    @Override
    public <T, P extends AssetLoaderParameters<T>> void setLoader(Class<T> type, AssetLoader<T, P> loader) {
        assetManager.setLoader(type, loader);
    }

    @Override
    public <T> void prepare(String name, Class<T> clazz) {
        assetManager.load(name, clazz);
    }

    @Override
    public <T> void prepare(String name, Class<T> clazz, AssetLoaderParameters<T> parameter) {
        assetManager.load(name, clazz, parameter);
    }

    @Override
    public <T> T get(String name) {
        return assetManager.get(name);
    }

    @Override
    public BitmapFont getFontFromSkin(String skinName, String fontName) {
        var skin = assetManager.get(skinName, Skin.class);
        return skin.getFont(fontName);
    }

    @Override
    public TextureRegion getTextureFromSkin(String skinName, String textureName) {
        var skin = assetManager.get(skinName, Skin.class);
        return skin.getRegion(textureName);
    }

    @Override
    public TextureAtlas getSkinAtlas(String skinName) {
        var skin = assetManager.get(skinName, Skin.class);
        return skin.getAtlas();
    }

    @Override
    public void loadAll() {
        assetManager.finishLoading();
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}