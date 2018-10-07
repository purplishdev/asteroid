package com.asteroid.net.packet;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PlayerMove {

    public float deltaVelocity;

    public float deltaRotationSpeed;
}