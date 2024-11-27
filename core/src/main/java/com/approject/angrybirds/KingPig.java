package com.approject.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class KingPig extends Pigs {

    // Constructor with a higher health value for KingPig
    public KingPig(SpriteBatch batch, Vector2 position, World world) {
        super(new Texture("piggy.png"), batch, position, new Vector2(0, 0), 150, 11);  // Set health to 150
        initializeBody(world);
    }
}
