package com.asteroid.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.asteroid.game.component.ExplosionComponent;
import com.asteroid.game.component.MovementComponent;
import com.asteroid.game.component.RemoveComponent;
import com.asteroid.game.factory.ParticleEffectFactory;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import lombok.var;

import static com.badlogic.gdx.math.MathUtils.clamp;
import static com.badlogic.gdx.math.MathUtils.radDeg;

public class ExplosionSystem extends IteratingSystem {

    private ComponentMapper<MovementComponent> movementMapper;

    private ComponentMapper<ExplosionComponent> explosionMapper;

    @Wire
    private ParticleEffectFactory effectFactory;

    @Wire
    private SpriteBatch batch;

    public ExplosionSystem() {
        super(Aspect.all(ExplosionComponent.class, MovementComponent.class)
                      .exclude(RemoveComponent.class));
    }

    @Override
    protected void processSystem() {
        batch.begin();
        super.processSystem();
        batch.end();
    }

    @Override
    protected void process(int entity) {
        var explosion = explosionMapper.get(entity);
        var movement = movementMapper.get(entity);

        explosion.effect.setPosition(movement.position.x, movement.position.y);
        explosion.effect.draw(batch, world.delta);

        if (explosion.effect.isComplete()) {
            explosionMapper.remove(entity);
        }
    }

    @Override
    protected void inserted(int entity) {
        var movement = movementMapper.get(entity);
        var explosion = explosionMapper.create(entity);
        explosion.effect = effectFactory.create(explosion.type);
        applyDispersion(explosion.effect, movement);
        explosion.effect.start();
    }

    private void applyDispersion(ParticleEffect effect, MovementComponent movement) {
        if (movement.velocity > 100.0f) {
            var rotation = movement.rotation * radDeg;
            var velocity = clamp(movement.velocity*2, 50.0f, 400.0f);
            effect.getEmitters().get(0).getVelocity().setHigh(velocity, velocity);
            effect.getEmitters().get(0).getAngle().setHigh(rotation - 45.0f, rotation + 45.0f);
        }
    }
}