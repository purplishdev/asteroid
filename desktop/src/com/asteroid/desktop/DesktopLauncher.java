package com.asteroid.desktop;

import com.asteroid.game.Asteroid;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 720;
		config.initialBackgroundColor = Color.BLACK;
		config.resizable = true;
		config.vSyncEnabled = true;
		config.foregroundFPS = 0;
		config.backgroundFPS = 0;
		config.samples = 16;
		new LwjglApplication(new Asteroid(), config);
	}
}