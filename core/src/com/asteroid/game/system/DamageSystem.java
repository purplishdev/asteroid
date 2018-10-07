package com.asteroid.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.asteroid.game.component.DamageComponent;
import com.asteroid.game.component.DeathComponent;
import com.asteroid.game.component.HealthComponent;
import com.asteroid.game.component.RemoveComponent;

import lombok.var;

public class DamageSystem extends IteratingSystem {

    private ComponentMapper<DamageComponent> damageMapper;

    private ComponentMapper<HealthComponent> healthMapper;

    private ComponentMapper<DeathComponent> deathMapper;

    public DamageSystem() {
        super(Aspect.all(DamageComponent.class, HealthComponent.class)
                      .exclude(RemoveComponent.class, DeathComponent.class));
    }

    @Override
    protected void process(int entity) {
        dealDamage(entity);
    }

    private void dealDamage(int entity) {
        var health = healthMapper.get(entity);
        var damage = damageMapper.get(entity);

        health.value -= damage.value;

        damageMapper.remove(entity);
    }

}