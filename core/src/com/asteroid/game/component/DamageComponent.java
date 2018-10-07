package com.asteroid.component;

import com.artemis.Component;
import com.artemis.annotations.PooledWeaver;

@PooledWeaver
public class DamageComponent extends Component {

    public int value;
}