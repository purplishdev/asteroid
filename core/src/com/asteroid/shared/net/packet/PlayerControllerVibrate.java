package com.asteroid.net.packet;

import lombok.Value;

@Value
public class PlayerControllerVibrate {

    private int vibrationTime;

    private int vibrationStrength;
}
