package com.approject.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Settings extends ScreenAdapter {
    AngryBirds game;
    SpriteBatch batch;
    Texture backgroundImage;
    Texture centerImage;
    Texture crossButton;
    Texture settingsButton;
    Texture musicButton;
    Texture noMusicButton;
    Texture ibutton;
    Texture termsAndPrivacyButton;

    Rectangle crossButtonBounds;
    Rectangle musicButtonBounds;// For handling the music button click
    Rectangle iButtonBounds;

    boolean isMusicOn = true;  // Track if the music is currently on or off

    public Settings(AngryBirds game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        backgroundImage = new Texture("main.png");  // Fullscreen background
        centerImage = new Texture("settingsImage.png");  // Center image
        crossButton = new Texture("crossButton.png");
        settingsButton = new Texture("settingsButton.png");
        musicButton = new Texture("musicbutton.png");
        noMusicButton = new Texture("noMusicbutton.png");
        ibutton = new Texture("ibutton.png");
        termsAndPrivacyButton = new Texture("termsAndPrivacy.png");

        // Define the rectangle area for crossButton
        int crossButtonWidth = 80;
        int crossButtonHeight = 80;
        int crossButtonX = 1430;
        int crossButtonY = 780;
        crossButtonBounds = new Rectangle(crossButtonX, crossButtonY, crossButtonWidth, crossButtonHeight);

        // Define the rectangle area for the music button (this will be used for both music and noMusic buttons)
        int musicButtonWidth = 70;
        int musicButtonHeight = 70;
        int musicButtonX = 300;
        int musicButtonY = 100;
        musicButtonBounds = new Rectangle(musicButtonX, musicButtonY, musicButtonWidth, musicButtonHeight);

        int iButtonWidth = 70;
        int iButtonHeight = 70;
        int iButtonX = 500;
        int iButtonY = 100;
        iButtonBounds = new Rectangle(iButtonX, iButtonY, iButtonWidth, iButtonHeight);
    }

    @Override
    public void render(float delta) {
        batch.begin();

        // Draw the background to cover the entire screen
        batch.draw(backgroundImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Center the centerImage on the screen
        int centerImageWidth = 1100;  // Set your desired width for the center image
        int centerImageHeight = 700;  // Set your desired height for the center image
        int centerImageX = (Gdx.graphics.getWidth() - centerImageWidth) / 2;
        int centerImageY = (Gdx.graphics.getHeight() - centerImageHeight) / 2;
        batch.draw(centerImage, centerImageX, centerImageY, centerImageWidth, centerImageHeight);

        // Draw the crossButton
        int crossButtonWidth = 80;
        int crossButtonHeight = 80;
        int crossButtonX = 1430;
        int crossButtonY = 780;
        batch.draw(crossButton, crossButtonX, crossButtonY, crossButtonWidth, crossButtonHeight);

        // Draw the settingsButton
        int settingsButtonWidth = 70;
        int settingsButtonHeight = 70;
        int settingsButtonX = 200;
        int settingsButtonY = 100;
        batch.draw(settingsButton, settingsButtonX, settingsButtonY, settingsButtonWidth, settingsButtonHeight);

        // Draw either the musicButton or noMusicButton depending on the state
        if (isMusicOn) {
            batch.draw(musicButton, musicButtonBounds.x, musicButtonBounds.y, musicButtonBounds.width, musicButtonBounds.height);
        } else {
            batch.draw(noMusicButton, musicButtonBounds.x, musicButtonBounds.y, musicButtonBounds.width, musicButtonBounds.height);
        }

        // Draw other buttons
        int noMusicButtonWidth = 70;
        int noMusicButtonHeight = 70;
        int ibuttonWidth = 70;
        int ibuttonHeight = 70;
        int iButtonX = 500;
        int iButtonY = 100;
        batch.draw(ibutton, iButtonX, iButtonY, ibuttonWidth, ibuttonHeight);

        int termsAndPrivacyButtonWidth = 100;
        int termsAndPrivacyButtonHeight = 100;
        int termsAndPrivacyButtonX = 1000;
        int termsAndPrivacyButtonY = 100;
        batch.draw(termsAndPrivacyButton, termsAndPrivacyButtonX, termsAndPrivacyButtonY, termsAndPrivacyButtonWidth, termsAndPrivacyButtonHeight);

        batch.end();

        // Handle input to check if the crossButton was clicked
        if (Gdx.input.isTouched()) {
            Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY()); // Get the touch position

            // If crossButton is clicked, go back to the MainScreen
            if (crossButtonBounds.contains(touchPos.x, touchPos.y)) {
                game.setScreen(new MainScreen(game));
            }

            // If musicButton (or noMusicButton) is clicked, toggle the music state
            if (musicButtonBounds.contains(touchPos.x, touchPos.y)) {
                isMusicOn = !isMusicOn;  // Toggle the music state
            }

            if(iButtonBounds.contains(touchPos.x, touchPos.y)) {
                game.setScreen(new Credits(game));
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        centerImage.dispose();
        crossButton.dispose();
        settingsButton.dispose();
        musicButton.dispose();
        noMusicButton.dispose();
        termsAndPrivacyButton.dispose();
    }
}
