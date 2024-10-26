package com.approject.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Credits extends ScreenAdapter {
    private static final float VIRTUAL_WIDTH = 1920;
    private static final float VIRTUAL_HEIGHT = 1080;

    AngryBirds game;
    SpriteBatch batch;
    Texture background;
    Texture creditsName;
    Texture backButton;
    Rectangle backButtonBounds;
    Viewport viewport;

    public Credits(AngryBirds game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);  // Setting up the viewport with 1920x1080

        background = new Texture("image.png");
        creditsName = new Texture("ew.png");
        backButton = new Texture("backButton.png");

        // Define the rectangle area for the back button at the top-left corner
        float backButtonWidth = 100;
        float backButtonHeight = 100;
        float backButtonX = 20;
        float backButtonY = VIRTUAL_HEIGHT - backButtonHeight - 20;  // Offset from the top edge
        backButtonBounds = new Rectangle(backButtonX, backButtonY, backButtonWidth, backButtonHeight);
    }

    @Override
    public void render(float delta) {
        viewport.apply();  // Apply the viewport
        batch.setProjectionMatrix(viewport.getCamera().combined);  // Update the batch projection matrix

        batch.begin();

        // Draw the background
        batch.draw(background, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

        // Center the creditsName image
        float creditsWidth = 600;
        float creditsHeight = 70;
        float creditsX = (VIRTUAL_WIDTH - creditsWidth) / 2;
        float creditsY = (VIRTUAL_HEIGHT - creditsHeight) / 2;
        batch.draw(creditsName, creditsX, creditsY, creditsWidth, creditsHeight);

        // Draw the back button
        batch.draw(backButton, backButtonBounds.x, backButtonBounds.y, backButtonBounds.width, backButtonBounds.height);

        batch.end();

        // Handle single touch detection for back button
        if (Gdx.input.justTouched()) {  // Detects a single tap
            Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPos);  // Convert to world coordinates in the viewport

            // Check if the back button was touched
            if (backButtonBounds.contains(touchPos.x, touchPos.y)) {
                game.setScreen(new Settings(game));  // Go to Settings screen
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);  // Update the viewport on resize
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        creditsName.dispose();
        backButton.dispose();
    }
}
