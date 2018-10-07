package com.asteroid.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.asteroid.component.BulletComponent;
import com.asteroid.component.CollidedComponent;
import com.asteroid.component.DamageComponent;
import com.asteroid.component.DeathComponent;
import com.asteroid.component.PlayerComponent;
import com.asteroid.component.RemoveComponent;

import lombok.var;

public class BulletCollisionResolveSystem extends IteratingSystem {

    private ComponentMapper<CollidedComponent> collidedMapper;

    private ComponentMapper<BulletComponent> bulletMapper;

    private ComponentMapper<PlayerComponent> playerMapper;

    private ComponentMapper<RemoveComponent> removeMapper;

    private ComponentMapper<DamageComponent> damageMapper;

    public BulletCollisionResolveSystem() {
        super(Aspect.all(BulletComponent.class, CollidedComponent.class)
                      .exclude(RemoveComponent.class, DeathComponent.class));
    }

    @Override
    protected void process(int entity) {
        resolveBulletCollision(entity);
    }

    private void resolveBulletCollision(int bulletEntity) {
        var bullet = bulletMapper.get(bulletEntity);
        var collided = collidedMapper.get(bulletEntity);

        // bullet vs player collision
        if (isPlayer(collided.entity)) {
            damage(collided.entity, bullet.damage);
        }
        // bullet vs bullet collision
        else if (isBullet(collided.entity)) {
            remove(collided.entity);
        }

        remove(bulletEntity);
    }

    private void damage(int entity, int value) {
        var damage = damageMapper.create(entity);
        damage.value = value;
    }

    private void remove(int entity) {
        removeMapper.create(entity);
    }

    private boolean isPlayer(int entity) {
        return playerMapper.has(entity);
    }

    private boolean isBullet(int entity) {
        return bulletMapper.has(entity);
    }
}