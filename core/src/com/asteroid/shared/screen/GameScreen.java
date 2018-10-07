package com.asteroid.shared.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface GameScreen {

    void create();

    void show();

    void update(float delta);

    void render(SpriteBatch batch);

    void resize(int width, int height);

    void pause();

    void resume();

    void hide();

    void dispose();
}