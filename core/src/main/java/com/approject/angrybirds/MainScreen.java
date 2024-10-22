package com.approject.angrybirds;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Cursor;
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
    Texture newGame, newGameHover;
    Texture loadGame, loadGameHover;
    Texture settings, settingsHover;
    Texture exit, exitHover;
    Texture title;
    AngryBirds game;
    Rectangle newGameButton, loadGameButton, settingsButton, exitButton;

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
        newGameHover = new Texture("newGameHover.png");
        loadGame = new Texture("loadGame.png");
        loadGameHover = new Texture("loadGameHover.png");
        settings = new Texture("settings.png");
        settingsHover = new Texture("settingsHover.png");
        exit = new Texture("exit.png");
        exitHover = new Texture("exitHover.png");
        title = new Texture("title.png");

        camera = new OrthographicCamera();
        viewport = new StretchViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        camera.position.set(VIRTUAL_WIDTH / 2f, VIRTUAL_HEIGHT / 2f, 0);
        camera.update();

        int buttonSpacing = 15;
        float buttonWidth = newGame.getWidth() * 0.75f;
        float buttonHeight = newGame.getHeight() * 0.75f;
        float centerX = 300;

        newGameButton = new Rectangle(centerX, 350, buttonWidth, buttonHeight);
        loadGameButton = new Rectangle(centerX, newGameButton.y - buttonHeight - buttonSpacing, buttonWidth, buttonHeight);
        settingsButton = new Rectangle(centerX, loadGameButton.y - buttonHeight - buttonSpacing, buttonWidth, buttonHeight);
        exitButton = new Rectangle(centerX, settingsButton.y - buttonHeight - buttonSpacing, buttonWidth, buttonHeight);
    }

    @Override
    public void render(float delta) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(backgroundImage, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

        float titleX = VIRTUAL_WIDTH - 630;
        float titleY = VIRTUAL_HEIGHT - 150;
        int titleWidth = 450;
        int titleHeight = 250;
        batch.draw(title, titleX, titleY, titleWidth, titleHeight);

        Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        viewport.unproject(touchPos);  // Convert screen coordinates to world coordinates

        boolean isHovering = false;
        // Draw the buttons and apply hover effect
        if (newGameButton.contains(touchPos.x, touchPos.y)) {
            batch.draw(newGameHover, newGameButton.x, newGameButton.y, 180, 60);
            isHovering = true;
        } else {
            batch.draw(newGame, newGameButton.x, newGameButton.y, 180, 60);
            isHovering = false;
        }

        if (loadGameButton.contains(touchPos.x, touchPos.y)) {
            batch.draw(loadGameHover, loadGameButton.x, loadGameButton.y, 180, 60);
            isHovering = true;
        } else {
            batch.draw(loadGame, loadGameButton.x, loadGameButton.y, 180, 60);
            isHovering = false;
        }

        if (settingsButton.contains(touchPos.x, touchPos.y)) {
            batch.draw(settingsHover, settingsButton.x, settingsButton.y, 180, 60);
            isHovering = true;
        } else {
            batch.draw(settings, settingsButton.x, settingsButton.y, 180, 60);
            isHovering = false;
        }

        if (exitButton.contains(touchPos.x, touchPos.y)) {
            batch.draw(exitHover, exitButton.x, exitButton.y, 180, 60);
            isHovering = true;
        } else {
            batch.draw(exit, exitButton.x, exitButton.y, 180, 60);
            isHovering = false;
        }

        batch.end();

        if (Gdx.input.isTouched()) {
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
        if(isHovering){
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
        }else{
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
        }

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        batch.dispose();
        backgroundImage.dispose();
        newGame.dispose();
        newGameHover.dispose();
        loadGame.dispose();
        loadGameHover.dispose();
        settings.dispose();
        settingsHover.dispose();
        exit.dispose();
        exitHover.dispose();
        title.dispose();
    }
}
