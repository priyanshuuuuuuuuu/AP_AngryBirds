package com.approject.angrybirds;
import com.badlogic.gdx.math.Vector2;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.io.Serializable;

public class GameState implements Serializable {
//    public static final GameState MAIN_MENU = new GameState(0, 1, new Vector2(0, 0));
    //    public static final GameState MAIN_MENU = new GameState(0, 1, new Vector2(0, 0));
    private int score;
    private int level;
    private Vector2 birdPosition;
    public ArrayList<BirdData> birds;
    public ArrayList<PigData> pigs;
    public ArrayList<BlockData> blocks;

//    public GameState(int Score, int Level, Vector2 birdPosition) {
//        this.score = Score;
//        this.level = Level;
//        this.birdPosition = birdPosition;
//    }


    public int getScore() {
        return score;
    }

    public int getLevel() {
        return level;
    }

    public Vector2 getBirdPosition() {
        return birdPosition;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setLevel(int i) {
        this.level = i;
    }

    public void setBirdPosition(Vector2 position) {
        this.birdPosition = position;
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
