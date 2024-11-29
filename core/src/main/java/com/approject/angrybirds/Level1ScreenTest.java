//package com.approject.angrybirds;
//
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.physics.box2d.BodyDef;
//import org.junit.Before;
//import org.junit.Test;
//import static org.junit.Assert.*;
//
//public class Level1ScreenTest {
//    private Level1Screen level1Screen;
//    private AngryBirds game;
//
//    @Before
//    public void setUp() {
//        game = new AngryBirds();
//        level1Screen = new Level1Screen(game);
//    }
//
//    @Test
//    public void testAddScore() {
//        level1Screen.addScore(100);
//        assertEquals(100, level1Screen.getScore());
//    }
//
//    @Test
//    public void testInitializeNewGame() {
//        level1Screen.initializeNewGame();
//        assertNotNull(level1Screen.birdList);
//        assertNotNull(level1Screen.pigList);
//    }
//
//    @Test
//    public void testSaveAndLoadGame() {
//        level1Screen.addScore(200);
//        level1Screen.saveGame();
//        Level1Screen loadedScreen = Level1Screen.loadGame();
//        assertNotNull(loadedScreen);
//        assertEquals(200, loadedScreen.getScore());
//    }
//
//    @Test
//    public void testLaunchObject() {
//        Vector2 dragVector = new Vector2(1, 1);
//        level1Screen.launchObject(dragVector);
//        assertNotNull(level1Screen.currentBird);
//        assertEquals(BodyDef.BodyType.DynamicBody, level1Screen.currentBird.getBody().getType());
//    }
//}
