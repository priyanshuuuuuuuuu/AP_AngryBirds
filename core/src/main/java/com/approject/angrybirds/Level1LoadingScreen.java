package com.approject.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Level1LoadingScreen extends ScreenAdapter {
    private SpriteBatch batch;
    private Texture backgroundImage;
    private AngryBirds game;
    private Texture loading;
    private OrthographicCamera camera;
    private Viewport viewport;
    private BitmapFont font;  // Font for displaying countdown

    // Define the virtual width and height for the FitViewport
    private static final float VIRTUAL_WIDTH = 1920;
    private static final float VIRTUAL_HEIGHT = 1080;

    // Time tracking variable for the 5-second loading delay
    private float elapsedTime = 0;

    public Level1LoadingScreen(AngryBirds game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        backgroundImage = new Texture("levelImage.png");
        loading = new Texture("loading.png");
        font = new BitmapFont();  // Initialize font with default settings

        // Create an orthographic camera
        camera = new OrthographicCamera();
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        camera.position.set(VIRTUAL_WIDTH / 2f, VIRTUAL_HEIGHT / 2f, 0);
        camera.update();

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                // If spacebar is pressed, switch to Level1Screen immediately
                if (keycode == Input.Keys.SPACE) {
                    game.setScreen(new Level1Screen(game));
                }
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        // Increment elapsed time with the time between frames
        elapsedTime += delta;

        // Calculate remaining time (5 seconds minus elapsed time)
        int remainingTime = 5 - (int) elapsedTime;

        // Check if 5 seconds have passed
        if (remainingTime <= 0) {
            game.setScreen(new Level1Screen(game));
            return;  // Exit the render method early to avoid additional drawing
        }

        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update the camera and viewport
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        // Begin drawing
        batch.begin();
        batch.draw(backgroundImage, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        batch.draw(loading, 1400, 20, 550, 100);

        // Draw the countdown text in the top-right corner
        String countdownText = "Time Left: " + remainingTime;
        font.draw(batch, countdownText, VIRTUAL_WIDTH - 200, VIRTUAL_HEIGHT - 20);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // Update the viewport to handle resizing
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        batch.dispose();
        backgroundImage.dispose();
        loading.dispose();
        font.dispose();  // Dispose of the font to free up resources
    }
}
