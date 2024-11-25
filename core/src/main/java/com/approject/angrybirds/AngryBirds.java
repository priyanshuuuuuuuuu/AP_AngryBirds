package com.approject.angrybirds;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class AngryBirds extends Game {
    SpriteBatch batch;
    Stage stage;
    @Override
    public void create () {
        batch = new SpriteBatch();
        stage = new Stage();
        GameState gameState = new GameState(0,1, new Vector2(0,0));
        setScreen(new Level1Screen(this, gameState));

    }
    @Override
    public void dispose () {
        batch.dispose();
        stage.dispose();
    }

}
