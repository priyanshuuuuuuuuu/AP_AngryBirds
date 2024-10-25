package com.approject.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class GlassBlock {
    protected SpriteBatch batch;
    protected Texture texture;
    protected Vector2 position;

    public GlassBlock(Texture texture, SpriteBatch batch, Vector2 position, Vector2 velocity) {
        this.texture = texture;
        this.batch = batch;
        this.position = position;
    }

    public abstract void health();

    public void render(){
        batch.draw(texture, position.x, position.y);
    }

    public void dispose(){
        texture.dispose();
    }
}
