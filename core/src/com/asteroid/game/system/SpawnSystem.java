package com.asteroid.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.asteroid.game.component.MovementComponent;
import com.asteroid.game.component.SpawnComponent;

import lombok.var;

public class SpawnSystem extends IteratingSystem {

    private ComponentMapper<MovementComponent> movementMapper;

    private ComponentMapper<SpawnComponent> spawnMapper;

    public SpawnSystem() {
        super(Aspect.all(SpawnComponent.class, MovementComponent.class));
    }

    @Override
    protected void process(int entity) {
        var spawn = spawnMapper.get(entity);
        var movement = movementMapper.create(entity);
        movement.position.x = spawn.x;
        movement.position.y = spawn.y;
        movement.velocity = spawn.velocity;
        movement.rotation = spawn.rotation;
        movement.rotationSpeed = spawn.rotationSpeed;

        spawnMapper.remove(entity);
    }
}