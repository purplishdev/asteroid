package com.asteroid.shared.net;

import com.asteroid.shared.net.entity.EntityConnection;
import com.asteroid.shared.net.entity.EntityPacketListener;
import com.asteroid.shared.net.exception.ConnectionException;
import com.asteroid.shared.net.packet.Packet;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

import lombok.NonNull;

class GameServerImpl extends Server implements GameServer {

    private final int tcpPort, udpPort;

    GameServerImpl(int tcpPort, int udpPort) {
        this.tcpPort = tcpPort;
        this.udpPort = udpPort;
    }

    @Override
    protected Connection newConnection() {
        return new EntityConnection();
    }

    @Override
    public void register(Class... packets) {
        Kryo kryo = super.getKryo();
        for (Class packet : packets) {
            kryo.register(packet);
        }
    }

    @Override
    public void startServer() {
        try {
            super.start();
            super.bind(tcpPort, udpPort);
        } catch (IOException e) {
            throw new ConnectionException(
                    String.format("Unable to start game server at ports tcp: %d and udp: %d", tcpPort, udpPort), e);
        }
    }

    @Override
    public <T extends Packet> void sendPacketTo(int connectionId, @NonNull T packet) {
        switch (packet.getProtocol()) {
            case TCP:
                super.sendToTCP(connectionId, packet);
                break;

            case UDP:
                super.sendToUDP(connectionId, packet);
                break;
        }
    }

    @Override
    public <T extends Packet> void sendPacketToAll(@NonNull T packet) {
        Packet.Protocol protocol = packet.getProtocol();
        switch (protocol) {
            case TCP:
                super.sendToAllTCP(packet);
                break;

            case UDP:
                super.sendToAllUDP(packet);
                break;
        }
    }

    @Override
    public <T extends Packet> void addPacketListener(Class<T> packetClass, EntityPacketListener<T> listener) {
        super.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object packet) {
                if (packetClass.isInstance(packet) && listener != null) {
                    listener.received((EntityConnection)connection, (T)packet);
                }
            }
        });
    }
}