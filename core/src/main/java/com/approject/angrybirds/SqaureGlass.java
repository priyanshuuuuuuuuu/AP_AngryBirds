package com.approject.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class SqaureGlass extends GlassBlock{

    public SqaureGlass(SpriteBatch batch, Vector2 position, World world) {
        super(new Texture("glass.png"), batch, position, world, 10);
    }


}