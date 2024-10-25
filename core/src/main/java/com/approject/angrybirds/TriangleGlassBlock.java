package com.approject.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class TriangleGlassBlock extends GlassBlock{

    public TriangleGlassBlock(SpriteBatch batch, Vector2 position) {
        super(new Texture("glassTriangleBlock.png"), batch, position, new Vector2(0, 0));
    }

    @Override
    public void health() {
        System.out.println("No health for now");
    }
}
