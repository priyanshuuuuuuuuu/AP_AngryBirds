package com.approject.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.w3c.dom.Text;

public class PauseScreen extends ScreenAdapter {
    private AngryBirds game;
    private Level1Screen level1Screen;  // Reference to the level screen
    private SpriteBatch batch;
    private Texture background;
    private Texture resumeButton;
    private Rectangle resumeButtonBounds;
    private Viewport viewport;  // Viewport for handling screen resizing
    private Texture gamePause;

    // Constants for virtual width and height
    private static final float VIRTUAL_WIDTH = 1920;
    private static final float VIRTUAL_HEIGHT = 1080;

    public PauseScreen(AngryBirds game, Level1Screen level1Screen) {
        this.game = game;
        this.level1Screen = level1Screen;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        background = new Texture("fade.png");
        resumeButton = new Texture("play.png");  // Button to resume the game

        // Define the position and size of the resume button
        resumeButtonBounds = new Rectangle(20, 950, 100, 100);  // Example position and size
        gamePause = new Texture("gamePause.png");
        // Set up the viewport with the virtual size of 1920x1080
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        viewport.apply(true);  // Ensure the viewport is centered
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
        batch.draw(background, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);  // Draw the pause screen background
        batch.draw(resumeButton, resumeButtonBounds.x, resumeButtonBounds.y, resumeButtonBounds.width, resumeButtonBounds.height);
        batch.draw(gamePause, 500, 800, 1000, 200);
        batch.end();

        // Handle input for resume button
        if (Gdx.input.isTouched()) {
            Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPos);  // Convert screen coordinates to world coordinates

            // Check if the resume button is clicked
            if (resumeButtonBounds.contains(touchPos.x, touchPos.y)) {
                game.setScreen(level1Screen);  // Resume the game by switching back to Level1Screen
            }
        }
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
        resumeButton.dispose();
        gamePause.dispose();
    }
}
