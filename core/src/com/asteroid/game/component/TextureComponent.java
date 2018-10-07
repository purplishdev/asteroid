package com.asteroid.component;

import com.artemis.Component;
import com.artemis.annotations.PooledWeaver;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

@PooledWeaver
public class TextureComponent extends Component {

    public TextureRegion region = null;
}