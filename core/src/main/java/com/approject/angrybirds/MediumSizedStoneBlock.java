package com.approject.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class MediumSizedStoneBlock extends StoneBlocks{

    public MediumSizedStoneBlock(SpriteBatch batch, Vector2 position, World world) {
        super(new Texture("stoneSmallBlock.png"), batch, position, world);
    }

    @Override
    public void health() {
        System.out.println("No health for now");
    }
}
