package com.asteroid.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.asteroid.game.component.BulletComponent;
import com.asteroid.game.component.RemoveComponent;

import lombok.var;

public class BulletSystem extends IteratingSystem {

    private ComponentMapper<BulletComponent> bulletMapper;

    private ComponentMapper<RemoveComponent> removeMapper;

    public BulletSystem() {
        super(Aspect.all(BulletComponent.class).exclude(RemoveComponent.class));
    }

    @Override
    protected void process(int entityId) {
        var bullet = bulletMapper.get(entityId);

        bullet.lifespan += world.delta;

        if (bullet.lifespan > bullet.maxLifespan) {
            removeMapper.create(entityId);
        }
    }
}