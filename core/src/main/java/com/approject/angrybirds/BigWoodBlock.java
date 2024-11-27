package com.approject.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import org.w3c.dom.Text;

public class BigWoodBlock extends WoodBlocks{
    public BigWoodBlock(Texture texture, SpriteBatch batch, Vector2 position, World world) {
        super(new Texture("woodBigBlock.png"),batch, position, world,100);
    }



}
