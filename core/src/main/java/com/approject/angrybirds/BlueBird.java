package com.approject.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
public class BlueBird extends Bird {

    public BlueBird(SpriteBatch batch, Vector2 position, World world) {
        // Set the health of RedBird to 1 (or any other number you want)
        super(new Texture("bluebird.png"), batch, position, new Vector2(0, 0), 40, 4);
        initializeBody(world);
    }

    @Override
    public void specialAbility() {
        // RedBird doesn't have any special ability
        System.out.println("Red Bird has no special ability.");
    }
}
