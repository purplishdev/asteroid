package com.asteroid.game;

import com.artemis.World;
import com.asteroid.shared.asset.AssetModule;

public interface GameContext {

    AssetModule getAssetModule();

    World getWorld();
}
