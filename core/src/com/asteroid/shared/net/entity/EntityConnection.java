package com.asteroid.shared.net.entity;

import com.asteroid.shared.net.packet.Packet;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import lombok.Getter;
import lombok.Setter;

public class EntityConnection extends Connection {

    @Getter @Setter
    private int entityId = -1;

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