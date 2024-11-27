package com.approject.angrybirds;
import com.badlogic.gdx.math.Vector2;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.io.Serializable;

import java.io.Serializable;
import java.util.List;

public class GameState implements Serializable {
    private static final long serialVersionUID = -4007385269365714679L;
    public List<BirdData> birds;
    public List<PigData> pigs;
    public List<BlockData> blocks;
    private int score;
    private int level;
    private Vector2 birdPosition;

    // Getters and setters for score, level, and birdPosition
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Vector2 getBirdPosition() {
        return birdPosition;
    }

    public void setBirdPosition(Vector2 birdPosition) {
        this.birdPosition = birdPosition;
    }
}



class BirdData implements Serializable{
    public float x, y;
    public float velocityX, velocityY;
    public int health;
}
class PigData implements Serializable{
    public float x, y;
    public int health;
}
class BlockData implements Serializable{
    public float x, y;
    public int health;
    public String type;
}
