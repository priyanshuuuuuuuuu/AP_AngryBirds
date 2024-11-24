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

public class LevelComplete extends ScreenAdapter {
    AngryBirds game;
    private SpriteBatch batch;
    private Texture backGroundImage;
    private static final float VIRTUAL_WIDTH = 1920;
    private static final float VIRTUAL_HEIGHT = 1080;
    private Viewport viewport;
    Texture retryButton, retryHoverButton, homeButton, homeHoverButton;
    Rectangle retryButtonBound, homeButtonBound;

    public LevelComplete(AngryBirds game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        backGroundImage = new Texture("sucess.png");

        retryButton = new Texture("retryButton.png");
        retryHoverButton = new Texture("retryHover.png");
        homeButton = new Texture("home.png");
        homeHoverButton = new Texture("homeHover.png");
        MusicControl.stopGameplayMusic();
        MusicControl.playSuccessMusic();
        // Set retry button dimensions and position (centered)
        float retryButtonWidth = 420;
        float retryButtonHeight = 100;
        float retryButtonX = (VIRTUAL_WIDTH - retryButtonWidth) / 2; // Center horizontally
        float buttonY = 100; // Bottom alignment for both buttons
        retryButtonBound = new Rectangle(retryButtonX, buttonY, retryButtonWidth, retryButtonHeight);

        // Set home button dimensions and position (100x100, aligned to left)
        float homeButtonSize = 100;
        float homeButtonX = 100;
        homeButtonBound = new Rectangle(homeButtonX, buttonY, homeButtonSize, homeButtonSize);

        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        viewport.apply(true);
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Apply the viewport and set projection matrix
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        // Draw background and other elements
        batch.draw(backGroundImage, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);


        Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        viewport.unproject(touchPos);  // Convert screen coordinates to world coordinates

        // Retry button hover and action
        if (retryButtonBound.contains(touchPos.x, touchPos.y)) {
            batch.draw(retryHoverButton, retryButtonBound.x, retryButtonBound.y, retryButtonBound.width, retryButtonBound.height);
            if (Gdx.input.isTouched()) {
                game.setScreen(new Level1Screen(game));  // Retry the level
            }
        } else {
            batch.draw(retryButton, retryButtonBound.x, retryButtonBound.y, retryButtonBound.width, retryButtonBound.height);
        }

        // Home button hover and action
        if (homeButtonBound.contains(touchPos.x, touchPos.y)) {
            batch.draw(homeHoverButton, homeButtonBound.x, homeButtonBound.y, homeButtonBound.width, homeButtonBound.height);
            if (Gdx.input.isTouched()) {
                game.setScreen(new MainScreen(game));  // Navigate to MainScreen
            }
        } else {
            batch.draw(homeButton, homeButtonBound.x, homeButtonBound.y, homeButtonBound.width, homeButtonBound.height);
        }

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        backGroundImage.dispose();
        batch.dispose();
        retryButton.dispose();
        retryHoverButton.dispose();
        homeButton.dispose();
        homeHoverButton.dispose();
        MusicControl.dispose();
        super.dispose();
    }
}
