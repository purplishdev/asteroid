package com.asteroid.shared.net.packet;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PlayerMove implements Packet {

    public float deltaVelocity;

    public float deltaRotationSpeed;
}