package com.approject.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class Pigs {
    protected Texture texture;
    protected SpriteBatch batch;
    protected Vector2 position;
    protected Vector2 velocity;
    public static final float PIG_WIDTH = 90f;
    public static final float PIG_HEIGHT =90f;

    public Pigs(Texture texture, SpriteBatch batch, Vector2 position, Vector2 velocity) {
        this.texture = texture;
        this.batch = batch;
        this.position = position;
        this.velocity = velocity;
    }

    public void render(){
        batch.draw(texture, position.x, position.y, PIG_WIDTH, PIG_HEIGHT);
    }

    public void dispose(){
        texture.dispose();
    }
}
