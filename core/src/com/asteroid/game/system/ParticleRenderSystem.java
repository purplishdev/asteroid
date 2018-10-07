package com.asteroid.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.asteroid.game.component.DeathComponent;
import com.asteroid.game.component.MovementComponent;
import com.asteroid.game.component.ParticleComponent;
import com.asteroid.game.component.RemoveComponent;
import com.asteroid.game.factory.ParticleEffectFactory;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import lombok.var;

public class ParticleRenderSystem extends IteratingSystem {

    private final Vector2 vector = new Vector2();

    private ComponentMapper<ParticleComponent> particleMapper;

    private ComponentMapper<MovementComponent> movementMapper;

    private ComponentMapper<RemoveComponent> removeMapper;

    @Wire
    private ParticleEffectFactory effectFactory;

    @Wire
    private SpriteBatch batch;

    public ParticleRenderSystem() {
        super(Aspect.all(ParticleComponent.class, MovementComponent.class)
                      .exclude(DeathComponent.class, RemoveComponent.class));
    }

    @Override
    public void processSystem() {
        batch.begin();
        super.processSystem();
        batch.end();
    }

    @Override
    protected void process(int entity) {
        var particle = particleMapper.get(entity);
        var movement = movementMapper.get(entity);
        var position = vector.set(particle.xOffset, particle.yOffset)
                .rotateRad(movement.rotation)
                .add(movement.position);

        particle.effect.setPosition(position.x, position.y);
        particle.effect.draw(batch, world.delta);

        if (particle.once && particle.effect.isComplete()) {
            removeMapper.remove(entity);
        }
    }

    @Override
    protected void inserted(int entity) {
        var particle = particleMapper.get(entity);
        particle.effect = effectFactory.create(particle.type);
        particle.effect.start();
    }
}