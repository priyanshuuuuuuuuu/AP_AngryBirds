package com.approject.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.utils.Array;
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
    private Body body;
    private World world;
    private Body groundBody;
    private MouseJoint mouseJoint;
    private Vector2 slingStartPosition;
    private Vector2 dragPosition;
    private boolean isDragging = false;
    private Texture trajectoryPointTexture;
    private Box2DDebugRenderer debugRenderer;




    // Constants for virtual width and height
    private static final float VIRTUAL_WIDTH = 1920;
    private static final float VIRTUAL_HEIGHT = 1080;

    public Level1Screen(AngryBirds game) {
        this.game = game;
        isPaused = false;  // Game starts in playing state
    }

    // Add screen boundaries
    private void createScreenBoundaries() {
        float screenWidth = VIRTUAL_WIDTH / 100f; // Convert to Box2D units
        float screenHeight = VIRTUAL_HEIGHT / 100f;

        // Left boundary
        BodyDef leftBodyDef = new BodyDef();
        leftBodyDef.type = BodyDef.BodyType.StaticBody;
        leftBodyDef.position.set(0, 0);
        Body leftBoundary = world.createBody(leftBodyDef);

        EdgeShape leftEdge = new EdgeShape();
        leftEdge.set(new Vector2(0, 0), new Vector2(0, screenHeight));
        leftBoundary.createFixture(leftEdge, 0);
        leftEdge.dispose();

        // Right boundary
        BodyDef rightBodyDef = new BodyDef();
        rightBodyDef.type = BodyDef.BodyType.StaticBody;
        rightBodyDef.position.set(screenWidth, 0);
        Body rightBoundary = world.createBody(rightBodyDef);

        EdgeShape rightEdge = new EdgeShape();
        rightEdge.set(new Vector2(0, 0), new Vector2(0, screenHeight));
        rightBoundary.createFixture(rightEdge, 0);
        rightEdge.dispose();

        // Top boundary
        BodyDef topBodyDef = new BodyDef();
        topBodyDef.type = BodyDef.BodyType.StaticBody;
        topBodyDef.position.set(0, screenHeight);
        Body topBoundary = world.createBody(topBodyDef);

        EdgeShape topEdge = new EdgeShape();
        topEdge.set(new Vector2(0, 0), new Vector2(screenWidth, 0));
        topBoundary.createFixture(topEdge, 0);
        topEdge.dispose();
    }



    @Override
    public void show() {
        debugRenderer = new Box2DDebugRenderer();
        batch = new SpriteBatch();
        background = new Texture("gamePlay.png");
        pauseButton = new Texture("pauseButton.png");
        playButton = new Texture("play.png");
        scoreTextImage = new Texture("score.png");
        MusicControl.stopScoreMusic();
        MusicControl.stopBackgroundMusic();
        MusicControl.playGameplayMusic();


        world = new World(new Vector2(0, -9.8f), true);
        createScreenBoundaries();
        slingStartPosition = new Vector2(230 / 100f, 147/ 100f);
        dragPosition = new Vector2(slingStartPosition);
        trajectoryPointTexture = new Texture("dots.png");


        // Initialize RedBird objects with their positions
        redBird1 = new RedBird(batch, new Vector2(180 / 100f, 203 / 100f), world); // Position in Box2D units
        redBird2 = new RedBird(batch, new Vector2(280 / 100f, 300/ 100f), world);

        verticalWoodBlock1 = new VerticalWoodBlock(batch, new Vector2(1525/100f, 250/100f), world);
        verticalWoodBlock2 = new VerticalWoodBlock(batch, new Vector2(1700/100f, 250/100f), world);
        horizontalWoodBlock1 = new HorizontalWoodBlock(batch , new Vector2(1600/100f, 350/100f), world);
        stoneBlock1 = new MediumSizedStoneBlock(batch , new Vector2(1530/100f, 158/100f), world);
        stoneBlock2 = new MediumSizedStoneBlock(batch , new Vector2(1610/100f, 158/100f),world);
        stoneBlock3 = new MediumSizedStoneBlock(batch , new Vector2(1690/100f, 158/100f),world);
        triangleGlassBlock = new TriangleGlassBlock(batch, new Vector2(1617 /100f, 395/100f), world);
        // Initialize SlingShot object and load its texture
        slingShot = new SlingShot(batch, 230, 147);
        slingShot.show();  // Load the texture for SlingShot
        yellowBird = new YellowBird(batch, new Vector2(80 / 100f, 203 / 100f), world);
        minionPig = new MinionPigs(batch, new Vector2(1620 / 100f, 223/ 100f), world);


        // Define ground body
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        groundBodyDef.position.set(0, 150 / 100f); // Set Y position for the ground (adjust as needed)

        groundBody = world.createBody(groundBodyDef);


        // Define the ground shape as an edge
        EdgeShape groundShape = new EdgeShape();
        groundShape.set(new Vector2(0, 0), new Vector2(VIRTUAL_WIDTH / 100f, 0)); // Edge from left to right

        // Create fixture for ground
        FixtureDef groundFixture = new FixtureDef();
        groundFixture.shape = groundShape;
        groundFixture.friction = 0.5f; // Add friction for realism
        groundFixture.restitution = 0f; // Prevent bouncing

        groundBody.createFixture(groundFixture);
        groundShape.dispose();

        // Create a viewport with 1920x1080 dimensions
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        viewport.apply(true);



        // Define the bounds of the pause button (used for click detection)
        pauseButtonBounds = new Rectangle(20, 950, 100, 100);
    }
    private void renderTrajectory(Vector2 initialPosition, Vector2 launchVelocity) {
        float timeStep = 0.1f;
        float totalTime = 2f;
        Vector2 gravity = world.getGravity();


        for (float t = 0; t < totalTime; t += timeStep) {
            float x = initialPosition.x + launchVelocity.x * t;
            float y = initialPosition.y + launchVelocity.y * t + 0.5f * gravity.y * t * t;

            // Only draw points above the ground level
            if (y > 0) {
                batch.draw(trajectoryPointTexture, x * 100, y * 100, 10, 10);
            }
        }

    }


    private Vector2 calculateLaunchVelocity() {
        Vector2 launchVector = new Vector2(dragPosition).sub(slingStartPosition); // Direction and distance of drag
        float power = launchVector.len() * 2.5f; // Scale power (adjust multiplier as needed)
        launchVector.nor().scl(power);
        float maxDragDistance = 2.0f; // Limit drag distance (adjust as needed)

        return launchVector;
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.step(delta, 6, 2);
        viewport.apply();

        //setting the Trajectory
        Array<Body> bodies = new Array<>();
        world.getBodies(bodies);
        for (Body body : bodies) {
            if (body.getType() == BodyDef.BodyType.StaticBody) {
                body.applyLinearImpulse(new Vector2(MathUtils.random(-0.2f, 0.2f), 0), body.getWorldCenter(), true);
                body.setAngularDamping(5.0f);
            }
        }
        // Set the SpriteBatch to draw within the viewport's bounds
        batch.setProjectionMatrix(viewport.getCamera().combined);
        debugRenderer.render(world, viewport.getCamera().combined);

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
        renderTrajectory(redBird2.getBody().getPosition(), calculateLaunchVelocity());

        // Draw the SlingShot on the screen
        slingShot.render();
        yellowBird.render();
        minionPig.render();

        if (isDragging) {
            Vector2 launchVelocity = calculateLaunchVelocity();
            renderTrajectory(redBird1.getBody().getPosition(), launchVelocity);
        }
        if (isPaused) {
            batch.draw(playButton, pauseButtonBounds.x, pauseButtonBounds.y, pauseButtonBounds.width, pauseButtonBounds.height);
        } else {
            batch.draw(pauseButton, pauseButtonBounds.x, pauseButtonBounds.y, pauseButtonBounds.width, pauseButtonBounds.height);
        }
        batch.end();
        handleInput();
        Gdx.app.log("Debug", "RedBird1 Position: " + redBird1.getBody().getPosition());
        Gdx.app.log("Debug", "VerticalWoodBlock1 Position: " + verticalWoodBlock1.getBody().getPosition());
        Gdx.app.log("Debug", "MinionPig Position: " + minionPig.getBody().getPosition());


//
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

    private void handleInput() {
        if (Gdx.input.isTouched()) {
            Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPos);

            if (!isDragging) {
                // Start dragging if touch is within bird's bounds
                if (redBird2.getBounds().contains(touchPos.x, touchPos.y)) {
                    isDragging = true;
                    dragPosition.set(touchPos);
                }
            } else {
                // Update drag position while dragging
                dragPosition.set(touchPos);
            }
        } else {
            // Release and launch when touch is lifted
            if (isDragging) {
                isDragging = false;
                Vector2 launchVelocity = calculateLaunchVelocity();
                redBird2.getBody().setLinearVelocity(launchVelocity);
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
        debugRenderer.dispose();
        MusicControl.stopBackgroundMusic();
    }
}
