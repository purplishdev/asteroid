package com.asteroid;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Asteroid extends ApplicationAdapter {

	private SpriteBatch batch;
	private Texture asteroidImage;

	@Override
	public void create () {
		batch = new SpriteBatch();
		asteroidImage = new Texture("asteroid.jpg");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(asteroidImage, 0, 0);
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		asteroidImage.dispose();
	}
}
