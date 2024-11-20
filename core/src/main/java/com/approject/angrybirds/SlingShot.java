package com.approject.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class SlingShot {
    protected SpriteBatch batch;
    protected Texture slingShot;
    protected float xCoordinate;
    protected float yCoordinate;
    public static final float VIRTUAL_WIDTH = 100f;
    public static final float VIRTUAL_HEIGHT = 150f;

    //
    private final Vector2 position = new Vector2();
    private Bird currentBird;
    private Trajectory trajectory;

    public void setBird(Bird bird) {
        this.currentBird = bird;
        this.currentBird.setPosition(position.x, position.y);
    }

    private boolean isDragging(){
        return false;
    }

    private Vector2 calculateVelocity(){
        return new Vector2(10, 10);
    }

    public void render() {
        batch.draw(slingShot, xCoordinate, yCoordinate, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        if (currentBird != null) {
            currentBird.render();
            if (isDragging()) {
                Vector2 velocity = calculateVelocity();
                trajectory.render(position, velocity);
            }
        }
    }
    //

    public SlingShot(SpriteBatch batch, float xCoordinate, float yCoordinate) {
        this.batch = batch;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public void show() {
        slingShot = new Texture("slingshot.png");
    }

//    public void render() {
//        batch.draw(slingShot, xCoordinate, yCoordinate, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
//    }

    public void dispose() {
        slingShot.dispose();
    }
}

