package com.approject.angrybirds;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

import java.util.Vector;

public class BirdInputHandler implements InputProcessor {
    private Bird bird;
    private Vector2 slingStartPosition;
    private Vector2 currentDragPosition;

    public BirdInputHandler(Bird bird, Vector2 slingStartPosition, Vector2 currentDragPosition) {
        this.bird = bird;
        this.slingStartPosition = slingStartPosition;
        this.currentDragPosition = currentDragPosition;
    }
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // Start dragging if touch is near the bird
        Vector2 worldCoords = convertToWorldCoordinates(screenX, screenY);
        if (bird.getBounds().contains(worldCoords.x, worldCoords.y)) {
            currentDragPosition.set(worldCoords);
        }
        return true;
    }
    private Vector2 convertToWorldCoordinates(int screenX, int screenY) {
        // Convert screen coordinates to game world coordinates
        return new Vector2(screenX, screenY); // Replace with your camera's unproject method
    }


    @Override
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }
}
