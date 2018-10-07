package com.asteroid.shared.net.packet;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PlayerPowerupPickup implements Packet {

    // TODO: change int to enum?
    private int powerupType;
}