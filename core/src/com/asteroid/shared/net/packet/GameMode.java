package com.asteroid.shared.net.packet;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@NoArgsConstructor
public class GameMode implements Packet {

    public int mode;
}