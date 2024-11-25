package com.approject.angrybirds;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.io.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.LinkedList;
//import java.util.Timer;

import static java.lang.Math.max;
import static java.lang.Math.min;


public class Level1Screen extends ScreenAdapter  {
    private AngryBirds game;
    private SpriteBatch batch;
    private int score = 0;
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
    private ShapeRenderer shapeRenderer;
    private StoneBlocks stoneBlock1;
    private StoneBlocks stoneBlock2;
    private StoneBlocks stoneBlock3;
    private GlassBlock triangleGlassBlock;
    private Body body;
    private World world;
    private Body groundBody;
    private MouseJoint mouseJoint;
    private Vector2 slingStartPosition, sitonsling;
    private Vector2 dragPosition;
    private boolean isDragging = false;
    private Texture trajectoryPointTexture;
    private Box2DDebugRenderer debugRenderer;
    private Vector2 dragStart = new Vector2();  // Start point of the drag
    private Vector2 dragEnd = new Vector2();    // End point of the drag
    private boolean dragging = false;          // To track dragging state
    private BitmapFont font;
    private final int NUM_TRAJECTORY_POINTS = 50;  // Number of points to show
    private Array<Vector2> trajectoryPoints;
    private final Vector2 gravity = new Vector2(0, -9.8f);  // Gravity vector
    private ArrayList<Bird> birdList;
    private Bird currentBird;
    private GameState gameState;
    private Texture saveButton;
    private Rectangle saveButtonBounds; // For detecting click on save button


    // Constants for virtual width and height
    private static final float VIRTUAL_WIDTH = 1920;
    private static final float VIRTUAL_HEIGHT = 1080;
    private static final float MAX_DRAG_DISTANCE = 2.0F;
    private static final float DRAG_MULTIPLIER = 5.0f;
    private final float TIME_STEP = 0.1f;
    private int currentBirdIndex = 0;


    public Level1Screen(AngryBirds game, GameState gameState) {
        this.game = game;
        isPaused = false;  // Game starts in playing state
        this.gameState = gameState;
    }

    public int getScore() {
        return gameState.getScore();
    }

    public int getLevel() {
        return gameState.getLevel();
    }

    public Vector2 getBirdPosition() {
        return gameState.getBirdPosition();
    }


    void addScore(int points) {
        score += points;
        System.out.println("Score: " + score);
    }

    private void removeBlock(WoodBlocks block) {
        world.destroyBody(block.getBody());
        block.dispose();
    }
    private void resetBirdIfStopped() {
        if (currentBird.getBody().getLinearVelocity().len() < 0.1f && currentBird.getBody().getType() == BodyDef.BodyType.DynamicBody) {
            // Bird has stopped moving; reset it for dragging
            currentBird.getBody().setType(BodyDef.BodyType.StaticBody);
            currentBird.getBody().setTransform(slingStartPosition, 0); // Move bird back to sling position
            isDragging = false; // Reset dragging state
        }
    }



    //    Add screen boundaries
    private void createScreenBoundaries() {
        float screenWidth = VIRTUAL_WIDTH / 100f; // Convert to Box2D units
        float screenHeight = VIRTUAL_HEIGHT / 100f;

        createEdgeBoundary(0,0,0,screenHeight);
        createEdgeBoundary(0,screenHeight,screenWidth,screenHeight);
        createEdgeBoundary(screenWidth,screenHeight,screenWidth,0);
        createEdgeBoundary(screenWidth,0,0,0);    }
    private void createEdgeBoundary(float x1, float y1, float x2, float y2) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, 0);
        body = world.createBody(bodyDef);

        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(new Vector2(x1, y1), new Vector2(x2, y2));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = edgeShape;
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0.3f;

        body.createFixture(fixtureDef);
        edgeShape.dispose();
    }


        // Left boundary
//        BodyDef leftBodyDef = new BodyDef();
//        leftBodyDef.type = BodyDef.BodyType.StaticBody;
//        leftBodyDef.position.set(0, 0);
//        Body leftBoundary = world.createBody(leftBodyDef);
//
//        EdgeShape leftEdge = new EdgeShape();
//        leftEdge.set(new Vector2(0, 0), new Vector2(0, screenHeight));
//        leftBoundary.createFixture(leftEdge, 0);
//        leftEdge.dispose();
//
//        // Right boundary
//        BodyDef rightBodyDef = new BodyDef();
//        rightBodyDef.type = BodyDef.BodyType.StaticBody;
//        rightBodyDef.position.set(screenWidth, 0);
//        Body rightBoundary = world.createBody(rightBodyDef);
//
//        EdgeShape rightEdge = new EdgeShape();
//        rightEdge.set(new Vector2(0, 0), new Vector2(0, screenHeight));
//        rightBoundary.createFixture(rightEdge, 0);
//        rightEdge.dispose();
//
//        // Top boundary
//        BodyDef topBodyDef = new BodyDef();
//        topBodyDef.type = BodyDef.BodyType.StaticBody;
//        topBodyDef.position.set(0, screenHeight);
//        Body topBoundary = world.createBody(topBodyDef);
//
//        EdgeShape topEdge = new EdgeShape();
//        topEdge.set(new Vector2(0, 0), new Vector2(screenWidth, 0));
//        topBoundary.createFixture(topEdge, 0);
//        topEdge.dispose();
//    }



    @Override
    public void show() {
        loadGame();
        debugRenderer = new Box2DDebugRenderer();
        batch = new SpriteBatch();
        background = new Texture("gamePlay.png");
        pauseButton = new Texture("pauseButton.png");
        playButton = new Texture("play.png");
        scoreTextImage = new Texture("score.png");
        saveButton = new Texture("savegame.png");
        MusicControl.stopScoreMusic();
        MusicControl.stopBackgroundMusic();
        MusicControl.playGameplayMusic();
        shapeRenderer = new ShapeRenderer();



        world = new World(new Vector2(0, -9.8f), true);
        createScreenBoundaries();
        slingStartPosition = new Vector2(2.7f, 3.0f);
        sitonsling = new Vector2();
        dragPosition = new Vector2(slingStartPosition);
//        trajectoryPointTexture = new Texture("dot.png");
        font = new BitmapFont();
        font.setColor(Color.WHITE);

        BodyDef slingBodyDef = new BodyDef();
        slingBodyDef.type = BodyDef.BodyType.StaticBody;
        slingBodyDef.position.set(slingStartPosition);
        Body slingBody = world.createBody(slingBodyDef);
//
        PolygonShape slingShape = new PolygonShape();
        slingShape.setAsBox(0.1f, 0.1f);

        FixtureDef slingFixtureDef = new FixtureDef();
        slingFixtureDef.shape = slingShape;
        slingFixtureDef.density = 1.0f;
        slingFixtureDef.friction = 1;
        slingFixtureDef.restitution = 0.1f;
        slingFixtureDef.isSensor = true;
        slingBody.createFixture(slingFixtureDef);
        slingShape.dispose();

        birdList = new ArrayList<>();
        birdList.add(new RedBird(batch, slingStartPosition, world));  // Bird 1
        birdList.add(new YellowBird(batch, slingStartPosition, world));  // Bird 2
        birdList.add(new RedBird(batch, slingStartPosition, world));

        currentBird = birdList.get(currentBirdIndex);
        currentBird.getBody().setType(BodyDef.BodyType.StaticBody);
        currentBird.getBody().setTransform(slingStartPosition, 0);

        // Initialize RedBird objects with their positions
//        redBird1 = new RedBird(batch, new Vector2(180 / 100f, 203 / 100f), world); // Position in Box2D units
//        redBird1.body.applyLinearImpulse(new Vector2(0.5f, 0), redBird1.body.getWorldCenter(), true);
//        redBird2 = new RedBird(batch, slingStartPosition, world);
        // Set the bird's body type to static initially
//        redBird2.getBody().setType(BodyDef.BodyType.StaticBody);
//        redBird2.getBody().setTransform(slingStartPosition, 0);

        verticalWoodBlock1 = new VerticalWoodBlock(batch, new Vector2(1525/100f, 250/100f), world);
        verticalWoodBlock2 = new VerticalWoodBlock(batch, new Vector2(1700/100f, 250/100f), world);
//        horizontalWoodBlock1 = new HorizontalWoodBlock(batch , new Vector2(1600/100f, 350/100f), world);
        stoneBlock1 = new MediumSizedStoneBlock(batch , new Vector2(1530/100f, 158/100f), world);
        stoneBlock2 = new MediumSizedStoneBlock(batch , new Vector2(1610/100f, 158/100f),world);
        stoneBlock3 = new MediumSizedStoneBlock(batch , new Vector2(1690/100f, 158/100f),world);
//        triangleGlassBlock = new TriangleGlassBlock(batch, new Vector2(1617 /100f, 395/100f), world);
        // Initialize SlingShot object and load its texture
        slingShot = new SlingShot(batch, 230, 147);
        slingShot.show();  // Load the texture for SlingShot
//        yellowBird = new YellowBird(batch, new Vector2(80 / 100f, 203 / 100f), world);
        minionPig = new MinionPigs(batch, new Vector2(1620 / 100f, 223/ 100f), world);
        saveButtonBounds = new Rectangle(140, 950, 100, 100);

        trajectoryPoints = new Array<>(NUM_TRAJECTORY_POINTS);

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                // Record the drag start position (convert screen to world coordinates if needed)
                dragStart.set(screenX, screenY);
                dragging = true;
                return true;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                if (dragging) {
                    // Update drag end position while dragging
                    dragEnd.set(screenX, screenY);
                }
                return true;
            }

            @Override //
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if (dragging) {
                    dragging = false;

                    // Final drag end position
                    dragEnd.set(screenX, screenY);

                    // Calculate the drag vector (dragStart -> dragEnd in the opposite direction)
                    Vector2 dragVector = new Vector2(dragStart).sub(dragEnd);

                    // Print or use the drag vector for launching
                    System.out.println("Launch Vector: " + dragVector);

                    // Use the drag vector in your game logic (e.g., launching an object)
                    launchObject(dragVector);

                    return true;
                }
                return false;
            }
        });

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
        groundFixture.friction = 1; // Add friction for realism
        groundFixture.restitution = 0f; // Prevent bouncing

        groundBody.createFixture(groundFixture);
        groundShape.dispose();

        // Create a viewport with 1920x1080 dimensions
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        viewport.apply(true);  // Center the camera at the start
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());



        // Define the bounds of the pause button (used for click detection)
        pauseButtonBounds = new Rectangle(20, 950, 100, 100);
    }

    private void updateTrajectoryPoints(Vector2 slingStartPosition, Vector2 launchVelocity) {
        trajectoryPoints.clear();

        // Start position and velocity
        float x = slingStartPosition.x;
        float y = slingStartPosition.y;
        float vx = launchVelocity.x;
        float vy = launchVelocity.y;

        // Calculate points
        for (int i = 0; i < NUM_TRAJECTORY_POINTS; i++) {
            // Time at this point
            float t = i * TIME_STEP;

            // Physics equations for projectile motion
            float newX =x + vx * t;
            float newY = y + vy * t + 0.5f * gravity.y * t * t;

            // Don't add points that would be below ground
            if (newY < 0) {  // Adjust this value based on your ground height
                break;
            }

            trajectoryPoints.add(new Vector2(newX, newY));
        }
    }
private void launchObject(Vector2 dragVector) {
    // Reverse the x and y components of the drag vector manually
    float launchX = dragVector.x * 0.2f; // Negate X direction
    float launchY = -dragVector.y * 0.2f; // Negate Y direction
    Vector2 launchVector = new Vector2(launchX, launchY);

    System.out.println("Drag vector: " + dragVector + ", Launch vector: " + launchVector);

    // Apply the calculated impulse
    currentBird.getBody().setType(BodyDef.BodyType.DynamicBody);
    currentBird.getBody().applyLinearImpulse(launchVector, currentBird.getBody().getWorldCenter(), true);

    dragging = false;
    isDragging = false;
    Timer.schedule(new Timer.Task() {
        @Override
        public void run() {
            // Check if the current bird has stopped moving
            if (currentBirdIndex < birdList.size() - 1 && (currentBird.getBody().getLinearVelocity().len() < 0.1f || !isBodyActive(currentBird.getBody()))) {
                currentBirdIndex++;
                currentBird = birdList.get(currentBirdIndex);
                currentBird.getBody().setType(BodyDef.BodyType.StaticBody);
                currentBird.getBody().setTransform(slingStartPosition, 0);

                isDragging = false;
                dragging = false;
                // Prepare the next bird if available
//                if (!birdList.isEmpty()) {
////                    redBird2 = (RedBird) birdList.poll();  // Get the next bird
//                    currentBirdIndex++;
//                    currentBird.getBody().setType(BodyDef.BodyType.StaticBody);  // Make it static for the slingshot
//                    currentBird.getBody().setTransform(slingStartPosition, 0);
//                }
            }
        }
    }, 2f);

}

    private void checkPigCollision() {
        for (Bird bird : birdList) {
            if (isPigHit(bird, minionPig)) {
                System.out.println("Pig hit!");

                // Introduce a 1-second delay before transitioning to the next screen
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        System.out.println("Changing the screen to Level Complete.");
                        game.setScreen(new LevelComplete(game, gameState));
                    }
                }, 1); // Delay of 1 second

                return;
            }
        }
    }

    private boolean isPigHit(Bird bird, MinionPigs pig) {
        // Get positions of bird and pig
        Vector2 birdPos = bird.getBody().getPosition();
        Vector2 pigPos = pig.getBody().getPosition();

        // Approximate bird's radius as the distance from the center to a vertex
        float birdRadius = Bird.BIRD_WIDTH / 2 / 100f;  // For a hexagon, it's the circumcircle radius

        // Approximate pig's radius as half the diagonal of the rectangle
        float pigHalfWidth = MinionPigs.PIG_WIDTH / 2 / 100f;
        float pigHalfHeight = MinionPigs.PIG_HEIGHT / 2 / 100f;
        float pigRadius = (float) Math.sqrt(pigHalfWidth * pigHalfWidth + pigHalfHeight * pigHalfHeight);

        // Collision threshold as the sum of these radii
        float collisionThreshold = birdRadius + pigRadius;

        // Calculate distance between centers
        float distance = Vector2.dst(birdPos.x, birdPos.y, pigPos.x, pigPos.y);

        // Check if the distance is less than the threshold
        return distance < collisionThreshold;
    }




    private boolean isBodyActive(Body body) {
        return body.isActive() &&
            body.getType() == BodyDef.BodyType.DynamicBody;
    }
    private void renderTrajectory() {
        if (dragging) {  // Only show trajectory while dragging
            // Calculate launch velocity based on drag
            Vector2 dragVector = new Vector2(dragEnd).sub(dragStart);
//            dragVector.scl(0.2f);  // Use the same scaling factor as your launch method
            float launchX = dragVector.x * 0.2f; // Negate X direction
            float launchY = -dragVector.y * 0.2f; // Negate Y direction
            Vector2 launchVector = new Vector2(launchX, launchY);
            // Update trajectory points
            updateTrajectoryPoints(currentBird.getBody().getPosition(), launchVector);

            // Render the points
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(1, 1, 1, 0.5f);  // White with some transparency

            for (Vector2 point : trajectoryPoints) {
                // Convert physics coordinates to screen coordinates
                shapeRenderer.circle(
                    point.x * 100,  // Convert to screen coordinates
                    point.y * 100,  // Convert to screen coordinates
                    5  // Radius of the dots
                );
            }

            shapeRenderer.end();
        }
    }
    private void renderRubberEffect() {
        if (isDragging) {
            // Set the projection matrix to match the viewport's camera
            shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);

            // Start rendering the rubber bands
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.BROWN);

            // Left rubber band (to the bird)
            shapeRenderer.rectLine(slingStartPosition.x - 0.1f, slingStartPosition.y,
                dragPosition.x, dragPosition.y, 5);

            // Right rubber band (to the bird)
            shapeRenderer.rectLine(slingStartPosition.x+ 0.1f, slingStartPosition.y,
                dragPosition.x, dragPosition.y, 5);

            shapeRenderer.end();
        }
    }



    private Vector2 calculateLaunchVelocity() {
        Vector2 launchVector = new Vector2(dragPosition).sub(slingStartPosition); // Direction and distance of drag
        float power = launchVector.len() * 10;
//        launchVector.nor().scl(power);
//        float maxDragDistance = 2.0f; // Limit drag distance (adjust as needed)
        launchVector.x = max(4, min(5, launchVector.x));
        launchVector.y = max(4, min(5, launchVector.y));
        return launchVector;
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.step(delta, 6, 2);
        viewport.apply();


        // Set the SpriteBatch to draw within the viewport's

        batch.setProjectionMatrix(viewport.getCamera().combined);
        debugRenderer.render(world, viewport.getCamera().combined);

        // Begin drawing the background and elements
        batch.begin();
        batch.draw(background, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        batch.draw(scoreTextImage, 1700, 1020, 200, 50);
        // Draw the RedBirds on the screen
//        redBird1.render();
//        redBird1.sprite.setRotation(redBird1.getBody().getAngle());
//        redBird2.render();
//        redBird2.sprite.setRotation(redBird2.getBody().getAngle());
        verticalWoodBlock1.render();
        verticalWoodBlock2.render();
//        horizontalWoodBlock1.render();
        stoneBlock1.render();
        stoneBlock2.render();
//        triangleGlassBlock.render();
        stoneBlock3.render();
        slingShot.render();
//        yellowBird.render();
        minionPig.render();
        for (Bird bird : birdList) {
            bird.render();
        }



        if (isPaused) {
            batch.draw(playButton, pauseButtonBounds.x, pauseButtonBounds.y, pauseButtonBounds.width, pauseButtonBounds.height);
        } else {
            batch.draw(pauseButton, pauseButtonBounds.x, pauseButtonBounds.y, pauseButtonBounds.width, pauseButtonBounds.height);
        }
        batch.end();
        renderTrajectory();
//        handleInput();
//        resetBirdIfStopped();
        renderRubberEffect();
//        Gdx.app.log("Debug", "RedBird1 Position: " + redBird1.getBody().getPosition());
//        Gdx.app.log("Debug", "VerticalWoodBlock1 Position: " + verticalWoodBlock1.getBody().getPosition());
//        Gdx.app.log("Debug", "MinionPig Position: " + minionPig.getBody().getPosition());

        if(Gdx.input.isKeyJustPressed(Input.Keys.B)){
            currentBird.getBody().applyLinearImpulse(new Vector2(100, 50), currentBird.getBody().getWorldCenter(), true);
        }
        checkPigCollision();



//
        // Handle input for pause/play button
        if (Gdx.input.isTouched()) {
            Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPos);  // Convert screen coordinates to world coordinates

            // Check if the pause/play button is clicked
//            if (pauseButtonBounds.contains(touchPos)) {
//                if (isPaused) {
//                    // If game is paused, clicking button will resume game
//                    resumeGame();
//                } else {
//                    // If game is playing, clicking button will pause the game
//                    pauseGame();
//                }
//            }
            if (pauseButtonBounds.contains(touchPos)) {
                if (isPaused) {
                    resumeGame();
                } else {
                    pauseGame();
                }
            } else if (saveButtonBounds.contains(touchPos)) {
                saveGame();
            }
        }
    }

    private void pauseGame() {
        isPaused = true;
        game.setScreen(new PauseScreen(game, this, gameState));  // Switch to pause screen
    }

    private void resumeGame() {
        isPaused = false;
    }

    @Override
    public void resize(int width, int height) {
        // Update the viewport size on window resize
        viewport.update(width, height, true);
    }
//    private void loadGame() {
//        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("savegame.ser"))) {
//            GameState gameState = (GameState) ois.readObject(); // Deserialize the game state
//            game.setScreen(new Level1Screen(game, gameState)); // Load the level with the saved game state
//            System.out.println("Game loaded successfully!");
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//            System.out.println("Failed to load game!");
//        }
//    }

//    private void loadGame() {
//        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("savegame.ser"))) {
//            gameState = (GameState) ois.readObject(); // Deserialize the game state
//            System.out.println("Game loaded successfully!");
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//            System.out.println("Failed to load game!");
//        }
//    }

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
//    private void saveGame() {
//        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("savegame.ser"))) {
//            oos.writeObject(gameState); // Serialize the game state
//            System.out.println("Game saved successfully!");
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("Failed to save game!");
//        }
//    }
    private void saveGame() {
        // Update gameState with current game state
        gameState.setScore(score);
        gameState.setLevel(1); // Assuming level 1 for this example
        gameState.setBirdPosition(currentBird.getBody().getPosition());

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("savegame.ser"))) {
            oos.writeObject(gameState); // Serialize the game state
            System.out.println("Game saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to save game!");
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
//        redBird1.dispose();  // Dispose the RedBird textures
//        redBird2.dispose();  // Dispose the RedBird textures
        slingShot.dispose();  // Dispose the SlingShot textures
        pauseButton.dispose();
        playButton.dispose();
        scoreTextImage.dispose();
        debugRenderer.dispose();
        MusicControl.stopBackgroundMusic();
        shapeRenderer.dispose();

    }
}
