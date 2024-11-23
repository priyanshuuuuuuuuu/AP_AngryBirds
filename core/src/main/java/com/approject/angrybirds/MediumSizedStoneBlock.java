package com.approject.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class MediumSizedStoneBlock extends StoneBlocks{

    public MediumSizedStoneBlock(SpriteBatch batch, Vector2 position, World world) {
        super(new Texture("stoneSmallBlock.png"), batch, position, world, 500);
    }
    //priyanshu Pandey   Rohit

    @Override
    public void health() {
        System.out.println("Current health: " + health);
    }

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);
        if (getHealth() <= 0) {
            ((Level1Screen) Gdx.app.getApplicationListener()).addScore(500); // Adjust points as needed
            world.destroyBody(body);
        }
    }
}
