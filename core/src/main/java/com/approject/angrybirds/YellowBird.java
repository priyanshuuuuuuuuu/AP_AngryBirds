package com.approject.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class YellowBird extends Bird{
    public YellowBird(SpriteBatch batch, Vector2 position, World world) {
        super(new Texture("yellowBird.png"), batch, position, new Vector2(0, 0), 60);
        initializeBody(world);
    }

    @Override
    public void specialAbility() {
        System.out.println("Yellow Bird don't have the special ability currently");
    }
}
