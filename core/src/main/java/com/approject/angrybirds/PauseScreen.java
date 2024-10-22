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

public class PauseScreen extends ScreenAdapter {
    private AngryBirds game;
    private Level1Screen level1Screen;  // Reference to the level screen
    private SpriteBatch batch;
    private Texture background;
    private Texture resumeButton, resumeHoverButton;
    private Texture restartButton, restartHoverButton;
    private Texture muteButton, muteHoverButton;
    private Texture unmuteButton, unmuteHoverButton;
    private Texture quitButton, quitHoverButton;
    private Rectangle resumeButtonBounds, restartButtonBounds, muteButtonBounds, unmuteButtonBounds, quitButtonBounds;
    private Viewport viewport;  // Viewport for handling screen resizing
    private Texture gamePause;

    // Constants for virtual width and height
    private static final float VIRTUAL_WIDTH = 1920;
    private static final float VIRTUAL_HEIGHT = 1080;

    private boolean isMuted = false;  // A flag to track mute state

    public PauseScreen(AngryBirds game, Level1Screen level1Screen) {
        this.game = game;
        this.level1Screen = level1Screen;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        background = new Texture("fade.png");
        gamePause = new Texture("gamePause.png");

        // Buttons and hover textures
        resumeButton = new Texture("resume.png");
        resumeHoverButton = new Texture("resumeHover.png");
        restartButton = new Texture("restartButton.png");
        restartHoverButton = new Texture("restartHover.png");
        muteButton = new Texture("muteButton.png");
        muteHoverButton = new Texture("muteHover.png");
        unmuteButton = new Texture("unmuteButton.png");
        unmuteHoverButton = new Texture("unmuteHover.png");
        quitButton = new Texture("quitGame.png");
        quitHoverButton = new Texture("quitHover.png");

        // Set up the viewport with the virtual size of 1920x1080
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        viewport.apply(true);  // Ensure the viewport is centered

        // Define the position and size of the buttons
        int buttonSpacing = 35;
        float buttonWidth = 450;
        float buttonHeight = 120;
        float centerX = 780;

        resumeButtonBounds = new Rectangle(centerX, 570, buttonWidth, buttonHeight);
        restartButtonBounds = new Rectangle(centerX, resumeButtonBounds.y - buttonHeight - buttonSpacing, buttonWidth, buttonHeight);
        muteButtonBounds = new Rectangle(centerX, restartButtonBounds.y - buttonHeight - buttonSpacing, buttonWidth, buttonHeight);
        quitButtonBounds = new Rectangle(centerX, muteButtonBounds.y - buttonHeight - buttonSpacing, buttonWidth, buttonHeight);
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
        batch.draw(gamePause, 500, 800, 1000, 200);  // Draw the pause title

        // Get touch position and unproject to world coordinates
        Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        viewport.unproject(touchPos);  // Convert screen coordinates to world coordinates

        boolean isHovering = false;

        // Resume button hover and action
        if (resumeButtonBounds.contains(touchPos.x, touchPos.y)) {
            batch.draw(resumeHoverButton, resumeButtonBounds.x, resumeButtonBounds.y, resumeButtonBounds.width, resumeButtonBounds.height);
            isHovering = true;
            if (Gdx.input.isTouched()) {
                game.setScreen(level1Screen);  // Resume the game
            }
        } else {
            batch.draw(resumeButton, resumeButtonBounds.x, resumeButtonBounds.y, resumeButtonBounds.width, resumeButtonBounds.height);
        }

        // Restart button hover and action
        if (restartButtonBounds.contains(touchPos.x, touchPos.y)) {
            batch.draw(restartHoverButton, restartButtonBounds.x, restartButtonBounds.y, restartButtonBounds.width, restartButtonBounds.height);
            isHovering = true;
            if (Gdx.input.isTouched()) {
                // Logic to restart the game (re-initialize the level)
                game.setScreen(new Level1Screen(game));  // Restart the level
            }
        } else {
            batch.draw(restartButton, restartButtonBounds.x, restartButtonBounds.y, restartButtonBounds.width, restartButtonBounds.height);
        }

        // Mute/unmute button hover and action
        if (muteButtonBounds.contains(touchPos.x, touchPos.y)) {
            if (isMuted) {
                batch.draw(unmuteHoverButton, muteButtonBounds.x, muteButtonBounds.y, muteButtonBounds.width, muteButtonBounds.height);
            } else {
                batch.draw(muteHoverButton, muteButtonBounds.x, muteButtonBounds.y, muteButtonBounds.width, muteButtonBounds.height);
            }
            isHovering = true;
            if (Gdx.input.isTouched()) {
                isMuted = !isMuted;
            }
        } else {
            if (isMuted) {
                batch.draw(unmuteButton, muteButtonBounds.x, muteButtonBounds.y, muteButtonBounds.width, muteButtonBounds.height);
            } else {
                batch.draw(muteButton, muteButtonBounds.x, muteButtonBounds.y, muteButtonBounds.width, muteButtonBounds.height);
            }
        }

        // Quit button hover and action
        if (quitButtonBounds.contains(touchPos.x, touchPos.y)) {
            batch.draw(quitHoverButton, quitButtonBounds.x, quitButtonBounds.y, quitButtonBounds.width, quitButtonBounds.height);
            isHovering = true;
            if (Gdx.input.isTouched()) {
                game.setScreen(new RetryScoreScreen(game));
            }
        } else {
            batch.draw(quitButton, quitButtonBounds.x, quitButtonBounds.y, quitButtonBounds.width, quitButtonBounds.height);
        }

        batch.end();

        // Change the cursor to hand if hovering over a button
        if (isHovering) {
            Gdx.graphics.setSystemCursor(com.badlogic.gdx.graphics.Cursor.SystemCursor.Hand);
        } else {
            Gdx.graphics.setSystemCursor(com.badlogic.gdx.graphics.Cursor.SystemCursor.Arrow);
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
        resumeHoverButton.dispose();
        restartButton.dispose();
        restartHoverButton.dispose();
        muteButton.dispose();
        muteHoverButton.dispose();
        unmuteButton.dispose();
        unmuteHoverButton.dispose();
        quitButton.dispose();
        quitHoverButton.dispose();
        gamePause.dispose();
    }
}
