package com.approject.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Credits extends ScreenAdapter {
    AngryBirds game;
    SpriteBatch batch;
    Texture background;
    Texture creditsName;

    public Credits(AngryBirds game) {
        this.game = game;
    }
    @Override
    public void show(){
        batch = new SpriteBatch();
        background = new Texture("main.png");
        creditsName = new Texture("creditsName.png");
    }
    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(creditsName, 0, 0, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        batch.end();

    }
    @Override
    public void dispose(){
        batch.dispose();
        background.dispose();
        creditsName.dispose();
    }
}
