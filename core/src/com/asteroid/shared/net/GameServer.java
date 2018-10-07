package com.asteroid.net;

public interface GameServer {

    void registerPackets(Class[] packets);

    void start(int tcpPort, int udpPort);
}
