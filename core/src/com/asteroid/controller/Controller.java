package com.asteroid.controller;

import com.asteroid.Constants;
import com.asteroid.asset.AssetModule;
import com.asteroid.asset.GdxAssetModule;
import com.asteroid.net.packet.PlayerJoinGame;
import com.asteroid.net.packet.PlayerMove;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.esotericsoftware.kryonet.Client;

import java.io.IOException;

public class Controller implements ApplicationListener {

    private AssetModule assetModule;

    private SpriteBatch batch;

    private Camera camera;

    private Stage stage;

    private Table table;

    private ExtendViewport viewport;

    private Skin skin;

    private Client client;

    private PlayerMove playerMove = new PlayerMove();

    @Override
    public void create() {

        // create kryo client
        client = new Client();
        client.getKryo().register(PlayerJoinGame.class);
        client.getKryo().register(PlayerMove.class);
        client.start();
        try {
            client.connect(5000, "127.0.0.1", 1337);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // initialize
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(1280, 720, camera);
        stage = new Stage(viewport, batch);
        stage.setDebugAll(true);
        Gdx.input.setInputProcessor(stage);

        // load assets
        assetModule = new GdxAssetModule();
        assetModule.prepare(Constants.Assets.SKIN, Skin.class);
        assetModule.loadAll();

        // get skin
        skin = assetModule.get(Constants.Assets.SKIN);

        // create main stage table
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // create controls
        TextField textField = new TextField("", skin);

        TextButton button = new TextButton("Click me!", skin);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Button clicked!");
                client.sendTCP(new PlayerJoinGame(textField.getText().trim()));
            }
        });

        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                switch (keycode) {
                    case Input.Keys.W:
                        playerMove.deltaVelocity = Constants.Player.ACCELERATION_SPEED;
                        System.out.println("W");
                        return true;

                    case Input.Keys.S:
                        playerMove.deltaVelocity = Constants.Player.DECELERATION_SPEED;
                        System.out.println("S");
                        return true;

                    case Input.Keys.A:
                        playerMove.deltaRotationSpeed = Constants.Player.ROTATION_SPEED;
                        System.out.println("A");
                        return true;

                    case Input.Keys.D:
                        playerMove.deltaRotationSpeed = -Constants.Player.ROTATION_SPEED;
                        System.out.println("D");
                        return true;
                }

                return super.keyDown(event, keycode);
            }
        });

        // add controls to main table
        table.add(textField).center();
        table.add(button).center();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.25f, 0.25f, 0.25f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float delta = Gdx.graphics.getDeltaTime();
        stage.act(delta);

        client.sendTCP(playerMove);

        stage.draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}