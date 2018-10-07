package com.asteroid.shared.net;

import com.asteroid.shared.net.entity.EntityPacketListener;
import com.asteroid.shared.net.packet.Packet;

public interface GameServer {

    void register(Class... packets);

    void startServer();

    <T extends Packet> void sendPacketTo(int connectionId, T packet);

    <T extends Packet> void sendPacketToAll(T packet);

    <T extends Packet> void addPacketListener(Class<T> clazz, EntityPacketListener<T> listener);
}