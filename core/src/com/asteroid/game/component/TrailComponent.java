package com.asteroid.component;

import com.artemis.Component;
import com.artemis.annotations.PooledWeaver;
import com.asteroid.factory.Particle;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

@PooledWeaver
public class TrailComponent extends Component {

    public float x;

    public float y;

    public ParticleEffect effect;

    public Particle type;
}