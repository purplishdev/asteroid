package com.asteroid.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.asteroid.game.component.DeathComponent;
import com.asteroid.game.component.MovementComponent;
import com.asteroid.game.component.RemoveComponent;
import com.asteroid.game.component.TextureComponent;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import lombok.var;

public class TextureRenderSystem extends IteratingSystem {

    private ComponentMapper<MovementComponent> movementMapper;

    private ComponentMapper<TextureComponent> textureMapper;

    @Wire
    private SpriteBatch batch;

    public TextureRenderSystem() {
        super(Aspect.all(TextureComponent.class, MovementComponent.class)
                      .exclude(RemoveComponent.class, DeathComponent.class));
    }

    @Override
    public void processSystem() {
        batch.begin();
        super.processSystem();
        batch.end();
    }

    @Override
    protected void process(int entity) {

        var movement = movementMapper.get(entity);
        var texture = textureMapper.get(entity);

        var width = texture.region.getRegionWidth();
        var height = texture.region.getRegionWidth();

        var xOrigin = width * 0.5f;
        var yOrigin = height * 0.5f;

        batch.draw(texture.region,
                   movement.position.x - xOrigin, movement.position.y - yOrigin,
                   xOrigin, yOrigin,
                   width, height,
                   1.0f, 1.0f,
                   MathUtils.radiansToDegrees * movement.rotation);
    }
}