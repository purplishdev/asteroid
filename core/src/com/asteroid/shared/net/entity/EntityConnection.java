package com.asteroid.shared.net.entity;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import lombok.Getter;
import lombok.Setter;

public class EntityAwareConnection extends Connection {

    @Getter @Setter
    private int entityId;

    public <T> void addPacketListener(Class<T> clazz, EntityAwarePacketListener<T> listener) {
        addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (clazz.isInstance(object)) {
                    listener.received((EntityAwareConnection)connection, (T)object);
                }
            }
        });
    }
}