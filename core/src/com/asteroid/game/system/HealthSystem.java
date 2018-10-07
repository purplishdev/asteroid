package com.asteroid.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.asteroid.component.DeathComponent;
import com.asteroid.component.HealthComponent;
import com.asteroid.component.RemoveComponent;

import lombok.var;

public class HealthSystem extends IteratingSystem {

    private ComponentMapper<HealthComponent> healthMapper;

    private ComponentMapper<DeathComponent> deathMapper;

    public HealthSystem() {
        super(Aspect.all(HealthComponent.class)
                      .exclude(RemoveComponent.class, DeathComponent.class));
    }

    @Override
    protected void process(int entity) {
        var health = healthMapper.get(entity);
        if (health.value < 1) {
            kill(entity);
        }
    }

    private void kill(int entity) {
        deathMapper.create(entity);
    }
}