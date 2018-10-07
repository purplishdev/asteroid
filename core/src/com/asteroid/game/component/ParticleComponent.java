package com.asteroid.component;

import com.artemis.Component;
import com.artemis.annotations.PooledWeaver;
import com.asteroid.factory.Particle;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

@PooledWeaver
public class ParticleComponent extends Component {

    public Particle type;

    public ParticleEffect effect;

    public float xOffset;

    public float yOffset;

    public boolean once;
}