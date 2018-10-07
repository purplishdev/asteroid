package com.asteroid.game.component;

import com.artemis.Component;
import com.artemis.annotations.PooledWeaver;

@PooledWeaver
public class BulletComponent extends Component {

    public float lifespan;

    public float maxLifespan;

    public int damage;
}