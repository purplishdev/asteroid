package com.asteroid.game.system;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.artemis.utils.IntBag;

public abstract class IteratingSystem extends BaseEntitySystem {

    public IteratingSystem(Aspect.Builder aspect) {
        super(aspect);
    }

    protected abstract void process(int entityId);

    @Override
    protected void processSystem() {
        IntBag actives = subscription.getEntities();
        int[] ids = actives.getData();
        for (int i = 0, s = actives.size(); s > i; i++) {
            process(ids[i]);
        }
    }
}