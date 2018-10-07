package com.asteroid.shared.net.packet;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PlayerControllerVibrate implements Packet {

    public int vibrationTime;

    public int vibrationStrength;
}
