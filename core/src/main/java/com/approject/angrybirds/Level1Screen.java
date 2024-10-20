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
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Level1Screen extends ScreenAdapter {
    SpriteBatch batch;
    Texture backGroundGameplayImage;
    Texture slingshot;
    Texture pauseButtonTexture;
    Texture scoreImage;
    BitmapFont scoreFont; // Font for displaying score
    AngryBirds game;
    int score; // Score variable

    // Bird object
    RedBird redBird; // Declare RedBird object

    // Scene2D stage to manage buttons
    Stage stage;
    ImageButton pauseButton;
    ImageButton optionButton1, optionButton2, optionButton3;
    boolean showOptions = false;

    public Level1Screen(AngryBirds game) {
        this.game = game;
        score = 0; // Initialize the score to 0
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        backGroundGameplayImage = new Texture("gamePlay.png");
        slingshot = new Texture("1.png");
        scoreImage = new Texture("score.png");

        // Initialize BitmapFont with default font
        scoreFont = new BitmapFont(); // You can also use a custom font here if you want
        scoreFont.getData().setScale(2); // Set the scale of the font for better visibility

        // Create RedBird object and set its initial position
        redBird = new RedBird(batch, new Vector2(200, 200)); // Position at (200, 200)

        // Set up the stage and add input processor for button handling
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Load pause button texture and create an ImageButton
        pauseButtonTexture = new Texture("pausebutton.png");
        Skin skin = new Skin();
        skin.add("pauseButton", pauseButtonTexture);
        pauseButton = new ImageButton(new ImageButton.ImageButtonStyle());
        pauseButton.getStyle().imageUp = skin.getDrawable("pauseButton");

        // Set position and size for the pause button
        pauseButton.setSize(80, 80); // Circular size
        pauseButton.setPosition(10, Gdx.graphics.getHeight() - 90); // Top-left position

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
        optionButton1.setPosition(10, Gdx.graphics.getHeight() - 90);
        optionButton2.setPosition(10, Gdx.graphics.getHeight() - 200);
        optionButton3.setPosition(10, Gdx.graphics.getHeight() - 300);

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
        button.setSize(80, 80); // Set button size
        return button;
    }

    private void addOptionButtonListeners() {
        optionButton1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Handle option button 1 click
                System.out.println("Option 1 clicked");
                hideOptionButtons();
            }
        });

        optionButton2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Handle option button 2 click
                System.out.println("Option 2 clicked");
            }
        });

        optionButton3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Handle option button 3 click
                System.out.println("Option 3 clicked");
            }
        });
    }

    private void hideOptionButtons() {
        // Hide the option buttons and set the visibility of pause to default state
        optionButton1.setVisible(false);
        optionButton2.setVisible(false);
        optionButton3.setVisible(false);
        showOptions = false;  // Reset the state of showOptions
    }

    private void toggleOptionButtons() {
        // Toggle visibility of the option buttons
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
        batch.draw(backGroundGameplayImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        int slingshotWidth = 100;
        int slingshotHeight = 150;
        int slingshotX = 250;
        int slingshotY = 145;
        batch.draw(slingshot, slingshotX, slingshotY, slingshotWidth, slingshotHeight);

        // Draw the red bird on the screen
        redBird.render(); // Render the RedBird

        // Set score image to the top-right corner
        int scoreImageWidth = 90;
        int scoreImageHeight = 45;
        int scoreImageX = Gdx.graphics.getWidth() - scoreImageWidth - 10; // 10px padding from the right
        int scoreImageY = Gdx.graphics.getHeight() - scoreImageHeight - 10; // 10px padding from the top
        batch.draw(scoreImage, scoreImageX, scoreImageY, scoreImageWidth, scoreImageHeight);

        // Draw the score below the score image
        String scoreText = "" + score;
        scoreFont.draw(batch, scoreText, scoreImageX, scoreImageY - 10); // 10px padding below the image

        batch.end();

        // Draw buttons and handle input
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
        backGroundGameplayImage.dispose();
        slingshot.dispose();
        pauseButtonTexture.dispose();
        stage.dispose();
        scoreImage.dispose();
        scoreFont.dispose(); // Dispose of the font
        redBird.dispose();  // Dispose of the RedBird
    }
}
