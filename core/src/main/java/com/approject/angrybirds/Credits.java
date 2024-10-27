package com.approject.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Credits extends ScreenAdapter {
    private static final float VIRTUAL_WIDTH = 1920;
    private static final float VIRTUAL_HEIGHT = 1080;

    private final AngryBirds game;
    private SpriteBatch batch;
    private Texture background;
    private Texture creditsName;
    private Texture backButton;
    private Rectangle backButtonBounds;
    private Viewport viewport;
    private OrthographicCamera camera;

    public Credits(AngryBirds game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        // Set up the camera and viewport with the specified virtual width and height
        camera = new OrthographicCamera();
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        camera.position.set(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2, 0);
        camera.update();

        background = new Texture("image.png");
        creditsName = new Texture("ew.png");
        backButton = new Texture("backButton.png");

        // Define the back button's area
        float backButtonWidth = 100;
        float backButtonHeight = 100;
        float backButtonX = 20;
        float backButtonY = VIRTUAL_HEIGHT - backButtonHeight - 20;  // Offset from the top edge
        backButtonBounds = new Rectangle(backButtonX, backButtonY, backButtonWidth, backButtonHeight);
    }

    @Override
    public void render(float delta) {
        // Clear the screen to a black background
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Apply the viewport and camera
        viewport.apply();
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        // Draw the background to fit the entire virtual screen size
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

        // Handle input for the back button
        if (Gdx.input.justTouched()) {
            Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPos);

            if (backButtonBounds.contains(touchPos.x, touchPos.y)) {
                game.setScreen(new Settings(game));  // Navigate back to the Settings screen
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);  // Ensure viewport updates correctly on resize
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        creditsName.dispose();
        backButton.dispose();
    }
}
