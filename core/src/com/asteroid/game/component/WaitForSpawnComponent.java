package com.asteroid.component;

import com.artemis.Component;
import com.artemis.annotations.PooledWeaver;

@PooledWeaver
public class WaitForSpawnComponent extends Component {

    public float time;

    public float timeElapsed;
}