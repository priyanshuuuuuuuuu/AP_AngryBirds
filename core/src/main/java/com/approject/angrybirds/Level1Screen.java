package com.approject.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Level1Screen extends ScreenAdapter {
    private AngryBirds game;
    private SpriteBatch batch;
    private Texture background;
    private Viewport viewport;
    private RedBird redBird1;  // First RedBird
    private RedBird redBird2;  // Second RedBird
    private Texture pauseButton;
    private Texture playButton;
    private Rectangle pauseButtonBounds;  // For detecting click on pause/play button
    private boolean isPaused;
    private SlingShot slingShot;
    private YellowBird yellowBird;
    private MinionPigs minionPig;
    private Texture scoreTextImage;
    private WoodBlocks verticalWoodBlock1;
    private WoodBlocks verticalWoodBlock2;
    private WoodBlocks horizontalWoodBlock1;
    private StoneBlocks stoneBlock1;
    private StoneBlocks stoneBlock2;
    private StoneBlocks stoneBlock3;
    private GlassBlock triangleGlassBlock;


    // Constants for virtual width and height
    private static final float VIRTUAL_WIDTH = 1920;
    private static final float VIRTUAL_HEIGHT = 1080;

    public Level1Screen(AngryBirds game) {
        this.game = game;
        isPaused = false;  // Game starts in playing state
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        background = new Texture("gamePlay.png");
        pauseButton = new Texture("pauseButton.png");
        playButton = new Texture("play.png");
        scoreTextImage = new Texture("score.png");

        // Initialize RedBird objects with their positions
        redBird1 = new RedBird(batch, new Vector2(120, 147));
        redBird2 = new RedBird(batch, new Vector2(220, 147));
        verticalWoodBlock1 = new VerticalWoodBlock(batch, new Vector2(1700, 170));
        verticalWoodBlock2 = new VerticalWoodBlock(batch, new Vector2(1550, 170));
        horizontalWoodBlock1 = new HorizontalWoodBlock(batch , new Vector2(1545, 350));
        stoneBlock1 = new MediumSizedStoneBlock(batch , new Vector2(1500, 147));
        stoneBlock2 = new MediumSizedStoneBlock(batch , new Vector2(1584, 147));
        stoneBlock3 = new MediumSizedStoneBlock(batch , new Vector2(1668, 147));
        triangleGlassBlock = new TriangleGlassBlock(batch, new Vector2(1600, 365));

        // Initialize SlingShot object and load its texture
        slingShot = new SlingShot(batch, 300, 147);
        slingShot.show();  // Load the texture for SlingShot
        yellowBird = new YellowBird(batch, new Vector2(20, 147));
        minionPig = new MinionPigs(batch, new Vector2(1600, 190));




        // Create a viewport with 1920x1080 dimensions
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        viewport.apply(true);

        // Define the bounds of the pause button (used for click detection)
        pauseButtonBounds = new Rectangle(20, 950, 100, 100);
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();

        // Set the SpriteBatch to draw within the viewport's bounds
        batch.setProjectionMatrix(viewport.getCamera().combined);

        // Begin drawing the background and elements
        batch.begin();
        batch.draw(background, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        batch.draw(scoreTextImage, 1700, 1020, 200, 50);
        // Draw the RedBirds on the screen
        redBird1.render();
        redBird2.render();
        verticalWoodBlock1.render();
        verticalWoodBlock2.render();
        horizontalWoodBlock1.render();
        stoneBlock1.render();
        stoneBlock2.render();
        triangleGlassBlock.render();
        stoneBlock3.render();


        // Draw the SlingShot on the screen
        slingShot.render();
        yellowBird.render();
        minionPig.render();


        // Draw the pause button if the game is not paused, otherwise draw the play button
        if (isPaused) {
            batch.draw(playButton, pauseButtonBounds.x, pauseButtonBounds.y, pauseButtonBounds.width, pauseButtonBounds.height);
        } else {
            batch.draw(pauseButton, pauseButtonBounds.x, pauseButtonBounds.y, pauseButtonBounds.width, pauseButtonBounds.height);
        }
        batch.end();

        // Handle input for pause/play button
        if (Gdx.input.isTouched()) {
            Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPos);  // Convert screen coordinates to world coordinates

            // Check if the pause/play button is clicked
            if (pauseButtonBounds.contains(touchPos)) {
                if (isPaused) {
                    // If game is paused, clicking button will resume game
                    resumeGame();
                } else {
                    // If game is playing, clicking button will pause the game
                    pauseGame();
                }
            }
        }
    }

    private void pauseGame() {
        isPaused = true;
        game.setScreen(new PauseScreen(game, this));  // Switch to pause screen
    }

    private void resumeGame() {
        isPaused = false;
    }

    @Override
    public void resize(int width, int height) {
        // Update the viewport size on window resize
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        redBird1.dispose();  // Dispose the RedBird textures
        redBird2.dispose();  // Dispose the RedBird textures
        slingShot.dispose();  // Dispose the SlingShot textures
        pauseButton.dispose();
        playButton.dispose();
        scoreTextImage.dispose();
    }
}
