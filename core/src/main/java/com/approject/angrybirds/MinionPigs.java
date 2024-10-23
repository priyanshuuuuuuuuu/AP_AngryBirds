package com.approject.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class MinionPigs extends Pigs{
    public MinionPigs(SpriteBatch batch, Vector2 position) {
        super(new Texture("minionPig.png"), batch, position, new Vector2(0, 0));
    }
}
