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

public class RetryScoreScreen extends ScreenAdapter {
    AngryBirds game;
    private SpriteBatch batch;
    private Texture backGroundImage;
    private static final float VIRTUAL_WIDTH = 1920;
    private static final float VIRTUAL_HEIGHT = 1080;
    private Viewport viewport;
    private Texture pigs;
    private Texture title;
    Texture retryButton, retryHoverButton;
    Rectangle retryButtonBound;


    public RetryScoreScreen(AngryBirds game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        backGroundImage = new Texture("retryScoreImage.png");
        pigs = new Texture("pigs.png");
        title = new Texture("wellTried.png");
        retryButton = new Texture("retryButton.png");
        retryHoverButton = new Texture("retryHover.png");

        float buttonWidth = 420;
        float buttonHeight = 100;
        float centerX = 780;
        retryButtonBound = new Rectangle(centerX, 100, buttonWidth, buttonHeight);

        // Initialize the viewport with the virtual size of 1920x1080
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        viewport.apply(true);  // Ensure the viewport is centered
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Apply the viewport to handle any resizing
        viewport.apply();

        // Set the projection matrix of the batch to the viewport's camera combined matrix
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        // Draw background and elements
        batch.draw(backGroundImage, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);  // Stretch the background
        batch.draw(pigs, 550, 0, 900, 900);
        batch.draw(title, 360, 800, 1200, 200);

        Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        viewport.unproject(touchPos);  // Convert screen coordinates to world coordinates

        boolean isHovering = false;

        // Resume button hover and action
        if (retryButtonBound.contains(touchPos.x, touchPos.y)) {
            batch.draw(retryHoverButton, retryButtonBound.x, retryButtonBound.y, retryButtonBound.width, retryButtonBound.height);
            isHovering = true;
            if (Gdx.input.isTouched()) {
                game.setScreen(new Level1Screen(game));  // Resume the game
            }
        } else {
            batch.draw(retryButton, retryButtonBound.x, retryButtonBound.y, retryButtonBound.width, retryButtonBound.height);
        }



        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // Update the viewport size based on new screen dimensions
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        backGroundImage.dispose();
        batch.dispose();
        pigs.dispose();
        title.dispose();
    }
}
