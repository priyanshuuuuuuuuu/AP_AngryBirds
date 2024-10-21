package com.approject.angrybirds;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class LoadingScreen extends ScreenAdapter {
    private SpriteBatch batch;
    private AngryBirds game;
    private Texture backgroundImage;
    private OrthographicCamera camera;
    private Viewport viewport;
    private float xPos, yPos;

    // Define the virtual width and height for the FitViewport
    private static final float VIRTUAL_WIDTH = 800;
    private static final float VIRTUAL_HEIGHT = 600;

    public LoadingScreen(AngryBirds game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        backgroundImage = new Texture("MainScreenBackground.png");

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

        // Begin drawing
        batch.begin();
        // Draw the background image centered based on the virtual coordinates
        batch.draw(backgroundImage,0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
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
    }
}
