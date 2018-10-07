package com.asteroid.shared.net;

public interface NetworkModule {

    GameServer getServer();

    GameClient getClient();
}