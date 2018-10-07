package com.asteroid.system.net;

import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.asteroid.component.ConnectionComponent;
import com.asteroid.component.RemoveComponent;
import com.asteroid.factory.PlayerFactory;
import com.asteroid.net.packet.PlayerJoinGame;
import com.asteroid.net.packet.PlayerMove;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

import lombok.var;

public class NetworkSystem extends BaseSystem {

    private ComponentMapper<ConnectionComponent> connectionMapper;

    private Server server;

    @Wire
    private PlayerFactory playerFactory;

    @Override
    protected void processSystem() { }

    @Override
    protected void initialize() {

        server = new Server();
        server.getKryo().register(PlayerJoinGame.class);
        server.getKryo().register(PlayerMove.class);

        server.start();
        try {
            server.bind(1337);
        } catch (IOException e) {
            e.printStackTrace();
        }

        server.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object packet) {
                if (packet instanceof PlayerJoinGame) {
                    createPlayer(connection, (PlayerJoinGame)packet);
                }
            }
        });
    }

    private void createPlayer(Connection connection, PlayerJoinGame p) {
        int player = playerFactory.create(p.name, 300.0f, 300.0f, 0.0f);

        var conn = connectionMapper.create(player);
        conn.connection = connection;
    }
}