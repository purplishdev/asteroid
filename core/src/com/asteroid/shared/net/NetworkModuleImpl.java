package com.asteroid.net;

import com.asteroid.net.packet.GameMode;
import com.asteroid.net.packet.PlayerControllerVibrate;
import com.asteroid.net.packet.PlayerHealth;
import com.asteroid.net.packet.PlayerJoinGame;
import com.asteroid.net.packet.PlayerJoinGameNope;
import com.asteroid.net.packet.PlayerJoinGameOk;
import com.asteroid.net.packet.PlayerMove;
import com.asteroid.net.packet.PlayerPowerupPickup;
import com.asteroid.net.packet.PlayerPowerupUse;
import com.asteroid.net.packet.PlayerReady;
import com.asteroid.net.packet.PlayerScore;
import com.asteroid.net.packet.PlayerShoot;
import com.asteroid.net.packet.PlayerTeamScore;

public class NetworkModuleImpl implements NetworkModule {

    public static Class[] PACKETS = {
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

    @Override
    public GameServer createServer() {
        return new GameServerImpl(PACKETS);
    }
}