package com.asteroid.game.component;

import com.artemis.Component;
import com.artemis.annotations.PooledWeaver;
import com.badlogic.gdx.math.Vector2;

@PooledWeaver
public class MovementComponent extends Component {

    public Vector2 position = new Vector2();

    public float velocity;

    public float minVelocity;

    public float maxVelocity;

    public float minDeltaVelocity;

    public float maxDeltaVelocity;

    public float rotation;

    public float rotationSpeed;

    public float minRotationSpeed;

    public float maxRotationSpeed;

    public float minDeltaRotationSpeed;

    public float maxDeltaRotationSpeed;
}