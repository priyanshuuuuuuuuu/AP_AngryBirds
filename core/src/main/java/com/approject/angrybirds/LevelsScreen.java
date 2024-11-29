package com.approject.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class LevelsScreen extends ScreenAdapter {
    SpriteBatch batch;
    AngryBirds game;
    Texture backgroundImage;
    Texture title;
    Texture level1, level1Hover;
    Texture level2, level2Hover;
    Texture level3, level3Hover;
    Texture level4;
    Texture level5;
    Texture level6;
    Texture level7;
    Texture level8;
    Texture level9;
    Texture level10;
    Texture level11;
    Texture level12;
    Texture level13;
    Texture level14;
    Texture level15;
    Texture extraImage1;
    Texture extraImage2;
    Rectangle[] levelButtons;
    private Rectangle backButtonBounds;
    private GameState  gameState;



    OrthographicCamera camera;
    Viewport viewport;
    private Texture backButton;

    // Virtual width and height for the camera
    private static final float VIRTUAL_WIDTH = 1920;
    private static final float VIRTUAL_HEIGHT = 1080;

    public LevelsScreen(AngryBirds game) {
        this.game = game;
        this.gameState = gameState;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        backgroundImage = new Texture("v.png");
        title = new Texture("selectLevel.png");
        level1 = new Texture("newlevel1.png");
        level1Hover = new Texture("level1Hover.png");
        level2 = new Texture("2.png");
        level2Hover = new Texture("level2Hover.png");
        level3 = new Texture("3rd.png");
        level3Hover = new Texture("level3Hover.png");
        level4 = new Texture("lock.png");
        level5 = new Texture("lock.png");
        level6 = new Texture("lock.png");
        level7 = new Texture("lock.png");
        level8 = new Texture("lock.png");
        level9 = new Texture("lock.png");
        level10 = new Texture("lock.png");
        level11 = new Texture("lock.png");
        level12 = new Texture("lock.png");
        level13 = new Texture("lock.png");
        level14 = new Texture("lock.png");
        level15 = new Texture("lock.png");
        extraImage1 = new Texture("red.png");
        extraImage2 = new Texture("piggy.png");
        backButton = new Texture("backButton.png");
        float backButtonWidth = 100;
        float backButtonHeight = 100;
        float backButtonX = 20;
        float backButtonY = VIRTUAL_HEIGHT - backButtonHeight - 20;  // Offset from the top edge
        backButtonBounds = new Rectangle(backButtonX, backButtonY, backButtonWidth, backButtonHeight);


        // Initialize OrthographicCamera and FitViewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        viewport.apply();

        // Set camera to look at the center of the world
        camera.position.set(VIRTUAL_WIDTH / 2f, VIRTUAL_HEIGHT / 2f, 0);
        camera.update();
        MusicControl.stopScoreMusic();
        MusicControl.playBackgroundMusic();
        MusicControl.stopSuccessMusic();
        // Initialize buttons as world coordinates rectangles
        levelButtons = new Rectangle[15];

        int buttonSpacing = 55;  // Spacing between buttons
        int buttonWidth = 180;    // Width of the level button
        int buttonHeight = 180;  // Height of the level button

        // Calculate starting position (centering the grid)
        int totalGridWidth = 5 * buttonWidth + 4 * buttonSpacing; // 5 buttons per row, 4 spacings
        int startX = (int) (VIRTUAL_WIDTH - totalGridWidth) / 2; // Start X centered
        int startY = 550; // Start Y for the first row (adjust as needed)

        // Create 5x5 grid for level buttons
        for (int i = 0; i < levelButtons.length; i++) {
            int row = i / 5;  // 5 buttons per row
            int col = i % 5;  // Column in the row
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
        // Draw background
        batch.draw(backgroundImage, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        batch.draw(backButton, backButtonBounds.x, backButtonBounds.y, backButtonBounds.width, backButtonBounds.height);

        if (Gdx.input.justTouched()) {
            Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPos);

            if (backButtonBounds.contains(touchPos.x, touchPos.y)) {
                game.setScreen(new MainScreen(game));  // Navigate back to the Settings screen
            }
        }
        // Draw title image at the top
        float titleX = 320;  // Center the title horizontally
        float titleY = 740;       // Position title below the top edge
        int titleWidth = 1350;
        int titleHeight = 350;
        batch.draw(title, titleX, titleY, titleWidth, titleHeight);
        Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        viewport.unproject(touchPos);
        boolean isHovering = false;
        // Draw the level buttons
        if(levelButtons[0].contains(touchPos.x, touchPos.y)) {
            batch.draw(level1Hover, levelButtons[0].x, levelButtons[0].y, levelButtons[0].width, levelButtons[0].height);
            isHovering = true;
        }else{
            batch.draw(level1, levelButtons[0].x, levelButtons[0].y, levelButtons[0].width, levelButtons[0].height);
        }

        if(levelButtons[1].contains(touchPos.x, touchPos.y)) {
            batch.draw(level2Hover, levelButtons[1].x, levelButtons[1].y, levelButtons[1].width, levelButtons[1].height);
            isHovering = true;
        }else{
            batch.draw(level2, levelButtons[1].x, levelButtons[1].y, levelButtons[1].width, levelButtons[1].height);
        }

        if(levelButtons[2].contains(touchPos.x, touchPos.y)) {
            batch.draw(level3Hover, levelButtons[2].x, levelButtons[2].y, levelButtons[2].width, levelButtons[2].height);
            isHovering = true;
        }else{
            batch.draw(level3, levelButtons[2].x, levelButtons[2].y, levelButtons[2].width, levelButtons[2].height);
        }
        batch.draw(level4, levelButtons[3].x, levelButtons[3].y, levelButtons[3].width, levelButtons[3].height);
        batch.draw(level5, levelButtons[4].x, levelButtons[4].y, levelButtons[4].width, levelButtons[4].height);
        batch.draw(level6, levelButtons[5].x, levelButtons[5].y, levelButtons[5].width, levelButtons[5].height);
        batch.draw(level7, levelButtons[6].x, levelButtons[6].y, levelButtons[6].width, levelButtons[6].height);
        batch.draw(level8, levelButtons[7].x, levelButtons[7].y, levelButtons[7].width, levelButtons[7].height);
        batch.draw(level9, levelButtons[8].x, levelButtons[8].y, levelButtons[8].width, levelButtons[8].height);
        batch.draw(level9, levelButtons[9].x, levelButtons[9].y, levelButtons[9].width, levelButtons[9].height);
        batch.draw(level9, levelButtons[10].x, levelButtons[10].y, levelButtons[10].width, levelButtons[10].height);
        batch.draw(level9, levelButtons[11].x, levelButtons[11].y, levelButtons[11].width, levelButtons[11].height);
        batch.draw(level9, levelButtons[12].x, levelButtons[12].y, levelButtons[12].width, levelButtons[12].height);
        batch.draw(level9, levelButtons[13].x, levelButtons[13].y, levelButtons[13].width, levelButtons[13].height);
        batch.draw(level9, levelButtons[14].x, levelButtons[14].y, levelButtons[14].width, levelButtons[14].height);

        // Draw extraImage1 at bottom-right corner
        int extraImage1Width = 500;
        int extraImage1Height = 700;
        batch.draw(extraImage1, 1500, 0, extraImage1Width, extraImage1Height);

        // Draw extraImage2 at bottom-left corner
        int extraImage2Width = 300;
        int extraImage2Height = 300;
        batch.draw(extraImage2, 0, 60, extraImage2Width, extraImage2Height);

        batch.end();

        // Handle input in world coordinates
        if (Gdx.input.isTouched()) {
             // Convert screen coordinates to world coordinates

            // Check if a button was clicked
            if (levelButtons[0].contains(touchPos.x, touchPos.y)) {
                game.setScreen(new Level1LoadingScreen(game, gameState));
            } else if (levelButtons[1].contains(touchPos.x, touchPos.y)) {
                game.setScreen(new Level2LoadingScreen(game, gameState));
//                 game.setScreen(new Level2Screen(game));
            } else if (levelButtons[2].contains(touchPos.x, touchPos.y)) {
                 game.setScreen(new Level3LoadingScreen(game, gameState));
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
        level6.dispose();
        level7.dispose();
        level8.dispose();
        level9.dispose();
        level10.dispose();
        level11.dispose();
        level12.dispose();
        level13.dispose();
        level14.dispose();
        level15.dispose();
    }
}
