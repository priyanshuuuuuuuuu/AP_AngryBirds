package com.approject.angrybirds;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;

import java.io.*;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class AngryBirds extends Game {
    SpriteBatch batch;
    Stage stage;

    @Override
    public void create() {
        batch = new SpriteBatch();
        stage = new Stage();
//        GameState gameState = new GameState(0,1, new Vector2(0,0));
        setScreen(new LoadingScreen(this));

    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
    }
}
//    public static void saveLevel(Level1Screen gameScreen) {
//        // Using gamescreen save the data as i mentioned above in a file
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\AP_AngryBirds\\savedlevel.txt"))) {
//            writer.write("Bird:" + gameScreen.birdList.size() + "\n");
//            for (Bird bird : gameScreen.birdList) {
//                writer.write(bird.type + "," + bird.getBody().getPosition().x + "," + bird.getBody().getPosition().y);
//                writer.newLine();
//            }
////            writer.write("Block:" + 5 + "\n");
//
//            writer.write("Pig:" + gameScreen.pigList.size() + "\n");
//            for (Pigs pig : gameScreen.pigList) {
//                writer.write(pig.type + "," + pig.getBody().getPosition().x + "," + pig.getBody().getPosition().y + "," + pig.getHealth());
//                writer.newLine();
//            }
//            System.out.println("Level saved");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }}
//        public static boolean isnotsaved() {
//            // if the file is not found, then the game is not saved yet
//            if (!new File("savedlevel.txt").exists()) {
//                System.out.println("No saved level found");
//                return true;
//            }
//            else{
//                if (new File("savedlevel.txt").length() == 0){
//                    System.out.println("No saved level found");
//                    return true;
//                }
//                System.out.println("Saved level found");
//                return false;
//            }
//        }
//
//        public static Level1Screen loadsavedLevel() {
//            Level1Screen level1Screen = new Level1Screen(new AngryBirds());
//            // if the file is not found, then the game is not saved yet
//            if (AngryBirds.isnotsaved()){
//                return null;
//            }
//
//            // Load the saved level from the file and set the level object with the data
//            try (BufferedReader reader = new BufferedReader(new FileReader("D:\\AP_AngryBirds\\savedlevel.txt"))) {
//                String line = reader.readLine();
////                if (line != null) {
////                    level.setLevelNumber(Integer.parseInt(line));
////                }
////                else{
////                    // Should not happen
////                    System.out.println("No saved level found HAHAHA");
////                    return;
////                }
////                line = reader.readLine();
//                if (line != null) {
//                    String[] birdData = line.split(":");
//                    System.out.println(line);
//                    int birdCount = Integer.parseInt(birdData[1]);
//                    for (int i = 0; i < birdCount; i++) {
//                        String[] bird = reader.readLine().split(",");
//                        int type = Integer.parseInt(bird[0]);
//                        if (type == 1){
//                            level1Screen.birdList.add(new RedBird(level1Screen.batch, new Vector2(0, 0), level1Screen.world));
//                        }
//                        else if (type == 2){
//                            level1Screen.birdList.add(new YellowBird(level1Screen.batch, new Vector2(0, 0), level1Screen.world));
//                        }
////                        else if (type == 3){
////                            level1Screen.birdList.add(new Bombbird());
////                        }
//                        level1Screen.birdList.get(i).setPosition(Float.parseFloat(bird[1]), Float.parseFloat(bird[2]));
//                    }
//                }
////                line = reader.readLine();
////                if (line != null) {
////                    String[] blockData = line.split(":");
////                    int blockCount = Integer.parseInt(blockData[1]);
////                    for (int i = 0; i < blockCount; i++) {
////                        String[] block = reader.readLine().split(",");
////                        int type = Integer.parseInt(block[0]);
////                        if (type == 1){
////                            level.blockList.add(new GlassBlock());
////                        }
////                        else if (type == 2){
////                            level.blockList.add(new WoodBlock());
////                        }
////                        else if (type == 3){
////                            level.blockList.add(new StoneBlock());
////                        }
////                        level.blockList.get(i).setPos(Integer.parseInt(block[1]), Integer.parseInt(block[2]));
////                        level.blockList.get(i).setScale(Integer.parseInt(block[3]), Integer.parseInt(block[4]));
////                    }
////                }
//
//                line = reader.readLine();
//                if (line != null) {
//                    String[] pigData = line.split(":");
//                    System.out.println(line);
//                    System.out.println(pigData[0]);
//                    System.out.println(pigData[1]);
//                    int pigCount = Integer.parseInt(pigData[1]);
//                    for (int i = 0; i < pigCount; i++) {
//                        String[] pig = reader.readLine().split(",");
//                        int type = Integer.parseInt(pig[0]);
//                        if (type == 10){
//                            level1Screen.pigList.add(new MinionPigs(level1Screen.batch, new Vector2(0, 0), level1Screen.world));
//                        }
//                        else if (type == 11){
//                            level1Screen.pigList.add(new KingPig(level1Screen.batch, new Vector2(0, 0), level1Screen.world));
//                        }
//                        level1Screen.pigList.get(i).setPosition(Float.parseFloat(pig[1]), Float.parseFloat(pig[2]));
////                        level1Screen.pigList.get(i).setPosition(Integer.parseInt(pig[3]), Integer.parseInt(pig[4]));
//                    }
//                }
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        return level1Screen;
//        }
//    }





