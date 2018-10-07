package com.asteroid.game.system;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.PooledComponent;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.reflect.ClassReflection;
import com.asteroid.game.Constants;
import com.asteroid.game.component.DebugComponent;
import com.asteroid.game.component.PlayerComponent;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import lombok.var;

public class DebugSystem extends EntityProcessingSystem {

    private static final int X = 20;

    private static final int Y = Constants.World.HEIGHT - 20;

    @Wire
    private SpriteBatch batch;

    @Wire(name = "font_32")
    private BitmapFont font;

    private Runtime runtime;

    private float initialMemoryUsage = 0.0f;

    public DebugSystem() {
        super(Aspect.one(PlayerComponent.class));
        runtime = Runtime.getRuntime();
    }

    @Override
    protected void initialize() {
        initialMemoryUsage = (runtime.totalMemory() - runtime.freeMemory()) / 1024.0f / 1024.0f;
        Gdx.app.log("[Debug System]", "Pooling ............... " + isPooledWeavingEnabled());
        Gdx.app.log("[Debug System]", "Hotspot optimization .. " + isHotspotOptimizationEnabled());
        Gdx.app.log("[Debug System]", "Initial memory usage .. " + (int)initialMemoryUsage + " MB");
    }

    @Override
    protected void process(Entity e) {
        var memoryUsage = (runtime.totalMemory() - runtime.freeMemory()) / 1024.0f / 1024.0f;
        var memoryAvailable = runtime.totalMemory() / 1024.0f / 1024.0f;
        var memoryUsagePercentage = memoryUsage / memoryAvailable * 100.0f;

        var framesPerSecond = Gdx.graphics.getFramesPerSecond();
        var rawDeltaTime = Gdx.graphics.getRawDeltaTime();

        String debugText = String.format("%d fps, frame time: %.3f ms, memory: %.2fMB / %.2fMB - %.2f%%, delta: %.2fMB, " +
                                                 "max draw calls: %d, max sprites: %d",
                                         framesPerSecond, rawDeltaTime,
                                         memoryUsage, memoryAvailable, memoryUsagePercentage, (memoryUsage - initialMemoryUsage),
                                         batch.renderCalls, batch.maxSpritesInBatch);

        batch.begin();
        font.draw(batch, debugText, X, Y);
        batch.end();
    }

    private boolean isHotspotOptimizationEnabled() {
        // hotspot optimization replaces (amongst other steps) references to entityprocessingsystem with entitysystem.
        // so we can determine this optimization by EntityProcessingSystem missing from our system's hierarchy.
        return !ClassReflection.isAssignableFrom(EntityProcessingSystem.class, DebugSystem.class);
    }

    private boolean isPooledWeavingEnabled() {
        // pooled components will subclass PooledComponent.
        return ClassReflection.isAssignableFrom(PooledComponent.class, DebugComponent.class);
    }
}