package com.asteroid.component;

import com.artemis.Component;
import com.artemis.annotations.PooledWeaver;

@PooledWeaver
public class SpawnComponent extends Component {

    public float x;

    public float y;

    public float velocity;

    public float rotation;

    public float rotationSpeed;
}