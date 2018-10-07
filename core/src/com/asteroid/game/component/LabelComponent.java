package com.asteroid.game.component;

import com.artemis.Component;
import com.artemis.annotations.PooledWeaver;

@PooledWeaver
public class LabelComponent extends Component {

    public float xOffset;

    public float yOffset;

    public String text;
}