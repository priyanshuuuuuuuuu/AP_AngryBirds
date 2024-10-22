package com.approject.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class Level1Screen extends ScreenAdapter {
    SpriteBatch batch;
    Texture backGroundGameplayImage;
    Texture slingshot;
    Texture pauseButtonTexture;
    Texture scoreImage;
    BitmapFont scoreFont;
    AngryBirds game;
    int score;

    // Bird object
    RedBird redBird;

    // Scene2D stage to manage buttons
    Stage stage;
    ImageButton pauseButton;
    ImageButton optionButton1, optionButton2, optionButton3;
    boolean showOptions = false;

    private static final float VIRTUAL_WIDTH = 800;
    private static final float VIRTUAL_HEIGHT = 600;

    public Level1Screen(AngryBirds game) {
        this.game = game;
        score = 0;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        backGroundGameplayImage = new Texture("gamePlay.png");
        slingshot = new Texture("sling.png");
        scoreImage = new Texture("score.png");

        // Initialize BitmapFont with default font
        scoreFont = new BitmapFont();
        scoreFont.getData().setScale(2);

        // Create RedBird object and set its initial position
        redBird = new RedBird(batch, new Vector2(200, 50));

        // Set up the stage with ExtendViewport (800x600)
        stage = new Stage(new StretchViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        // Load pause button texture and create an ImageButton
        pauseButtonTexture = new Texture("pausebutton.png");
        Skin skin = new Skin();
        skin.add("pauseButton", pauseButtonTexture);
        pauseButton = new ImageButton(new ImageButton.ImageButtonStyle());
        pauseButton.getStyle().imageUp = skin.getDrawable("pauseButton");

        // Set position and size for the pause button
        pauseButton.setSize(40, 40); // Circular size
        pauseButton.setPosition(10, 550); // Adjust position relative to viewport

        // Add the pause button to the stage
        stage.addActor(pauseButton);

        // Add listener for the pause button click
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Toggle visibility of option buttons when pause is clicked
                showOptions = !showOptions;
                toggleOptionButtons();
            }
        });

        // Set up additional buttons (option buttons) to show when pause is clicked
        createOptionButtons();
    }

    private void createOptionButtons() {
        Skin skin = new Skin();

        // Example option button textures
        optionButton1 = createButton("play.png", skin);
        optionButton2 = createButton("music.png", skin);
        optionButton3 = createButton("off.png", skin);

        // Set positions for the option buttons (below the pause button)
        optionButton1.setPosition(10, 500);
        optionButton2.setPosition(10, stage.getViewport().getWorldHeight() - 140);
        optionButton3.setPosition(10, stage.getViewport().getWorldHeight() - 190);

        // Initially hide the option buttons
        optionButton1.setVisible(false);
        optionButton2.setVisible(false);
        optionButton3.setVisible(false);

        // Add option buttons to the stage
        stage.addActor(optionButton1);
        stage.addActor(optionButton2);
        stage.addActor(optionButton3);

        // Add click listeners to option buttons
        addOptionButtonListeners();
    }

    private ImageButton createButton(String texturePath, Skin skin) {
        Texture buttonTexture = new Texture(texturePath);
        skin.add(texturePath, buttonTexture);
        ImageButton button = new ImageButton(new ImageButton.ImageButtonStyle());
        button.getStyle().imageUp = skin.getDrawable(texturePath);
        button.setSize(40, 40); // Set button size
        return button;
    }

    private void addOptionButtonListeners() {
        optionButton1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Option 1 clicked");
                hideOptionButtons();
            }
        });

        optionButton2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Option 2 clicked");
            }
        });

        optionButton3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Option 3 clicked");
            }
        });
    }

    private void hideOptionButtons() {
        optionButton1.setVisible(false);
        optionButton2.setVisible(false);
        optionButton3.setVisible(false);
        showOptions = false;
    }

    private void toggleOptionButtons() {
        optionButton1.setVisible(showOptions);
        optionButton2.setVisible(showOptions);
        optionButton3.setVisible(showOptions);
    }

    @Override
    public void render(float delta) {
        // Clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Begin drawing the background and slingshot
        batch.begin();
        batch.draw(backGroundGameplayImage, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

        int slingshotWidth = 150;
        int slingshotHeight = 200;
        int slingshotX = 250;
        int slingshotY = 100;
        batch.draw(slingshot, slingshotX, slingshotY, slingshotWidth, slingshotHeight);

        // Draw the red bird on the screen
        redBird.render();

        // Set score image to the top-right corner
        int scoreImageWidth = 90;
        int scoreImageHeight = 45;
        int scoreImageX = (int) (VIRTUAL_WIDTH - scoreImageWidth - 10);
        int scoreImageY = (int) (VIRTUAL_HEIGHT - scoreImageHeight - 10);
        batch.draw(scoreImage, scoreImageX, scoreImageY, scoreImageWidth, scoreImageHeight);

        // Draw the score below the score image
        String scoreText = "" + score;
        scoreFont.draw(batch, scoreText, scoreImageX, scoreImageY - 10);

        batch.end();

        // Draw buttons and handle input
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true); // Maintain aspect ratio
    }

    @Override
    public void dispose() {
        batch.dispose();
        backGroundGameplayImage.dispose();
        slingshot.dispose();
        pauseButtonTexture.dispose();
        stage.dispose();
        scoreImage.dispose();
        scoreFont.dispose();
        redBird.dispose();
    }
}
