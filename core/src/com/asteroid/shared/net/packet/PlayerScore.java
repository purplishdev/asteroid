package com.asteroid.net.packet;

import lombok.Value;

@Value
public class PlayerScore {

    private int playerKills;

    private int playerDeaths;

    private int playerScore;
}
