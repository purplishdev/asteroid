package com.asteroid.net;

public interface GameClient {

    void registerPackets(Class[] packets);

    void connect(String address, int tcpPort, int udpPort, int timeout);
}
