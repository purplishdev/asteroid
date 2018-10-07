package com.asteroid.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.asteroid.game.component.DeathComponent;
import com.asteroid.game.component.MovementComponent;
import com.asteroid.game.component.RemoveComponent;
import com.asteroid.game.component.TrailComponent;
import com.asteroid.game.factory.ParticleEffectFactory;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import lombok.var;

public class TrailSystem extends IteratingSystem {

    private static final Vector2 vector = new Vector2();

    private ComponentMapper<MovementComponent> movementMapper;

    private ComponentMapper<TrailComponent> trailMapper;

    @Wire
    private ParticleEffectFactory effectFactory;

    @Wire
    private SpriteBatch batch;

    public TrailSystem() {
        super(Aspect.all(TrailComponent.class, MovementComponent.class)
                      .exclude(DeathComponent.class, RemoveComponent.class));
    }

    @Override
    protected void processSystem() {
        batch.begin();
        super.processSystem();
        batch.end();
    }

    @Override
    protected void process(int entity) {
        var trail = trailMapper.get(entity);
        var movement = movementMapper.get(entity);

        var position = vector.set(trail.x, trail.y)
                .rotateRad(movement.rotation)
                .add(movement.position);

        trail.effect.setPosition(position.x, position.y);
        trail.effect.draw(batch, world.delta);
    }

    @Override
    protected void inserted(int entity) {
        var trail = trailMapper.create(entity);
        trail.effect = effectFactory.create(trail.type);
    }
}