package com.asteroid.shared.net;

import com.asteroid.shared.net.exception.ConnectionException;
import com.asteroid.shared.net.packet.Packet;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;

import lombok.NonNull;
import lombok.var;

class GameClientImpl extends Client implements GameClient {

    private final int tcpPort, udpPort;

    GameClientImpl(int tcpPort, int udpPort) {
        this.tcpPort = tcpPort;
        this.udpPort = udpPort;
    }

    @Override
    public void register(Class... packets) {
        Kryo kryo = super.getKryo();
        for (Class packet : packets) {
            kryo.register(packet);
        }
    }

    @Override
    public void connect(int timeoutSeconds) {
        var timeoutMillis = timeoutSeconds * 1000;
        var address = super.discoverHost(udpPort, timeoutMillis);
        if (address == null) {
            throw new ConnectionException("Unable to find any game server");
        }
        try {
            super.start();
            super.connect(timeoutMillis, address, tcpPort, udpPort);
        } catch (IOException e) {
            throw new ConnectionException("Unable to connect with game server at ip " + address, e);
        }
    }

    public <T extends Packet> void sendPacket(@NonNull T packet) {
        switch (packet.getProtocol()) {
            case TCP:
                super.sendTCP(packet);
                break;

            case UDP:
                super.sendUDP(packet);
                break;
        }
    }

    @Override
    public <T extends Packet> void addPacketListener(Class<T> clazz, PacketListener<T> listener) {
        super.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (clazz.isInstance(object) && listener != null) {
                    listener.received(connection, (T)object);
                }
            }
        });
    }
}