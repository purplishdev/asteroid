package com.asteroid.shared.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import lombok.NonNull;

public class ScreenModuleImpl implements ScreenModule {

    private GameScreen currentScreen = new BlankScreen();

    @Override
    public void create() {
        currentScreen.create();
    }

    @Override
    public void dispose() {
        currentScreen.dispose();
    }

    @Override
    public void pause() {
        currentScreen.pause();
    }

    @Override
    public void resume() {
        currentScreen.resume();
    }

    @Override
    public void update(float delta) {
        currentScreen.update(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        currentScreen.render(batch);
    }

    @Override
    public void resize(int width, int height) {
        currentScreen.resize(width, height);
    }

    @Override
    public void setScreen(@NonNull GameScreen screen) {
        removeCurrentScreen();

        currentScreen = screen;
        currentScreen.create();
        currentScreen.show();
        currentScreen.resume();
        currentScreen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public GameScreen getScreen() {
        return currentScreen;
    }

    private void removeCurrentScreen() {
        if (currentScreen != null) {
            currentScreen.pause();
            currentScreen.hide();
            currentScreen.dispose();
        }
    }
}