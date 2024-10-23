package com.approject.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SlingShot {
    protected SpriteBatch batch;
    protected Texture slingShot;
    protected float xCoordinate;
    protected float yCoordinate;
    public static final float VIRTUAL_WIDTH = 100f;
    public static final float VIRTUAL_HEIGHT = 150f;

    public SlingShot(SpriteBatch batch, float xCoordinate, float yCoordinate) {
        this.batch = batch;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    // Load the texture for SlingShot
    public void show() {
        slingShot = new Texture("slingshot.png");
    }

    // Render the SlingShot
    public void render() {
        batch.draw(slingShot, xCoordinate, yCoordinate, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
    }

    // Dispose the texture (don't dispose the batch here)
    public void dispose() {
        slingShot.dispose();
    }
}
