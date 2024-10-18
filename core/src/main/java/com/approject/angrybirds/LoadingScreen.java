package com.approject.angrybirds;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LoadingScreen extends ScreenAdapter {
    SpriteBatch batch;
    AngryBirds game;
    Texture backgroundImage;
    float xPos, yPos;

    public LoadingScreen(AngryBirds game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        backgroundImage = new Texture("MainScreenBackground.png");

        // Calculate the center position for the background image
        xPos = (Gdx.graphics.getWidth() - backgroundImage.getWidth()) / 2f;
        yPos = (Gdx.graphics.getHeight() - backgroundImage.getHeight()) / 2f;

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
        // Clear the screen and set background color
        Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Begin drawing
        batch.begin();
        // Draw the background image centered on the screen
        batch.draw(backgroundImage, xPos, yPos);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        backgroundImage.dispose();
    }
}
