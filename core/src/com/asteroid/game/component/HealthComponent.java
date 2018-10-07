package com.asteroid.game.component;

import com.artemis.Component;
import com.artemis.annotations.PooledWeaver;

@PooledWeaver
public class HealthComponent extends Component {

    public int value;

    public int maxValue;
}