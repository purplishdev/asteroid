package com.asteroid.shared.net.packet;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PlayerTeamScore implements Packet {

    public int scoreTeamFirst;

    public int scoreTeamSecond;
}
