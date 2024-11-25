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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class LoadGameScreen extends ScreenAdapter {
    SpriteBatch batch;
    AngryBirds game;
    Texture backgroundImage;
    Texture level1, level1Hover;
    Texture level2, level2Hover;
    Texture level3, level3Hover;
    Rectangle[] levelButtons;
    private Rectangle backButtonBounds;
    private GameState gameState;


    OrthographicCamera camera;
    Viewport viewport;
    private Texture backButton;

    // Virtual width and height for the camera
    private static final float VIRTUAL_WIDTH = 1920;
    private static final float VIRTUAL_HEIGHT = 1080;

    public LoadGameScreen(AngryBirds game, GameState gameState) {
        this.game = game;
        this.gameState = gameState;
    }

    private void loadGame() {
        File saveFile = new File("savegame.ser");
        if (!saveFile.exists() || !saveFile.canRead()) {
            System.out.println("Save file does not exist or is not readable!");
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveFile))) {
            gameState = (GameState) ois.readObject(); // Deserialize the game state
            System.out.println("Game loaded successfully!");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Failed to load game!");
        }
    }
    @Override
    public void show() {
        batch = new SpriteBatch();
        backgroundImage = new Texture("Production.png");
        level1 = new Texture("newlevel1.png");
        level1Hover = new Texture("level1Hover.png");
        level2 = new Texture("2.png");
        level2Hover = new Texture("level2Hover.png");
        level3 = new Texture("3rd.png");
        level3Hover = new Texture("level3Hover.png");

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
                game.setScreen(new MainScreen(game, gameState));  // Navigate back to the Settings screen
            }
        }

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



        batch.end();

        // Handle input in world coordinates
        if (Gdx.input.isTouched()) {
            // Convert screen coordinates to world coordinates
            // Check if a button was clicked
            if (levelButtons[0].contains(touchPos.x, touchPos.y)) {
                loadGame();
//                game.setScreen(new Level1LoadingScreen(game, gameState));
            } else if (levelButtons[1].contains(touchPos.x, touchPos.y)) {
                loadGame();
            } else if (levelButtons[2].contains(touchPos.x, touchPos.y)) {
                loadGame();
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
        level1.dispose();
        level2.dispose();
        level3.dispose();
    }
}
