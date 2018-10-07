package com.asteroid.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.asteroid.component.DeathComponent;
import com.asteroid.component.MovementComponent;
import com.asteroid.component.RemoveComponent;
import com.badlogic.gdx.math.MathUtils;

import lombok.var;

import static com.badlogic.gdx.math.MathUtils.clamp;

public class MovementSystem extends IteratingSystem {

    private ComponentMapper<MovementComponent> movementMapper;

    public MovementSystem() {
        super(Aspect.all(MovementComponent.class)
                      .exclude(RemoveComponent.class, DeathComponent.class));
    }

    @Override
    protected void process(int entity) {
        var movement = movementMapper.get(entity);

        limit(movement);
        move(movement);
    }

    private void limit(MovementComponent movement) {
        movement.velocity = clamp(movement.velocity, movement.minVelocity, movement.maxVelocity);
        movement.rotationSpeed = clamp(movement.rotationSpeed, movement.minRotationSpeed, movement.maxRotationSpeed);
    }

    private void move(MovementComponent movement) {
        movement.rotation    += movement.rotationSpeed * world.delta;
        movement.position.x  += MathUtils.cos(movement.rotation) * (movement.velocity * world.delta);
        movement.position.y  += MathUtils.sin(movement.rotation) * (movement.velocity * world.delta);
    }
}