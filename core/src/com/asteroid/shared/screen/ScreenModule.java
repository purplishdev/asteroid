package com.asteroid.shared.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface ScreenModule {

    void create();

    void dispose();

    void pause();

    void resume();

    void update(float time);

    void render(SpriteBatch batch);

    void resize(int width, int height);

    void setScreen(GameScreen screen);

    GameScreen getScreen();
}