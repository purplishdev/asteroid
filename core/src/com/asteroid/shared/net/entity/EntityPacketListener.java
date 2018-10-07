package com.asteroid.shared.net.entity;

public interface EntityAwarePacketListener<T> {

    void received(EntityAwareConnection connection, T packet);
}