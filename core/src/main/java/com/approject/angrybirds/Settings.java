package com.approject.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Settings extends ScreenAdapter {
    private static final float VIRTUAL_WIDTH = 1920;
    private static final float VIRTUAL_HEIGHT = 1080;

    AngryBirds game;
    SpriteBatch batch;
    Texture backgroundImage;
    Texture centerImage;
    Texture crossButton;
    Texture musicButton;
    Texture noMusicButton;
    Texture ibutton;
    Texture termsAndPrivacyButton; // Button for Terms and Privacy
    Texture settingsButton; // New button on the left of the music button

    Rectangle crossButtonBounds;
    Rectangle musicButtonBounds;
    Rectangle iButtonBounds;
    Rectangle termsAndPrivacyButtonBounds; // Bounds for Terms and Privacy button
    Rectangle newButtonBounds; // Bounds for the new button
    private GameState gameState;

    boolean isMusicOn = true;

    OrthographicCamera camera;
    Viewport viewport;

    public Settings(AngryBirds game, GameState gameState) {
        this.game = game;
        this.gameState = gameState;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        // Initialize camera and viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        camera.position.set(VIRTUAL_WIDTH / 2f, VIRTUAL_HEIGHT / 2f, 0);
        camera.update();

        // Load textures
        backgroundImage = new Texture("main.png");
        centerImage = new Texture("settingsImage.png");
        crossButton = new Texture("crossButton.png");
        musicButton = new Texture("musicbutton.png");
        noMusicButton = new Texture("noMusicbutton.png");
        ibutton = new Texture("ibutton.png");
        termsAndPrivacyButton = new Texture("termsAndPrivacy.png");
        settingsButton = new Texture("settingsButton.png"); // Load texture for new button

        // Define button bounds with coordinates based on virtual width and height
        int buttonSize = 100;
        crossButtonBounds = new Rectangle(1430, 780, buttonSize, buttonSize);
        musicButtonBounds = new Rectangle(350, 50, buttonSize, buttonSize);
        iButtonBounds = new Rectangle(500, 50, buttonSize, buttonSize);
        termsAndPrivacyButtonBounds = new Rectangle(1500, 50, 300, 100); // Position for Terms and Privacy button
        newButtonBounds = new Rectangle(250, 50, buttonSize, buttonSize); // Position for the new button to the left of music button
    }

    @Override
    public void render(float delta) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // Draw background covering entire viewport
        batch.draw(backgroundImage, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

        // Draw center image in the middle of the screen
        int centerImageWidth = 1100;
        int centerImageHeight = 700;
        int centerImageX = (int)(VIRTUAL_WIDTH - centerImageWidth) / 2;
        int centerImageY = (int)(VIRTUAL_HEIGHT - centerImageHeight) / 2;
        batch.draw(centerImage, centerImageX, centerImageY, centerImageWidth, centerImageHeight);

        Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        viewport.unproject(touchPos);

        boolean isHovering = false;

        // Draw and handle hover for each button
        // Draw Cross (Exit) Button with hover effect
        if (crossButtonBounds.contains(touchPos.x, touchPos.y)) {
            batch.draw(crossButton, crossButtonBounds.x, crossButtonBounds.y, crossButtonBounds.width, crossButtonBounds.height);
            isHovering = true;
        } else {
            batch.draw(crossButton, crossButtonBounds.x, crossButtonBounds.y, crossButtonBounds.width, crossButtonBounds.height);
        }

        // Draw Settings (Info) Button with hover effect
        if (iButtonBounds.contains(touchPos.x, touchPos.y)) {
            batch.draw(ibutton, iButtonBounds.x, iButtonBounds.y, iButtonBounds.width, iButtonBounds.height);
            isHovering = true;
        } else {
            batch.draw(ibutton, iButtonBounds.x, iButtonBounds.y, iButtonBounds.width, iButtonBounds.height);
        }

        // Draw Music Button with hover effect
        if (musicButtonBounds.contains(touchPos.x, touchPos.y)) {
            if(Gdx.input.isTouched()){
                isMusicOn = !isMusicOn;
                if(isMusicOn){
                    MusicControl.playBackgroundMusic();
                }else{
                    MusicControl.stopBackgroundMusic();
                }
            }
            batch.draw(isMusicOn ? musicButton : noMusicButton, musicButtonBounds.x, musicButtonBounds.y, musicButtonBounds.width, musicButtonBounds.height);
            isHovering = true;
        }
        else {
            batch.draw(isMusicOn ? musicButton : noMusicButton, musicButtonBounds.x, musicButtonBounds.y, musicButtonBounds.width, musicButtonBounds.height);
        }

        // Draw New Button with hover effect
        if (newButtonBounds.contains(touchPos.x, touchPos.y)) {
            batch.draw(settingsButton, newButtonBounds.x, newButtonBounds.y, newButtonBounds.width, newButtonBounds.height);
            isHovering = true;
        } else {
            batch.draw(settingsButton, newButtonBounds.x, newButtonBounds.y, newButtonBounds.width, newButtonBounds.height);
        }

        // Draw Terms and Privacy Button with hover effect
        if (termsAndPrivacyButtonBounds.contains(touchPos.x, touchPos.y)) {
            batch.draw(termsAndPrivacyButton, termsAndPrivacyButtonBounds.x, termsAndPrivacyButtonBounds.y, termsAndPrivacyButtonBounds.width, termsAndPrivacyButtonBounds.height);
            isHovering = true;
        } else {
            batch.draw(termsAndPrivacyButton, termsAndPrivacyButtonBounds.x, termsAndPrivacyButtonBounds.y, termsAndPrivacyButtonBounds.width, termsAndPrivacyButtonBounds.height);
        }

        batch.end();

        // Handle input for button actions
        if (Gdx.input.justTouched()) {
            if (crossButtonBounds.contains(touchPos.x, touchPos.y)) {
                game.setScreen(new MainScreen(game, gameState));
            }
            if (musicButtonBounds.contains(touchPos.x, touchPos.y)) {
                isMusicOn = !isMusicOn;  // Toggle music on/off
            }
            if (iButtonBounds.contains(touchPos.x, touchPos.y)) {
                game.setScreen(new Credits(game, gameState));  // Navigate to Credits screen
            }
            if (newButtonBounds.contains(touchPos.x, touchPos.y)) {
                // Add action for new button here (e.g., navigate to another screen)
                System.out.println("New Button clicked!");
            }
            if (termsAndPrivacyButtonBounds.contains(touchPos.x, touchPos.y)) {
                // Action for Terms and Privacy button here
                System.out.println("Terms and Privacy clicked!");
            }
        }

        // Change cursor appearance
        if (isHovering) {
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
        } else {
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
        centerImage.dispose();
        crossButton.dispose();
        musicButton.dispose();
        noMusicButton.dispose();
        ibutton.dispose();
        termsAndPrivacyButton.dispose();
        settingsButton.dispose(); // Dispose of the new button texture
    }
}
