package com.asteroid.component;

import com.artemis.Component;
import com.artemis.annotations.PooledWeaver;
import com.badlogic.gdx.math.Polygon;

@PooledWeaver
public class CollisionComponent extends Component {

    public Polygon polygon;
}