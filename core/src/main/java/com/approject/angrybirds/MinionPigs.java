package com.approject.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class MinionPigs extends Pigs {

    // Constructor with a specific health value for MinionPigs
    public MinionPigs(SpriteBatch batch, Vector2 position, World world) {
        super(new Texture("minionPig.png"), batch, position, new Vector2(0, 0), 50, 10);  // Set health to 50
        initializeBody(world);
    }

    // Additional logic specific to the MinionPigs class can go here if needed
}
