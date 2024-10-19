package com.approject.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class LevelsScreen extends ScreenAdapter {
    SpriteBatch batch;
    AngryBirds game;
    Texture backgroundImage;
    Texture title;
    Texture level1;
    Texture level2;
    Texture level3;
    Texture level4;
    Texture level5;
    Texture level6;
    Texture level7;
    Texture level8;
    Texture level9;
    Rectangle[] levelButtons;

    public LevelsScreen(AngryBirds game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        backgroundImage = new Texture("main.png");
        title = new Texture("title.png");
        level1 = new Texture("level1.png");
        level2 = new Texture("level2.png");
        level3 = new Texture("levels.png");
        level4 = new Texture("levels.png");
        level5 = new Texture("levels.png");
        level6 = new Texture("levels.png");
        level7 = new Texture("levels.png");
        level8 = new Texture("levels.png");
        level9 = new Texture("levels.png");


        // Initialize buttons
        levelButtons = new Rectangle[9];
        levelButtons[0] = new Rectangle(0, 0, level1.getWidth(), level1.getHeight());
        levelButtons[1] = new Rectangle(0, 0, level2.getWidth(), level2.getHeight());
        levelButtons[2] = new Rectangle(0, 0, level3.getWidth(), level3.getHeight());
        levelButtons[3] = new Rectangle(0, 0, level4.getWidth(), level4.getHeight());
        levelButtons[4] = new Rectangle(0, 0, level5.getWidth(), level5.getHeight());
        levelButtons[5] = new Rectangle(0, 0, level6.getWidth(), level6.getHeight());
        levelButtons[6] = new Rectangle(0, 0, level7.getWidth(), level7.getHeight());
        levelButtons[7] = new Rectangle(0, 0, level8.getWidth(), level8.getHeight());
        levelButtons[8] = new Rectangle(0, 0, level9.getWidth(), level9.getHeight());

        // Define button positions in a grid
        int buttonSpacing = 50;
        int buttonsPerRow = 5;
        int buttonWidth = level1.getWidth();
        int buttonHeight = level1.getHeight();
        int startX = (Gdx.graphics.getWidth() - (buttonWidth * buttonsPerRow + buttonSpacing * (buttonsPerRow - 1))) / 2;
        int startY = 400; // Adjusting to keep title at top

        for (int i = 0; i < levelButtons.length; i++) {
            int row = i / buttonsPerRow;
            int col = i % buttonsPerRow;
            levelButtons[i].setPosition(startX + col * (buttonWidth + buttonSpacing), startY - row * (buttonHeight + buttonSpacing));
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(backgroundImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(title, (Gdx.graphics.getWidth() - title.getWidth()) / 2, 650);
        batch.draw(level1, levelButtons[0].x, levelButtons[0].y);
        batch.draw(level2, levelButtons[1].x, levelButtons[1].y);
        batch.draw(level3, levelButtons[2].x, levelButtons[2].y);
        batch.draw(level4, levelButtons[3].x, levelButtons[3].y);
        batch.draw(level5, levelButtons[4].x, levelButtons[4].y);
        batch.draw(level6, levelButtons[5].x, levelButtons[5].y);
        batch.draw(level7, levelButtons[6].x, levelButtons[6].y);
        batch.draw(level8, levelButtons[7].x, levelButtons[7].y);
        batch.draw(level9, levelButtons[8].x, levelButtons[8].y);
        batch.end();

        // Handle input
        if (Gdx.input.isTouched()) {
            Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
            if (levelButtons[0].contains(touchPos.x, touchPos.y)) {
                 game.setScreen(new Level1LoadingScreen(game));
            } else if (levelButtons[1].contains(touchPos.x, touchPos.y)) {
                // game.setScreen(new Level2Screen(game));
            } else if (levelButtons[2].contains(touchPos.x, touchPos.y)) {
                // game.setScreen(new Level3Screen(game));
            } else if (levelButtons[3].contains(touchPos.x, touchPos.y)) {
                // game.setScreen(new Level4Screen(game));
            } else if (levelButtons[4].contains(touchPos.x, touchPos.y)) {
                // game.setScreen(new Level5Screen(game));
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        backgroundImage.dispose();
        title.dispose();
        level1.dispose();
        level2.dispose();
        level3.dispose();
        level4.dispose();
        level5.dispose();
    }
}
