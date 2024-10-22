package com.approject.angrybirds;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class LoadingScreen extends ScreenAdapter {
    private SpriteBatch batch;
    private AngryBirds game;
    private Texture backgroundImage;
    private OrthographicCamera camera;
    private Viewport viewport;
    private float xPos, yPos;
    private Texture loadingBar;
    private Texture pressKeySpace;

    // Define the virtual width and height for the FitViewport
    private static final float VIRTUAL_WIDTH = 800;
    private static final float VIRTUAL_HEIGHT = 600;

    // For animating the loading bar
    private float elapsedTime = 0f; // Time elapsed since loading started
    private float maxLoadingTime = 2f; // Total time for the loading bar to fill (2 seconds)

    public LoadingScreen(AngryBirds game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        backgroundImage = new Texture("try.png");
        loadingBar = new Texture("bar.png");
        pressKeySpace = new Texture("space.png"); // Load the image for "Press SPACE to continue"

        // Create an orthographic camera
        camera = new OrthographicCamera();
        // Create a FitViewport with 800x600 dimensions
        viewport = new StretchViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        // Ensure the camera is centered
        camera.position.set(VIRTUAL_WIDTH / 2f, VIRTUAL_HEIGHT / 2f, 0);
        camera.update();

        // Set the initial position for the background image
        xPos = (VIRTUAL_WIDTH - backgroundImage.getWidth()) / 2f;
        yPos = (VIRTUAL_HEIGHT - backgroundImage.getHeight()) / 2f;

        // Set the input processor to listen for spacebar press
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                // If spacebar is pressed, switch to MainScreen
                if (keycode == Input.Keys.SPACE) {
                    game.setScreen(new MainScreen(game));
                }
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        // Update the camera and viewport
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        // Clear the screen and set background color
        Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update the elapsed time
        elapsedTime += delta;

        // Begin drawing
        batch.begin();

        // Draw the background image centered based on the virtual coordinates
        batch.draw(backgroundImage, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

        // Calculate the width of the loading bar as a percentage of elapsed time
        float loadingProgress = Math.min(elapsedTime / maxLoadingTime, 1f); // Ensure the progress doesn't exceed 1
        float loadingBarWidth = 300 * loadingProgress; // Adjust width based on progress
        float loadingBarHeight = 40;

        // Position the loading bar at the bottom center (horizontally start from the left)
        float loadingBarX = (VIRTUAL_WIDTH - 300) / 2f;  // Fixed start position, bar will grow to the right
        float loadingBarY = 50; // Set Y position near the bottom (adjust as needed)

        // If loading is not complete, draw the loading bar
        if (elapsedTime < maxLoadingTime) {
            batch.draw(loadingBar, loadingBarX, loadingBarY, loadingBarWidth, loadingBarHeight);
        }

        // If loading is complete, show the "Press space to continue" image where the loading bar was
        if (elapsedTime >= maxLoadingTime) {
            float pressKeySpaceWidth = 300; // Adjust according to the size of the image
            float pressKeySpaceHeight = 40; // Adjust according to the size of the image
            batch.draw(pressKeySpace, loadingBarX, loadingBarY, pressKeySpaceWidth, pressKeySpaceHeight);
        }

        batch.end();

        // Check if spacebar is pressed, then switch to MainScreen
        if (elapsedTime >= maxLoadingTime && Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(new MainScreen(game));
        }
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
        loadingBar.dispose();
        pressKeySpace.dispose(); // Dispose of the pressKeySpace texture
    }
}
