package com.asteroid.net;

public interface NetworkModule {
    int DEFAULT_TCP_PORT = 60000;
    int DEFAULT_UDP_PORT = 60001;

    GameServer createServer();
}