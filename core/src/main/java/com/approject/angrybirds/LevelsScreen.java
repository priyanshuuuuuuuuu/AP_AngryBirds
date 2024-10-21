package com.approject.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

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

    OrthographicCamera camera;
    Viewport viewport;

    // Virtual width and height for the camera
    private static final float VIRTUAL_WIDTH = 800;
    private static final float VIRTUAL_HEIGHT = 600;

    public LevelsScreen(AngryBirds game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        backgroundImage = new Texture("main.png");
        title = new Texture("title.png");
        level1 = new Texture("newlevel1.png");
        level2 = new Texture("level2.png");
        level3 = new Texture("levels.png");
        level4 = new Texture("levels.png");
        level5 = new Texture("levels.png");
        level6 = new Texture("levels.png");
        level7 = new Texture("levels.png");
        level8 = new Texture("levels.png");
        level9 = new Texture("levels.png");

        // Initialize OrthographicCamera and FitViewport
        camera = new OrthographicCamera();
        viewport = new StretchViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        viewport.apply();

        // Set camera to look at the center of the world
        camera.position.set(VIRTUAL_WIDTH / 2f, VIRTUAL_HEIGHT / 2f, 0);
        camera.update();

        // Initialize buttons as world coordinates rectangles
        levelButtons = new Rectangle[9];
        int buttonSpacing = 5;
        int buttonsPerRow = 5;
        int buttonWidth = level1.getWidth();
        int buttonHeight = level1.getHeight();
        int startX = (int) ((VIRTUAL_WIDTH - (buttonWidth * buttonsPerRow + buttonSpacing * (buttonsPerRow - 1))) / 2);
        int startY = 400; // Adjusting to keep title at top

        for (int i = 0; i < levelButtons.length; i++) {
            int row = i / buttonsPerRow;
            int col = i % buttonsPerRow;
            levelButtons[i] = new Rectangle(
                startX + col * (buttonWidth + buttonSpacing),
                startY - row * (buttonHeight + buttonSpacing),
                buttonWidth, buttonHeight);
        }
    }

    @Override
    public void render(float delta) {
        // Clear screen with black color
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update camera and set the projection matrix for the batch
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(backgroundImage, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        float titleX = VIRTUAL_WIDTH - 630 ;
        float titleY = VIRTUAL_HEIGHT - 150;
        int titleWidth = 450;
        int titleHeight = 250;
        batch.draw(title, titleX, titleY, titleWidth, titleHeight);
        int levelButtonsWidth = 100;
        int levelButtonsHeight = 100;
        batch.draw(level1, levelButtons[0].x, levelButtons[0].y, levelButtonsWidth, levelButtonsHeight);
        batch.draw(level2, levelButtons[1].x, levelButtons[1].y, levelButtonsWidth, levelButtonsHeight);
        batch.draw(level3, levelButtons[2].x, levelButtons[2].y, levelButtonsWidth, levelButtonsHeight);
        batch.draw(level4, levelButtons[3].x, levelButtons[3].y, levelButtonsWidth, levelButtonsHeight);
        batch.draw(level5, levelButtons[4].x, levelButtons[4].y, levelButtonsWidth, levelButtonsHeight);
        batch.draw(level6, levelButtons[5].x, levelButtons[5].y, levelButtonsWidth, levelButtonsHeight);
        batch.draw(level7, levelButtons[6].x, levelButtons[6].y, levelButtonsWidth, levelButtonsHeight);
        batch.draw(level8, levelButtons[7].x, levelButtons[7].y, levelButtonsWidth, levelButtonsHeight);
        batch.draw(level9, levelButtons[8].x, levelButtons[8].y, levelButtonsWidth, levelButtonsHeight);
        batch.end();

        // Handle input in world coordinates
        if (Gdx.input.isTouched()) {
            Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPos);  // Convert screen coordinates to world coordinates

            // Check if a button was clicked
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
    public void resize(int width, int height) {
        // Update the viewport when resizing the screen
        viewport.update(width, height);
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
