package com.approject.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class Bird {
    protected Texture texture;
    protected SpriteBatch batch;
    protected Vector2 position;
    protected Vector2 velocity;
    public static final float BIRD_WIDTH = 100f;
    public static final float BIRD_HEIGHT = 100f;

    public Bird(Texture texture, SpriteBatch batch, Vector2 position, Vector2 velocity) {
        this.texture = texture;
        this.batch = batch;
        this.position = position;
        this.velocity = velocity;
    }

    public abstract void specialAbility();

    public void render() {
        batch.draw(texture, position.x, position.y, BIRD_WIDTH, BIRD_HEIGHT);
    }

    public void dispose() {
        texture.dispose();
    }
}
