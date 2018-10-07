package com.asteroid.net;

import com.asteroid.net.exception.ConnectionException;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;

import java.io.IOException;

public class GameClientImpl implements GameClient {

    private final Client client;

    public GameClientImpl() {
        client = new Client();
    }

    public void registerPackets(Class[] packets) {
        Kryo kryo = client.getKryo();
        for (Class packet : packets) {
            kryo.register(packet);
        }
    }

    public void connect(String address, int tcpPort, int udpPort, int timeout) {
        try {
            client.connect(timeout * 1000, address, tcpPort, udpPort);
            client.start();
        } catch (IOException e) {
            throw new ConnectionException("Unable to connect with ip " + address, e);
        }
    }
}