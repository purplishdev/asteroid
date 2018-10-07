package com.asteroid.shared.net.packet;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PlayerHealth implements Packet {

    public int health;
}
