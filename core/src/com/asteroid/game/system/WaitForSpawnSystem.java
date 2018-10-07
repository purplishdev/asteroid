package com.asteroid.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.asteroid.game.Constants;
import com.asteroid.game.component.DeathComponent;
import com.asteroid.game.component.RemoveComponent;
import com.asteroid.game.component.SpawnComponent;
import com.asteroid.game.component.WaitForSpawnComponent;
import com.badlogic.gdx.math.MathUtils;

import lombok.var;

public class WaitForSpawnSystem extends IteratingSystem {

    private ComponentMapper<DeathComponent> deathMapper;

    private ComponentMapper<SpawnComponent> spawnMapper;

    private ComponentMapper<WaitForSpawnComponent> waitMapper;

    public WaitForSpawnSystem() {
        super(Aspect.one(WaitForSpawnComponent.class)
                      .exclude(RemoveComponent.class));
    }

    @Override
    protected void process(int entity) {
        var wait = waitMapper.get(entity);
        wait.timeElapsed += world.delta;
        if (wait.timeElapsed > wait.time) {
            spawn(entity);
        }
    }

    private void spawn(int entity) {
        // TODO: calculate the least populated position
        var spawn = spawnMapper.create(entity);
        spawn.x = MathUtils.random(Constants.World.WIDTH);
        spawn.y = MathUtils.random(Constants.World.HEIGHT);
        spawn.velocity = 0.0f;
        spawn.rotationSpeed = 0.0f;
        spawn.rotation = MathUtils.random(0, MathUtils.PI2);

        deathMapper.remove(entity);
        waitMapper.remove(entity);
    }
}