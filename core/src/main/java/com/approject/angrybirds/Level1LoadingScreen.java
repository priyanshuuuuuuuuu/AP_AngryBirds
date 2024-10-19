package com.approject.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Level1LoadingScreen extends ScreenAdapter {
    SpriteBatch batch;
    Texture backgroundImage;
    AngryBirds game;

    public Level1LoadingScreen(AngryBirds game) {
        this.game = game;
    }
    @Override
    public void show(){
        batch = new SpriteBatch();
        backgroundImage = new Texture("bg.png");

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                // If spacebar is pressed, switch to MainScreen
                if (keycode == Input.Keys.SPACE) {
                    game.setScreen(new Level1Screen(game));
                }
                return true;
            }
        });
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(backgroundImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }
    @Override
    public void dispose(){
        batch.dispose();
        backgroundImage.dispose();
    }
}
