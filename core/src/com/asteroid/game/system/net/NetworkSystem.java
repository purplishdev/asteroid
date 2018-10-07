package com.asteroid.game.system.net;

import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.asteroid.game.component.NetworkComponent;
import com.asteroid.game.factory.PlayerFactory;
import com.asteroid.shared.net.NetworkModuleImpl;
import com.asteroid.shared.net.entity.EntityConnection;
import com.asteroid.shared.net.packet.PlayerJoinGame;

import lombok.var;

public class NetworkSystem extends BaseSystem {

    private ComponentMapper<NetworkComponent> networkMapper;

    @Wire
    private NetworkModuleImpl networkModule;

    @Wire
    private PlayerFactory playerFactory;

    @Override
    protected void processSystem() { }

    @Override
    protected void initialize() {

        var server = networkModule.getServer();
        server.startServer();

        server.addPacketListener(PlayerJoinGame.class, this::createPlayer);
    }

    private void createPlayer(EntityConnection entityConnection, PlayerJoinGame packet) {
        int player = playerFactory.create(packet.name, 300.0f, 300.0f, 0.0f);

        var network = networkMapper.create(player);
        network.connection = entityConnection;
        network.connection.setEntityId(player);
    }
}