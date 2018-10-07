package com.asteroid.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.asteroid.game.component.DeathComponent;
import com.asteroid.game.component.LabelComponent;
import com.asteroid.game.component.MovementComponent;
import com.asteroid.game.component.RemoveComponent;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import lombok.var;

public class LabelRenderSystem extends IteratingSystem {

    private final GlyphLayout glyphLayout;

    private ComponentMapper<MovementComponent> movementMapper;

    private ComponentMapper<LabelComponent> labelMapper;

    @Wire
    private SpriteBatch batch;

    @Wire(name = "font_32")
    private BitmapFont font;

    public LabelRenderSystem() {
        super(Aspect.all(MovementComponent.class, LabelComponent.class)
                      .exclude(RemoveComponent.class, DeathComponent.class));

        glyphLayout = new GlyphLayout();
    }

    @Override
    protected void processSystem() {
        batch.begin();
        super.processSystem();
        batch.end();
    }

    protected void process(int entity) {
        var movement = movementMapper.get(entity);
        var label = labelMapper.get(entity);

        glyphLayout.setText(font, label.text);

        var x = label.xOffset + movement.position.x - glyphLayout.width * 0.5f;
        var y = label.yOffset + movement.position.y;

        font.draw(batch, glyphLayout, x, y);
    }
}