package com.asteroid.shared.net;

import com.esotericsoftware.kryonet.Connection;

public interface PacketListener<T> {

    void received(Connection connection, T packet);
}