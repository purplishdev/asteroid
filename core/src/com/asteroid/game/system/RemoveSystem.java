package com.asteroid.system;

import com.artemis.Aspect;
import com.asteroid.component.RemoveComponent;

public class RemoveSystem extends IteratingSystem {

    public RemoveSystem() {
        super(Aspect.one(RemoveComponent.class));
    }

    @Override
    protected void process(int entity) {
        world.delete(entity);
    }
}