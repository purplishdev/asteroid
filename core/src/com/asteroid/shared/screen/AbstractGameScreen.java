package com.asteroid.shared.screen.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class AbstractGameScreen implements GameScreen {

    protected final InputMultiplexer input;

    public AbstractGameScreen() {
        input = new InputMultiplexer();
    }

    @Override
    public void create() { }

    @Override
    public void show() { }

    @Override
    public void update(float delta) { }

    @Override
    public void render(SpriteBatch batch) { }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() { }

    @Override
    public void resume() {
        Gdx.input.setInputProcessor(input);
    }

    @Override
    public void hide() { }

    @Override
    public void dispose() { }
}