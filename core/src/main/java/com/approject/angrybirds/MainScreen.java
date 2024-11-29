package com.approject.angrybirds;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class MainScreen extends ScreenAdapter {
    SpriteBatch batch;
    Texture backgroundImage;
    Texture newGame, newGameHover;
    Texture loadGame, loadGameHover;
    Texture settings, settingsHover;
    Texture exit, exitHover;
    Texture title;
    AngryBirds game;
    Rectangle newGameButton, loadGameButton, settingsButton, exitButton;
    private GameState gameState;
    private Level1Screen level1Screen = null;

    private static final float VIRTUAL_WIDTH = 1920;
    private static final float VIRTUAL_HEIGHT = 1080;

    OrthographicCamera camera;
    Viewport viewport;

    public MainScreen(AngryBirds game) {
        this.game = game;
        this.level1Screen = level1Screen;
        this.gameState = gameState;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        // Load textures
        backgroundImage = new Texture("finalBg.png");
        newGame = new Texture("newGame.png");
        newGameHover = new Texture("newGameHover.png");
        loadGame = new Texture("loadGame.png");
        loadGameHover = new Texture("loadGameHover.png");
        settings = new Texture("settings.png");
        settingsHover = new Texture("settingsHover.png");
        exit = new Texture("exit.png");
        exitHover = new Texture("exitHover.png");
        title = new Texture("title.png");
        camera = new OrthographicCamera();
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        camera.position.set(VIRTUAL_WIDTH / 2f, VIRTUAL_HEIGHT / 2f, 0);
        camera.update();
        MusicControl.stopScoreMusic();

        int buttonSpacing = 35;  // Increased spacing between buttons
        float buttonWidth = 450;  // Increased button width
        float buttonHeight = 120; // Increased button height
        float centerX = 780;      // Adjusted x position for better centering

        // Increase button sizes
        newGameButton = new Rectangle(centerX, 570, buttonWidth, buttonHeight);
        loadGameButton = new Rectangle(centerX, newGameButton.y - buttonHeight - buttonSpacing, buttonWidth, buttonHeight);
        settingsButton = new Rectangle(centerX, loadGameButton.y - buttonHeight - buttonSpacing, buttonWidth, buttonHeight);
        exitButton = new Rectangle(centerX, settingsButton.y - buttonHeight - buttonSpacing, buttonWidth, buttonHeight);

        // Play background music when MainScreen is shown
        MusicControl.stopGameplayMusic();
        MusicControl.playBackgroundMusic();
    }

    @Override
    public void render(float delta) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(backgroundImage, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

        float titleX = 470;
        float titleY = 750;
        int titleWidth = 1050;
        int titleHeight = 600;
        batch.draw(title, titleX, titleY, titleWidth, titleHeight);

        Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        viewport.unproject(touchPos);  // Convert screen coordinates to world coordinates

        boolean isHovering = false;

        if (settingsButton.contains(touchPos.x, touchPos.y)) {
            batch.draw(settingsHover, settingsButton.x, settingsButton.y, settingsButton.width, settingsButton.height);
            isHovering = true;
        } else {
            batch.draw(settings, settingsButton.x, settingsButton.y, settingsButton.width, settingsButton.height);
        }

        if (newGameButton.contains(touchPos.x, touchPos.y)) {
            batch.draw(newGameHover, newGameButton.x, newGameButton.y, newGameButton.width, newGameButton.height);
            isHovering = true;
        } else {
            batch.draw(newGame, newGameButton.x, newGameButton.y, newGameButton.width, newGameButton.height);
        }

        if (exitButton.contains(touchPos.x, touchPos.y)) {
            batch.draw(exitHover, exitButton.x, exitButton.y, exitButton.width, exitButton.height);
            isHovering = true;
        } else {
            batch.draw(exit, exitButton.x, exitButton.y, exitButton.width, exitButton.height);
        }

        if (loadGameButton.contains(touchPos.x, touchPos.y)) {
            batch.draw(loadGameHover, loadGameButton.x, loadGameButton.y, loadGameButton.width, loadGameButton.height);
            isHovering = true;
            if(Gdx.input.isTouched()){
//                game.setScreen(new LoadGameScreen(game, gameState));
//                Level1Screen.loadGame();
//                game.setScreen(new LoadGameScreen(game, gameState));
                System.out.println("load game");
            }
        } else {
            batch.draw(loadGame, loadGameButton.x, loadGameButton.y, loadGameButton.width, loadGameButton.height);
        }

        batch.end();

        // Button actions
        if (Gdx.input.isTouched()) {
            if (newGameButton.contains(touchPos.x, touchPos.y)) {
                game.setScreen(new LevelsScreen(game));
            } else if (loadGameButton.contains(touchPos.x, touchPos.y)) {
//                Level1Screen.loadGame();
                game.setScreen(new LoadGameScreen(game, gameState, level1Screen));
                System.out.println("Load Game");
            } else if (settingsButton.contains(touchPos.x, touchPos.y)) {
                game.setScreen(new Settings(game, gameState));
            } else if (exitButton.contains(touchPos.x, touchPos.y)) {
                Gdx.app.exit();
            }
        }

        // Change cursor based on hovering
        if (isHovering) {
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
        } else {
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
        }
    }
    private void loadGame() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("savegame.dat"))) {
            gameState = (GameState) ois.readObject(); // Deserialize the game state
            game.setScreen(Level1Screen.loadGame()); // Load the level with the saved game state
            System.out.println("Game loaded successfully!");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Failed to load game!");
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        MusicControl.stopBackgroundMusic();  // Stop background music if explicitly needed
        newGame.dispose();
        newGameHover.dispose();
        loadGame.dispose();
        settings.dispose();
        settingsHover.dispose();
        exit.dispose();
        batch.dispose();
        loadGameHover.dispose();
        exitHover.dispose();
        title.dispose();
        backgroundImage.dispose();
    }
}
