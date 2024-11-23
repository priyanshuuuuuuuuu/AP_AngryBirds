package com.approject.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class VerticalWoodBlock extends WoodBlocks{

    public VerticalWoodBlock(SpriteBatch batch, Vector2 position, World world) {
        super(new Texture("woodVerticalBlock.png"),batch, position, world,100);
    }
    @Override
    public void health() {
        System.out.println("Vertical Wood Block health: " + getBody().getUserData());
    }


}
