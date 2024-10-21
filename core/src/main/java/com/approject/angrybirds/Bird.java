package com.approject.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class Bird {
    protected Texture texture;
    protected SpriteBatch batch;
    protected Vector2 position;
    protected Vector2 velocity;

    public Bird(Texture texture, SpriteBatch batch, Vector2 position, Vector2 velocity) {
        this.texture = texture;
        this.batch = batch;
        this.position = position;
        this.velocity = velocity;
    }
    public abstract void specialAbility();

    public void render(){
        int birdWidth = 100;
        int birdHeight = 100;
        int birdCoordinateX = 100;
        int birdCoordinateY = 100;
        batch.draw(texture,birdWidth, birdHeight, birdCoordinateX,birdCoordinateY);
    }
    public void dispose(){
        texture.dispose();
    }
}
