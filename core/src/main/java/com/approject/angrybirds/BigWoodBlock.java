package com.approject.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import org.w3c.dom.Text;

public class BigWoodBlock extends WoodBlocks{
    public BigWoodBlock(Texture texture, SpriteBatch batch, Vector2 position) {
        super(new Texture("woodBigBlock.png"),batch, position, new Vector2(0, 0));
    }

    @Override
    public void health() {
        System.out.println("No health for now");
    }
}
