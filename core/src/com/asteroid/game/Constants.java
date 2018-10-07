package com.asteroid.game;

import com.badlogic.gdx.assets.loaders.ParticleEffectLoader;

public final class Constants {

    public static class Assets {
        public static final String ATLAS = "asteroid.atlas";
        public static final String PLAYER_TEXTURE = "player";
        public static final String PLAYER_POLYGON = "player.psh";
        public static final String LASER_TEXTURE = "laser";
        public static final String LASER_POLYGON = "laser.psh";
        public static final String FONT_32 = "font_32";
        public static final String SKIN = "asteroid.json";

        public static final String TRAIL_PARTICLE = "effects/exhaust.p";
        public static final String EXPLOSION_PARTICLE = "effects/explosion.p";

        public static ParticleEffectLoader.ParticleEffectParameter PARTICLE_EFFECT_PARAMS;

        static {
            PARTICLE_EFFECT_PARAMS = new ParticleEffectLoader.ParticleEffectParameter();
            PARTICLE_EFFECT_PARAMS.atlasFile = ATLAS;
        }
    }

    public static class World {
        public static final int WIDTH = 1920*2;
        public static final int HEIGHT = 1080*2;
    }

    public static class Player {
        public static final float ROTATION_SPEED = 0.045f;
        public static final float ACCELERATION_SPEED = 2.5f;
        public static final float DECELERATION_SPEED = -3.5f;

        public static final float MAX_ROTATION_SPEED = 1.8f;
        public static final float MAX_VELOCITY = 400.0f;
    }

    public static class Bullet {
        public static final float LIFESPAN = 3.0f;
        public static final float VELOCITY = 900.0f;
    }
}