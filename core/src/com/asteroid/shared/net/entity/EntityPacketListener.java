package com.asteroid.shared.net.entity;

public interface EntityPacketListener<T> {

    void received(EntityConnection connection, T packet);
}