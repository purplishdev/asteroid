package com.asteroid.shared.net.packet;

public interface Packet {

    enum Protocol {
        TCP,
        UDP
    }

    default Protocol getProtocol() {
        return Protocol.TCP;
    }
}