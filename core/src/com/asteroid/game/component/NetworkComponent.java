package com.asteroid.game.component;

import com.artemis.Component;
import com.artemis.annotations.PooledWeaver;
import com.asteroid.shared.net.entity.EntityConnection;

@PooledWeaver
public class NetworkComponent extends Component {

    public EntityConnection connection;
}