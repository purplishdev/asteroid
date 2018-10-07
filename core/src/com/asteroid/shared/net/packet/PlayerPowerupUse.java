package com.asteroid.shared.net.packet;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PlayerPowerupUse implements Packet {

    // TODO: change int to enum?
    public int powerupType;
}