package com.asteroid.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.asteroid.component.CollidedComponent;
import com.asteroid.component.DeathComponent;
import com.asteroid.component.PlayerComponent;
import com.asteroid.component.RemoveComponent;

import lombok.var;

public class PlayerCollisionResolveSystem extends IteratingSystem {

    private ComponentMapper<CollidedComponent> collidedMapper;

    private ComponentMapper<PlayerComponent> playerMapper;

    private ComponentMapper<DeathComponent> deathMapper;

    public PlayerCollisionResolveSystem() {
        super(Aspect.all(PlayerComponent.class, CollidedComponent.class)
                      .exclude(RemoveComponent.class, DeathComponent.class));
    }

    @Override
    protected void process(int entity) {
        resolvePlayerCollision(entity);
    }

    private void resolvePlayerCollision(int player) {
        var collided = collidedMapper.get(player);

        // player vs player collision
        if (isPlayer(collided.entity)) {
            kill(collided.entity);
            kill(player);
        }

        collidedMapper.remove(player);
    }

    private void kill(int entity) {
        deathMapper.create(entity);
    }

    private boolean isPlayer(int entity) {
        return playerMapper.has(entity);
    }
}