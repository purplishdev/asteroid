package com.asteroid.desktop;

import com.asteroid.controller.Controller;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;

public class DesktopControllerLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1280;
        config.height = 720;
        config.initialBackgroundColor = Color.BLACK;
        config.resizable = true;
        config.vSyncEnabled = true;
        new LwjglApplication(new Controller(), config);
    }
}