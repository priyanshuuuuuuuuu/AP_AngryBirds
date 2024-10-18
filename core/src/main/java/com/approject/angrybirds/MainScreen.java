package com.approject.angrybirds;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class MainScreen extends ScreenAdapter {
    SpriteBatch batch;
    Texture backgroundImage;
    Texture newGame;
    Texture loadGame;
    Texture settings;
    Texture exit;
    Texture title;
    AngryBirds game;
    Rectangle newGameButton, loadGameButton, settingsButton, exitButton;

    public MainScreen(AngryBirds game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        backgroundImage = new Texture("main.png");
        newGame = new Texture("newGame.png");
        loadGame = new Texture("loadGame.png");
        settings = new Texture("settings.png");
        exit = new Texture("exit.png");
        title = new Texture("title.png");

        // Define button positions and sizes with spacing
        int buttonSpacing = 15;
        int centerX = (Gdx.graphics.getWidth() - newGame.getWidth()) / 2;

        // Set buttons positions (y positions relative to title)
        newGameButton = new Rectangle(centerX, 500, newGame.getWidth(), newGame.getHeight());
        loadGameButton = new Rectangle(centerX, newGameButton.y - newGameButton.height - buttonSpacing, loadGame.getWidth(), loadGame.getHeight());
        settingsButton = new Rectangle(centerX, loadGameButton.y - loadGameButton.height - buttonSpacing, settings.getWidth(), settings.getHeight());
        exitButton = new Rectangle(centerX, settingsButton.y - settingsButton.height - buttonSpacing, exit.getWidth(), exit.getHeight());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        // Draw the background
        batch.draw(backgroundImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Draw the title at the top center of the screen
        float titleX = (Gdx.graphics.getWidth() - title.getWidth())/2;
        float titleY = 650; // No margin from the top
        batch.draw(title, titleX, titleY);

        // Draw buttons
        batch.draw(newGame, newGameButton.x, newGameButton.y);
        batch.draw(loadGame, loadGameButton.x, loadGameButton.y);
        batch.draw(settings, settingsButton.x, settingsButton.y);
        batch.draw(exit, exitButton.x, exitButton.y);
        batch.end();

        // Handle input
        if (Gdx.input.isTouched()) {
            Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
            if (newGameButton.contains(touchPos.x, touchPos.y)) {
                // Transition to Levels screen
                game.setScreen(new LevelsScreen(game));
            } else if (loadGameButton.contains(touchPos.x, touchPos.y)) {
                // Handle load game
                System.out.println("Load Game clicked!");
            } else if (settingsButton.contains(touchPos.x, touchPos.y)) {
                // Handle settings
                System.out.println("Settings clicked!");
            } else if (exitButton.contains(touchPos.x, touchPos.y)) {
                // Exit the game
                Gdx.app.exit();
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        backgroundImage.dispose();
        newGame.dispose();
        loadGame.dispose();
        settings.dispose();
        exit.dispose();
        title.dispose();
    }
}
