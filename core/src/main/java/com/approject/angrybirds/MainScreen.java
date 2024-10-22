package com.approject.angrybirds;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainScreen extends ScreenAdapter {
    SpriteBatch batch;
    Texture backgroundImage;
    Texture newGame;
    Texture loadGame;
    Texture settings;
    Texture exit;
    Texture title;
    AngryBirds game;
    Rectangle newGameButton, loadGameButton, settingsButton, exitButton;

    // Constants for the virtual size of the game world
    private static final float VIRTUAL_WIDTH = 800;
    private static final float VIRTUAL_HEIGHT = 600;

    OrthographicCamera camera;
    Viewport viewport;

    public MainScreen(AngryBirds game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        // Load textures
        backgroundImage = new Texture("finalBg.png");
        newGame = new Texture("newGame.png");
        loadGame = new Texture("loadGame.png");
        settings = new Texture("settings.png");
        exit = new Texture("exit.png");
        title = new Texture("title.png");

        // Create the camera and StretchViewport
        camera = new OrthographicCamera();
        viewport = new StretchViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        camera.position.set(VIRTUAL_WIDTH / 2f, VIRTUAL_HEIGHT / 2f, 0);
        camera.update();

        // Define button positions and reduce their sizes
        int buttonSpacing = 15;
        float buttonWidth = newGame.getWidth() * 0.75f;  // Reduce button size to 75%
        float buttonHeight = newGame.getHeight() * 0.75f;

        float centerX = 300;

        // Define button rectangles in world coordinates (centered and spaced vertically)
        newGameButton = new Rectangle(centerX, 350, buttonWidth, buttonHeight);  // Lowered for better layout
        loadGameButton = new Rectangle(centerX, newGameButton.y - buttonHeight - buttonSpacing, buttonWidth, buttonHeight);
        settingsButton = new Rectangle(centerX, loadGameButton.y - buttonHeight - buttonSpacing, buttonWidth, buttonHeight);
        exitButton = new Rectangle(centerX, settingsButton.y - buttonHeight - buttonSpacing, buttonWidth, buttonHeight);
    }

    @Override
    public void render(float delta) {
        // Update the camera and viewport
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        // Clear the screen and set background color
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Begin drawing
        batch.begin();
        batch.draw(backgroundImage, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

        // Keep the original size for the title
        float titleX = VIRTUAL_WIDTH - 630 ;
        float titleY = VIRTUAL_HEIGHT - 150;
        int titleWidth = 450;
        int titleHeight = 250;
        batch.draw(title, titleX, titleY, titleWidth, titleHeight);  // Draw title without resizing

        // Draw buttons with their updated positions and sizes
        batch.draw(newGame, newGameButton.x, newGameButton.y, 180,60);
        batch.draw(loadGame, loadGameButton.x, loadGameButton.y, 180,60);
        batch.draw(settings, settingsButton.x, settingsButton.y, 180,60);
        batch.draw(exit, exitButton.x, exitButton.y, 180,60);
        batch.end();

        if (Gdx.input.isTouched()) {
            // Log the touch coordinates
            System.out.println("Touch X: " + Gdx.input.getX() + ", Touch Y: " + Gdx.input.getY());

            Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPos);  // Convert screen coordinates to world coordinates

            // Check which button was clicked...
            if (newGameButton.contains(touchPos.x, touchPos.y)) {
                game.setScreen(new LevelsScreen(game));
            } else if (loadGameButton.contains(touchPos.x, touchPos.y)) {
                System.out.println("Load Game clicked!");
            } else if (settingsButton.contains(touchPos.x, touchPos.y)) {
                game.setScreen(new Settings(game));
            } else if (exitButton.contains(touchPos.x, touchPos.y)) {
                Gdx.app.exit();
            }
        }
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
        newGame.dispose();
        loadGame.dispose();
        settings.dispose();
        exit.dispose();
        title.dispose();
    }
}
