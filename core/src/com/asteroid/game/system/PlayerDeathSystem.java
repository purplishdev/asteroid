package com.asteroid.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.asteroid.game.component.DeathComponent;
import com.asteroid.game.component.ExplosionComponent;
import com.asteroid.game.component.HealthComponent;
import com.asteroid.game.component.PlayerComponent;
import com.asteroid.game.component.WaitForSpawnComponent;
import com.asteroid.game.factory.Particle;

import lombok.var;

public class PlayerDeathSystem extends IteratingSystem {

    private ComponentMapper<PlayerComponent> playerMapper;

    private ComponentMapper<HealthComponent> healthMapper;

    private ComponentMapper<DeathComponent> deathMapper;

    private ComponentMapper<WaitForSpawnComponent> waitMapper;

    private ComponentMapper<ExplosionComponent> explosionMapper;

    public PlayerDeathSystem() {
        super(Aspect.all(PlayerComponent.class, DeathComponent.class, HealthComponent.class)
                      .exclude(WaitForSpawnComponent.class));
    }

    @Override
    protected void process(int entity) {
        explode(entity);
        resetHealth(entity);
        increaseDeathCount(entity);
        wait(entity);
    }

    private void explode(int entity) {
        var explosion = explosionMapper.create(entity);
        explosion.type = Particle.PLAYER_EXPLOSION;
    }

    private void wait(int entity) {
        var player = playerMapper.get(entity);
        var wait = waitMapper.create(entity);
        wait.time = resolveDeathTime(player.deathCount);
    }

    private void increaseDeathCount(int entity) {
        var player = playerMapper.get(entity);
        player.deathCount += 1;
    }

    private void resetHealth(int entity) {
        var health = healthMapper.get(entity);
        health.value = health.maxValue;
    }

    private float resolveDeathTime(int deathCount) {
        if (deathCount > 10) {
            return 3.0f; // 20.0f;
        } else if (deathCount > 5) {
            return 2.0f; // 15.0f;
        } else {
            return 1.0f; // 10.0f;
        }
    }
}