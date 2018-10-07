package com.asteroid.game.factory;

import com.asteroid.shared.AsteroidException;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;

import java.util.HashMap;

import lombok.NonNull;
import lombok.var;

public class ParticleEffectFactory {

    private final HashMap<Particle, ParticleEffectPool> pools;

    public ParticleEffectFactory() {
        this.pools = new HashMap<>();
    }

    public void register(@NonNull Particle particle, @NonNull ParticleEffect effect) {
        pools.computeIfAbsent(particle, p -> new ParticleEffectPool(effect, 4, 8));
    }

    public ParticleEffectPool.PooledEffect create(@NonNull Particle particle) {
        var pool = pools.get(particle);
        if (pool == null) {
            throw new AsteroidException("Particle type " + particle.name() + " not registered!");
        }
        return pool.obtain();
    }
}