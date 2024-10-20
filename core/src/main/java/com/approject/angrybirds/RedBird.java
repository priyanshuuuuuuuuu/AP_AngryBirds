package com.approject.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class RedBird extends Bird {

    public RedBird(SpriteBatch batch, Vector2 position) {
        super(new Texture("redBird.png"), batch, position, new Vector2(0, 0));  // Default velocity set to 0 for now
    }

    @Override
    public void specialAbility() {
        // RedBird doesn't have any special ability
        System.out.println("Red Bird has no special ability.");
    }
}
