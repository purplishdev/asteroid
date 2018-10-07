package com.asteroid.system;

import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.asteroid.component.MovementComponent;

import lombok.var;

import static com.badlogic.gdx.math.MathUtils.clamp;

public class InputSystem extends BaseSystem {

    private ComponentMapper<MovementComponent> movementMapper;

    @Override
    protected void processSystem() { }

    public void move(int entity, float amount) {
        var movement = movementMapper.get(entity);
        movement.velocity += clamp(amount, movement.minDeltaVelocity, movement.maxDeltaVelocity);
    }

    public void rotate(int entity, float amount) {
        var movement = movementMapper.get(entity);
        movement.rotationSpeed += clamp(amount, movement.minDeltaRotationSpeed, movement.maxDeltaRotationSpeed);
    }
}