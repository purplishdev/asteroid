package com.asteroid.shared.net.packet;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PlayerScore implements Packet {

    public int playerKills;

    public int playerDeaths;

    public int playerScore;
}