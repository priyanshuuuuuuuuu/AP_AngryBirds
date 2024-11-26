package com.approject.angrybirds;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class SettingMM extends ScreenAdapter {
    SpriteBatch batch;
    Texture backgroundImage;
    Texture backButton;
    AngryBirds game;
    Rectangle backButtonBounds;
    private GameState gamestate;

    public SettingMM(AngryBirds game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        backgroundImage = new Texture("settingsBackground.png");
        backButton = new Texture("backButton.png");

        // Define button positions and sizes
        int centerX = (Gdx.graphics.getWidth() - backButton.getWidth()) / 2;
        backButtonBounds = new Rectangle(centerX, 100, backButton.getWidth(), backButton.getHeight());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Begin drawing
        batch.begin();
        batch.draw(backgroundImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(backButton, backButtonBounds.x, backButtonBounds.y);
        batch.end();

        // Handle input
        if (Gdx.input.isTouched()) {
            Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
            if (backButtonBounds.contains(touchPos.x, touchPos.y)) {
                game.setScreen(new MainScreen(game));
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        backgroundImage.dispose();
        backButton.dispose();
    }
}
