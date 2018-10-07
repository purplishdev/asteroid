package com.asteroid.shared.net;

import com.asteroid.shared.net.packet.Packet;

public interface GameClient {

    void register(Class... packets);

    void connect(int timeoutSeconds);

    <T extends Packet> void sendPacket(T packet);

    <T extends Packet> void addPacketListener(Class<T> clazz, PacketListener<T> listener);
}