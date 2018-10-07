package com.asteroid.game.component;

import com.artemis.Component;
import com.artemis.annotations.PooledWeaver;

@PooledWeaver
public class PlayerComponent extends Component {

    public int deathCount;
}