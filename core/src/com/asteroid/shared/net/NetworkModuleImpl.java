package com.asteroid.shared.net;

import com.asteroid.shared.net.packet.GameMode;
import com.asteroid.shared.net.packet.PlayerControllerVibrate;
import com.asteroid.shared.net.packet.PlayerHealth;
import com.asteroid.shared.net.packet.PlayerJoinGame;
import com.asteroid.shared.net.packet.PlayerJoinGameNope;
import com.asteroid.shared.net.packet.PlayerJoinGameOk;
import com.asteroid.shared.net.packet.PlayerMove;
import com.asteroid.shared.net.packet.PlayerPowerupPickup;
import com.asteroid.shared.net.packet.PlayerPowerupUse;
import com.asteroid.shared.net.packet.PlayerReady;
import com.asteroid.shared.net.packet.PlayerScore;
import com.asteroid.shared.net.packet.PlayerShoot;
import com.asteroid.shared.net.packet.PlayerTeamScore;

public class NetworkModuleImpl implements NetworkModule {

    private static final int DEFAULT_TCP_PORT = 1337;

    private static final int DEFAULT_UDP_PORT = 1338;

    private static Class[] PACKETS = {
            GameMode.class,
            PlayerControllerVibrate.class,
            PlayerHealth.class,
            PlayerJoinGame.class,
            PlayerJoinGameOk.class,
            PlayerJoinGameNope.class,
            PlayerPowerupPickup.class,
            PlayerPowerupUse.class,
            PlayerMove.class,
            PlayerReady.class,
            PlayerShoot.class,
            PlayerScore.class,
            PlayerTeamScore.class
    };

    private GameServer server;

    private GameClient client;

    @Override
    public GameServer getServer() {
        if (server == null) {
            server = new GameServerImpl(DEFAULT_TCP_PORT, DEFAULT_UDP_PORT);
            server.register(PACKETS);
        }
        return server;
    }

    @Override
    public GameClient getClient() {
        if (client == null) {
            client = new GameClientImpl(DEFAULT_TCP_PORT, DEFAULT_UDP_PORT);
            client.register(PACKETS);
        }
        return client;
    }
}