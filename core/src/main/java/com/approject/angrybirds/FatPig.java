package com.approject.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class FatPig extends Pigs {

    // Constructor with a higher health value for KingPig
    public FatPig(SpriteBatch batch, Vector2 position, World world) {
        super(new Texture("fat.png"), batch, position, new Vector2(0, 0), 180, 12);  // Set health to 150
        initializeBody(world);
    }

    // Additional logic specific to the KingPig class can go here if needed
}
