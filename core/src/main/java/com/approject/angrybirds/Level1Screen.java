package com.approject.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Level1Screen extends ScreenAdapter {
    private AngryBirds game;
    private SpriteBatch batch;
    private Texture background;
    private Viewport viewport;
    private RedBird redBird1;  // First RedBird
    private RedBird redBird2;  // Second RedBird
    private Texture pauseButton;
    private Texture playButton;
    private Rectangle pauseButtonBounds;  // For detecting click on pause/play button
    private boolean isPaused;  // Track if the game is paused

    // Constants for virtual width and height
    private static final float VIRTUAL_WIDTH = 1920;
    private static final float VIRTUAL_HEIGHT = 1080;

    public Level1Screen(AngryBirds game) {
        this.game = game;
        isPaused = false;  // Game starts in playing state
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        background = new Texture("gamePlay.png");
        pauseButton = new Texture("pauseButton.png");
        playButton = new Texture("play.png");

        // Initialize RedBird objects with their positions
        redBird1 = new RedBird(batch, new Vector2(100, 50));  // Red bird at position (100, 50)
        redBird2 = new RedBird(batch, new Vector2(200, 100)); // Another red bird at position (200, 100)

        // Create a viewport with 1920x1080 dimensions
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        viewport.apply(true);  // Apply the viewport and center the camera

        // Define the bounds of the pause button (used for click detection)
        pauseButtonBounds = new Rectangle(20, 950, 100, 100);  // Position and size of the button
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update the viewport to handle resizing
        viewport.apply();

        // Set the SpriteBatch to draw within the viewport's bounds
        batch.setProjectionMatrix(viewport.getCamera().combined);

        // Begin drawing the background and elements
        batch.begin();
        batch.draw(background, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);  // Draw background stretched to viewport size

        // Draw the RedBirds on the screen
        redBird1.render();  // Render redBird1 at its current position
        redBird2.render();  // Render redBird2 at its current position

        // Draw the pause button if the game is not paused, otherwise draw the play button
        if (isPaused) {
            batch.draw(playButton, pauseButtonBounds.x, pauseButtonBounds.y, pauseButtonBounds.width, pauseButtonBounds.height);
        } else {
            batch.draw(pauseButton, pauseButtonBounds.x, pauseButtonBounds.y, pauseButtonBounds.width, pauseButtonBounds.height);
        }
        batch.end();

        // Handle input for pause/play button
        if (Gdx.input.isTouched()) {
            Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPos);  // Convert screen coordinates to world coordinates

            // Check if the pause/play button is clicked
            if (pauseButtonBounds.contains(touchPos)) {
                if (isPaused) {
                    // If game is paused, clicking button will resume game
                    resumeGame();
                } else {
                    // If game is playing, clicking button will pause the game
                    pauseGame();
                }
            }
        }
    }

    private void pauseGame() {
        isPaused = true;
        game.setScreen(new PauseScreen(game, this));  // Switch to pause screen
    }

    private void resumeGame() {
        isPaused = false;
    }

    @Override
    public void resize(int width, int height) {
        // Update the viewport size on window resize
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        redBird1.dispose();  // Dispose the RedBird textures
        redBird2.dispose();  // Dispose the RedBird textures
        pauseButton.dispose();
        playButton.dispose();
    }
}
