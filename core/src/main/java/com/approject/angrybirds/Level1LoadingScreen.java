package com.approject.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Level1LoadingScreen extends ScreenAdapter {
    private SpriteBatch batch;
    private Texture backgroundImage;
    private AngryBirds game;
    private Texture loading;
    private OrthographicCamera camera;
    private Viewport viewport;

    // Define the virtual width and height for the StretchViewport
    private static final float VIRTUAL_WIDTH = 1920;
    private static final float VIRTUAL_HEIGHT = 1080;

    public Level1LoadingScreen(AngryBirds game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        backgroundImage = new Texture("levelImage.png");
        loading = new Texture("loading.png");

        // Create an orthographic camera
        camera = new OrthographicCamera();
        // Create a StretchViewport with 800x600 dimensions
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        // Center the camera
        camera.position.set(VIRTUAL_WIDTH / 2f, VIRTUAL_HEIGHT / 2f, 0);
        camera.update();

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                // If spacebar is pressed, switch to Level1Screen
                if (keycode == Input.Keys.SPACE) {
                    game.setScreen(new Level1Screen(game));
                }
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
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
    }
}
