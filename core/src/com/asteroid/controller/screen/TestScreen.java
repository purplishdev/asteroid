package com.asteroid.controller.screen;

import com.asteroid.controller.ControllerContext;
import com.asteroid.game.Constants;
import com.asteroid.shared.net.GameClient;
import com.asteroid.shared.net.packet.PlayerJoinGame;
import com.asteroid.shared.screen.AbstractGameScreen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import lombok.var;

public class TestScreen extends AbstractGameScreen {

    private final GameClient client;

    private final Stage stage;

    private Skin skin;

    private Table table;

    private TextField loginControl;

    private Button loginButton;

    public TestScreen(ControllerContext context) {
        client = context.getNetworkModule().getClient();
        skin = context.getAssetModule().get(Constants.Assets.SKIN);
        stage = new Stage(context.getViewport(), context.getSpriteBatch());
        addInputProcessor(stage);
    }

    @Override
    public void create() {
        createTable();
        createControls();
    }

    private void createTable() {
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
    }

    private void createControls() {
        loginControl = new TextField("", skin);
        loginControl.setMessageText("Enter your nickname");
        table.add(loginControl).width(300).center().row();

        loginButton = new TextButton("Try connect", skin);
        loginButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                var nickname = loginControl.getText();
                onGameConnect(nickname);
            }
        });
        table.add(loginButton).space(15).center();
    }

    private void onGameConnect(String nickname) {
        try {
            client.connect(30);
            client.sendPacket(new PlayerJoinGame(nickname));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(float delta) {
        stage.act(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        stage.draw();
    }
}