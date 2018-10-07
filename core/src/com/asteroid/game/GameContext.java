package com.asteroid;

import com.artemis.World;
import com.asteroid.asset.AssetModule;

public interface GameContext {

    AssetModule getAssetModule();

    World getWorld();
}
