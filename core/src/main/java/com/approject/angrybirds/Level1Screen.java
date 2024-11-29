package com.approject.angrybirds;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
//import java.util.Timer;

import static com.approject.angrybirds.Bird.BIRD_HEIGHT;
import static com.approject.angrybirds.Bird.BIRD_WIDTH;
import static com.approject.angrybirds.Pigs.PIG_HEIGHT;
import static com.approject.angrybirds.Pigs.PIG_WIDTH;
import static java.lang.Math.max;
import static java.lang.Math.min;


public class Level1Screen extends ScreenAdapter{
    public static AngryBirds game;
    SpriteBatch batch;
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
    World world;
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
    public List<Bird> birdList;
    private Bird currentBird;
    private GameState gameState;
    private Texture saveButton;
    private Rectangle saveButtonBounds; // For detecting click on save button
    private int level;
    public int loaded;
    public List<Pigs> pigList;
    private boolean levelComplete = false;
    private List<Object> blockList;


    // Constants for virtual width and height
    private static final float VIRTUAL_WIDTH = 1920;
    private static final float VIRTUAL_HEIGHT = 1080;
    private static final float MAX_DRAG_DISTANCE = 2.0F;
    private static final float DRAG_MULTIPLIER = 5.0f;
    private final float TIME_STEP = 0.1f;
    private int currentBirdIndex = 0;


    public Level1Screen(AngryBirds game) {
        this.game = game;
        isPaused = false;  // Game starts in playing state
        this.gameState = gameState;
        birdList = new ArrayList<>();
        this.batch = new SpriteBatch();
        this.world = new World(new Vector2(0, -9.8f), true);
        pigList = new ArrayList<>();
        this.loaded = 0;
        initializeNewGame();

        blockList = new ArrayList<>();

    }
    public Level1Screen(AngryBirds game, GameState gameState){
        this.game = game;
        this.gameState = gameState;
        this.world = new World(new Vector2(0, -9.8f), true);
        blockList = new ArrayList<>();
        pigList = new ArrayList<>();
        birdList = new ArrayList<>();
        loadGame();
    }
    public void initializeNewGame(){

    }



//    public void restoreEntities(GameState gameState) {
//        // Restore birds
//        for (BirdData birdData : gameState.birds) {
//            Bird bird = new RedBird(batch, new Vector2(birdData.x, birdData.y), world);
//            bird.getBody().setTransform(birdData.x, birdData.y, 0);
//            bird.getBody().setLinearVelocity(birdData.velocityX, birdData.velocityY);
//            birdList.add(bird);
//        }
//
//        // Restore pigs
//        for (PigData pigData : gameState.pigs) {
//            Pigs pig = new MinionPigs(batch, new Vector2(pigData.x, pigData.y), world);
//            pig.getBody().setTransform(pigData.x, pigData.y, 0);
//            pigList.add(pig);
//        }
//
//        // Restore blocks
////        for (BlockData blockData : gameState.blocks) {
////            Object block = null; // Initialize as appropriate block type
////            if ("Wood".equals(blockData.type)) {
////                block = new VerticalWoodBlock(batch, new Vector2(blockData.x, blockData.y), world);
////            } else if ("Stone".equals(blockData.type)) {
////                block = new MediumSizedStoneBlock(batch, new Vector2(blockData.x, blockData.y), world);
////            } else if ("Glass".equals(blockData.type)) {
////                block = new GlassBlock(batch, new Vector2(blockData.x, blockData.y), world);
////            }
////            if (block != null) blockList.add(block);
////        }
//    }



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

//    private void removeBlock(WoodBlocks block) {
//        world.destroyBody(block.getBody());
//        block.dispose();
//    }
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



    @Override
    public void show() {
        world = new World(new Vector2(0, -9.8f), true);

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
        if(loaded == 0){
            creater();
        }

//        birdList = new ArrayList<>();
//        birdList.add(new RedBird(batch, slingStartPosition, world));  // Bird 1
//        birdList.add(new YellowBird(batch, slingStartPosition, world));  // Bird 2
//        birdList.add(new RedBird(batch, slingStartPosition, world));

        currentBird = birdList.get(currentBirdIndex);
        currentBird.getBody().setType(BodyDef.BodyType.StaticBody);
        currentBird.getBody().setTransform(slingStartPosition, 0);

//        pigList = new ArrayList<>();
//        pigList.add( new MinionPigs(batch, new Vector2(1620 / 100f, 223/ 100f), world));
        blockList.add(new VerticalWoodBlock(batch, new Vector2(1525 / 100f, 250 / 100f), world));
        blockList.add(new VerticalWoodBlock(batch, new Vector2(1700 / 100f, 250 / 100f), world));
        blockList.add(new MediumSizedStoneBlock(batch, new Vector2(1530 / 100f, 158 / 100f), world));
        blockList.add(new MediumSizedStoneBlock(batch, new Vector2(1610 / 100f, 158 / 100f), world));
        blockList.add(new MediumSizedStoneBlock(batch, new Vector2(1690 / 100f, 158 / 100f), world));


//        horizontalWoodBlock1 = new HorizontalWoodBlock(batch , new Vector2(1600/100f, 350/100f), world);
//        stoneBlock1 = new MediumSizedStoneBlock(batch , new Vector2(1530/100f, 158/100f), world);
//        stoneBlock2 = new MediumSizedStoneBlock(batch , new Vector2(1610/100f, 158/100f),world);
//        stoneBlock3 = new MediumSizedStoneBlock(batch , new Vector2(1690/100f, 158/100f),world);
//        triangleGlassBlock = new TriangleGlassBlock(batch, new Vector2(1617 /100f, 395/100f), world);
        // Initialize SlingShot object and load its texture
        slingShot = new SlingShot(batch, 230, 147);
        slingShot.show();  // Load the texture for SlingShot
//        yellowBird = new YellowBird(batch, new Vector2(80 / 100f, 203 / 100f), world);
//        minionPig = new MinionPigs(batch, new Vector2(1620 / 100f, 223/ 100f), world);
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

    private void creater(){
        birdList = new ArrayList<>();
        birdList.add(new YellowBird(batch, slingStartPosition, world));  // Bird 1
        birdList.add(new YellowBird(batch, slingStartPosition, world));  // Bird 2
        birdList.add(new RedBird(batch, slingStartPosition, world));
        pigList = new ArrayList<>();
        pigList.add( new MinionPigs(batch, new Vector2(1620 / 100f, 223/ 100f), world));

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
    currentBirdIndex++;
    try{
        currentBird = birdList.get(currentBirdIndex);
    }catch (IndexOutOfBoundsException e){
        System.out.println("No more birds left");
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (game.getScreen() instanceof Level1Screen) { // Check if we're still on the current level screen
                    game.setScreen(new RetryScoreScreen(game, gameState));
                }
            }
        }, 4);

    }
    dragging = false;
    isDragging = false;

}

    private boolean transitionScheduled = false; // Add this flag

    private void checkCollision() {
        for (Bird bird : birdList) {
            for (Iterator<Pigs> pigIterator = pigList.iterator(); pigIterator.hasNext(); ) {
                Pigs pig = pigIterator.next();
                if (isPigHit(bird, pig)) {
                    System.out.println("Pig hit!");
                    pig.takeDamage(1);

                    if (pig.getHealth() <= 0) {
                        // Destroy the pig's body in the Box2D world
                        world.destroyBody(pig.getBody());
                        // Remove the pig from the list
                        pigIterator.remove();
                    }
                }
            }

//            System.out.println("refreshing");
//            for (Iterator<Object> blockIterator = blockList.iterator(); blockIterator.hasNext(); ) {
////                System.out.println("refreshing");
//                Object block = blockIterator.next();
////                System.out.println("refreshing");
//                if (block instanceof VerticalWoodBlock && isBlockHit(bird, (WoodBlocks) block)) {
//                    System.out.println("Wood block hit!");
//                    world.destroyBody(((WoodBlocks) block).body);
//                    System.out.println("Wood block hit!");
//                    blockIterator.remove();
//                    System.out.println("Wood block destroyed!");
//                } else if (block instanceof StoneBlocks && isBlockHit(bird, (StoneBlocks) block)) {
//                    world.destroyBody(((StoneBlocks) block).body);
//                    blockIterator.remove();
//                } else if (block instanceof GlassBlock && isBlockHit(bird, (GlassBlock) block)) {
//                    world.destroyBody(((GlassBlock) block).body);
//                    blockIterator.remove();
//                }
//            }

        }

        // Check if all pigs are removed and schedule level completion transition only once
        if (pigList.isEmpty() && !transitionScheduled) {
            transitionScheduled = true; // Mark the transition as scheduled
            levelComplete = true;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    System.out.println("Changing the screen to Level Complete.");
                    game.setScreen(new LevelComplete(game));
                }
            }, 2); // Delay transition by 2 seconds
        }
    }


    public class CollisionListener implements ContactListener {

        private List<Object> blockList;

        public CollisionListener(List<Object> blockList) {
            this.blockList = blockList;
        }

        @Override
        public void beginContact(Contact contact) {
            Fixture fixtureA = contact.getFixtureA();
            Fixture fixtureB = contact.getFixtureB();
            System.out.println("Collision detected!");

            // Check if the collision involves a bird and a block
            if (isBirdAndBlockCollision(fixtureA, fixtureB)) {
                handleBirdBlockCollision(fixtureA, fixtureB);
            }
        }

        @Override
        public void endContact(Contact contact) {
            // Handle end of contact if needed
        }

        @Override
        public void preSolve(Contact contact, Manifold oldManifold) {
            // Handle pre-solve if needed
        }

        @Override
        public void postSolve(Contact contact, ContactImpulse impulse) {
            // Handle post-solve if needed
        }

        private boolean isBirdAndBlockCollision(Fixture fixtureA, Fixture fixtureB) {
            return (fixtureA.getBody().getUserData() instanceof Bird && fixtureB.getBody().getUserData() instanceof WoodBlocks) ||
                (fixtureA.getBody().getUserData() instanceof WoodBlocks && fixtureB.getBody().getUserData() instanceof Bird);
        }

        private void handleBirdBlockCollision(Fixture fixtureA, Fixture fixtureB) {
            Body birdBody = fixtureA.getBody().getUserData() instanceof Bird ? fixtureA.getBody() : fixtureB.getBody();
            Body blockBody = fixtureA.getBody().getUserData() instanceof WoodBlocks ? fixtureA.getBody() : fixtureB.getBody();

            // Handle the collision (e.g., destroy the block)
            World world = birdBody.getWorld();
            world.destroyBody(blockBody);

            // Remove the block from the blockList
            blockList.removeIf(block -> ((WoodBlocks) block).getBody() == blockBody);

            System.out.println("Block destroyed due to collision with bird!");
        }
    }



    private boolean isPigHit(Bird bird, Pigs pig) {
        // Get positions of bird and pig
        Vector2 birdPos = bird.getBody().getPosition();
        Vector2 pigPos = pig.getBody().getPosition();

        // Approximate bird's radius as the distance from the center to a vertex
        float birdRadius = BIRD_WIDTH / 2 / 100f;  // For a hexagon, it's the circumcircle radius

        // Approximate pig's radius as half the diagonal of the rectangle
        float pigHalfWidth = PIG_WIDTH / 2 / 100f;
        float pigHalfHeight = PIG_HEIGHT / 2 / 100f;
        float pigRadius = (float) Math.sqrt(pigHalfWidth * pigHalfWidth + pigHalfHeight * pigHalfHeight);

        // Collision threshold as the sum of these radii
        float collisionThreshold = birdRadius + pigRadius;

        // Calculate distance between centers
        float distance = Vector2.dst(birdPos.x, birdPos.y, pigPos.x, pigPos.y);

        // Check if the distance is less than the threshold
        return distance < collisionThreshold;
    }
    private boolean isBlockHit(Bird bird, WoodBlocks block){
        return false;
    }
    private boolean isBlockHit(Bird bird, GlassBlock block){
        return false;
    }
    private boolean isBlockHit(Bird bird, StoneBlocks block){
        return false;
    }




    private boolean isBodyActive(Body body) {
        return body.isActive() &&
            body.getType() == BodyDef.BodyType.DynamicBody;
    }
    private void renderTrajectory() {
        if (dragging) {  // Only show trajectory while dragging
            // Calculate launch velocity based on drag
//            Vector2 dragVector = new Vector2(dragEnd).sub(dragStart);
            Vector2 dragVector = new Vector2(dragStart).sub(dragEnd);
//            dragVector.scl(0.2f);  // Use the same scaling factor as your launch method
            float launchX = dragVector.x * 0.2f; // Negate X direction
            float launchY = -dragVector.y * 0.3f; // Negate Y direction
            Vector2 launchVector = new Vector2(launchX, launchY);
            // Update trajectory points
            updateTrajectoryPoints(currentBird.getBody().getPosition(), launchVector);

            // Render the points
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(1, 1, 1, 0.5f);  // White with some transparency

            for (Vector2 point : trajectoryPoints) {
                // Convert physics coordinates to screen coordinates
                shapeRenderer.circle(
                    point.x * 60,  // Convert to screen coordinates
                    point.y * 60,  // Convert to screen coordinates
                    5 // Radius of the dots
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

//        verticalWoodBlock1.render();
//        verticalWoodBlock2.render();
//        horizontalWoodBlock1.render();//
//        stoneBlock1.render();
//        stoneBlock2.render();
//        triangleGlassBlock.render();
//        stoneBlock3.render();
        slingShot.render();
//        yellowBird.render();
//        minionPig.render();
//        for (Bird bird : birdList) {
////            bird.setBatch(batch);
////            bird.setWorld(world);
////            bird.render();
//            System.out.println("Bird22 position: " + bird.getBody().getPosition().x * 100 );
//            batch.draw(bird.texture, bird.getBody().getPosition().x*100f - BIRD_WIDTH/2, bird.getBody().getPosition().y*100f - BIRD_HEIGHT/2, BIRD_WIDTH, BIRD_HEIGHT);
//            System.out.println("Bird position: " + body.getPosition().x*100f);
////            batch.draw(bird.texture, 270,300, BIRD_WIDTH, BIRD_HEIGHT);
//
//        }
        for (Bird bird : birdList) {
//            if (bird.isAtRest() && !bird.isPositionPrinted()) {
//                System.out.println("Bird position: " + bird.getBody().getPosition().x * 100);
//                bird.setPositionPrinted(true);  // Ensure it prints only once
//            }
            batch.draw(bird.texture, bird.getBody().getPosition().x*100f - BIRD_WIDTH/2, bird.getBody().getPosition().y*100f - BIRD_HEIGHT/2, BIRD_WIDTH, BIRD_HEIGHT);

        }
        for(Pigs pigs: pigList){
//            pigs.setBatch(batch);
//            pigs.setWorld(world);
//            pigs.render();
            batch.draw(pigs.texture, pigs.getBody().getPosition().x * 100f - PIG_WIDTH / 2, pigs.getBody().getPosition().y * 100f - PIG_HEIGHT / 2, PIG_WIDTH, PIG_HEIGHT);
        }
        for (Object block : blockList) {
            if (block instanceof WoodBlocks) {
                ((WoodBlocks) block).render();
            } else if (block instanceof StoneBlocks) {
                ((StoneBlocks) block).render();
            } else if (block instanceof GlassBlock) {
                ((GlassBlock) block).render();
            }
        }


        if (isPaused) {
            batch.draw(playButton, pauseButtonBounds.x, pauseButtonBounds.y, pauseButtonBounds.width, pauseButtonBounds.height);
        } else {
            batch.draw(pauseButton, pauseButtonBounds.x, pauseButtonBounds.y, pauseButtonBounds.width, pauseButtonBounds.height);
        }
        batch.end();
        renderTrajectory();

        renderRubberEffect();

        if(Gdx.input.isKeyJustPressed(Input.Keys.B)){
            currentBird.getBody().applyLinearImpulse(new Vector2(100, 50), currentBird.getBody().getWorldCenter(), true);
        }
        checkCollision();



//
        // Handle input for pause/play button
        if (Gdx.input.isTouched()) {
            Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPos);  // Convert screen coordinates to world coordinates


            if (pauseButtonBounds.contains(touchPos)) {
                if (isPaused) {
                    resumeGame();
                } else {
                    pauseGame();
                }
            } else if (saveButtonBounds.contains(touchPos)) {
                System.out.println("Game saved");
                saveGame();
            }
        }
    }

    private void pauseGame() {
        isPaused = true;
        game.setScreen(new Pause1Screen(game, this, gameState));  // Switch to pause screen
    }

    private void resumeGame() {
        isPaused = false;
    }

    @Override
    public void resize(int width, int height) {
        // Update the viewport size on window resize
        viewport.update(width, height, true);
    }

//    public void saveGame() {
//        GameState gameState = new GameState();
//        gameState.birds = new ArrayList<>();
//        for(Bird bird: birdList){
//            BirdData birdData = new BirdData();
//            birdData.x = bird.body.getPosition().x;
//            birdData.y = bird.body.getPosition().y;
//            birdData.velocityX = bird.body.getLinearVelocity().x;
//            birdData.velocityY = bird.body.getLinearVelocity().y;
//            gameState.birds.add(birdData);
//        }
//        gameState.pigs = new ArrayList<>();
//        for(Pigs pig : pigList){
//            PigData pigData = new PigData();
//            pigData.x = pig.body.getPosition().x;
//            pigData.y = pig.body.getPosition().y;
////            pigData.health = pig.health;
//            gameState.pigs.add(pigData);
//
//        }
//
//        gameState.blocks = new ArrayList<>();
//        BlockData blockData = new BlockData();
//        blockData.x = verticalWoodBlock1.body.getPosition().x;
//        blockData.y = verticalWoodBlock1.body.getPosition().y;
//
//        // Update gameState with current game state
////        gameState.setScore(score);
////        gameState.setLevel(1); // Assuming level 1 for this example
////        gameState.setBirdPosition(currentBird.getBody().getPosition());
//
//        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("savegame.dat"))) {
//            oos.writeObject(gameState); // Serialize the game state
//            System.out.println("Game saved successfully!");
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("Failed to save game!");
//        }
//    }

//    public static Level1Screen loadGame(){
//        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("savegame.dat"))) {
//            GameState gameState = (GameState) in.readObject();
//            Level1Screen level1Screen = new Level1Screen(game);
//
//            for(int i = 0; i < gameState.birds.size(); i++){
//                BirdData birdData = gameState.birds.get(i);
//                Bird birds = level1Screen.birdList.get(i);
//                birds.body.setTransform(birdData.x, birdData.y, 0);
//                birds.body.setLinearVelocity(birdData.velocityX, birdData.velocityY);
//            }
//            for(int i =0; i < gameState.pigs.size(); i++){
//                PigData pigData = gameState.pigs.get(i);
//                Pigs pigs = level1Screen.pigList.get(i);
//                pigs.body.setTransform(pigData.x, pigData.y, 0);
//            }
//            for (BlockData blockData : gameState.blocks) {
//                level1Screen.verticalWoodBlock1.body.setTransform(blockData.x, blockData.y, 0);
//                level1Screen.verticalWoodBlock2.body.setTransform(blockData.x, blockData.y, 0);
//                level1Screen.stoneBlock1.body.setTransform(blockData.x, blockData.y, 0);
//                level1Screen.stoneBlock2.body.setTransform(blockData.x, blockData.y, 0);
//                level1Screen.stoneBlock3.body.setTransform(blockData.x, blockData.y, 0);
//
//            }
//            System.out.println("Game loaded successfully!");
//            return level1Screen;
//        }catch (IOException | ClassNotFoundException e){
//            e.printStackTrace();
//            System.out.println("Failed to load game!");
//            return null;
//        }
//    }
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

    public boolean isLevelComplete() {
        return levelComplete;
    }

    public void setLevelComplete(boolean levelComplete) {
        this.levelComplete = levelComplete;
    }
    public void saveGame() {
        GameState gameState = new GameState();
        gameState.birds = new ArrayList<>();
        for (Bird bird : birdList) {
            BirdData birdData = new BirdData();
            birdData.x = bird.getBody().getPosition().x;
            System.out.println("Bird x: " + bird.getBody().getPosition().x);
            birdData.y = bird.getBody().getPosition().y;
            System.out.println("Bird y: " + bird.getBody().getPosition().y);
            birdData.velocityX = bird.getBody().getLinearVelocity().x;
            System.out.println("Bird velocityX: " + bird.getBody().getLinearVelocity().x);
            birdData.velocityY = bird.getBody().getLinearVelocity().y;
            System.out.println("Bird velocityY: " + bird.getBody().getLinearVelocity().y);
            gameState.birds.add(birdData);
        }
        gameState.pigs = new ArrayList<>();
        for (Pigs pig : pigList) {
            PigData pigData = new PigData();
            pigData.x = pig.getBody().getPosition().x;
            pigData.y = pig.getBody().getPosition().y;
            gameState.pigs.add(pigData);
        }
        gameState.blocks = new ArrayList<>();
//        for (Object block : blockList) {
//            BlockData blockData = new BlockData();
//            if (block instanceof WoodBlocks) {
//                blockData.x = ((WoodBlocks) block).getBody().getPosition().x;
//                blockData.y = ((WoodBlocks) block).getBody().getPosition().y;
//            } else if (block instanceof StoneBlocks) {
//                blockData.x = ((StoneBlocks) block).getBody().getPosition().x;
//                blockData.y = ((StoneBlocks) block).getBody().getPosition().y;
//            } else if (block instanceof GlassBlock) {
//                blockData.x = ((GlassBlock) block).getBody().getPosition().x;
//                blockData.y = ((GlassBlock) block).getBody().getPosition().y;
//            }
//            gameState.blocks.add(blockData);
//        }

        gameState.setScore(score);
        gameState.setLevel(level);
        gameState.setBirdPosition(currentBird.getBody().getPosition());

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("D:\\AP_AngryBirds\\core\\src\\main\\java\\com\\approject\\angrybirds\\savegame.dat"))) {
            oos.writeObject(gameState);
            System.out.println("Game saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to save game!");
        }
    }
    public static Level1Screen loadGame() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("D:\\AP_AngryBirds\\core\\src\\main\\java\\com\\approject\\angrybirds\\savegame.dat"))) {
            GameState gameState = (GameState) in.readObject();
            Level1Screen level1Screen = new Level1Screen(new AngryBirds());


            for (int i = 0; i < gameState.birds.size(); i++) {
                if(i < level1Screen.birdList.size()){
                    BirdData birdData = gameState.birds.get(i);
                    Bird bird = level1Screen.birdList.get(i);
                    bird.getBody().setTransform(birdData.x, birdData.y, 0);
                    bird.getBody().setLinearVelocity(birdData.velocityX, birdData.velocityY);
                }
//                BirdData birdData = gameState.birds.get(i);
//                Bird bird = level1Screen.birdList.get(i);
//                bird.getBody().setTransform(birdData.x, birdData.y, 0);
//                bird.getBody().setLinearVelocity(birdData.velocityX, birdData.velocityY);
            }
            for (int i = 0; i < gameState.pigs.size(); i++) {
                if (i < level1Screen.pigList.size()) {
                    PigData pigData = gameState.pigs.get(i);
                    Pigs pig = level1Screen.pigList.get(i);
                    pig.getBody().setTransform(pigData.x, pigData.y, 0);
                }
            }
            for (int i = 0; i < gameState.blocks.size(); i++) {
                if (i < level1Screen.blockList.size()) {
                    BlockData blockData = gameState.blocks.get(i);
                    Object block = level1Screen.blockList.get(i);
                    if (block instanceof WoodBlocks) {
                        ((WoodBlocks) block).getBody().setTransform(blockData.x, blockData.y, 0);
                    } else if (block instanceof StoneBlocks) {
                        ((StoneBlocks) block).getBody().setTransform(blockData.x, blockData.y, 0);
                    } else if (block instanceof GlassBlock) {
                        ((GlassBlock) block).getBody().setTransform(blockData.x, blockData.y, 0);
                    }
                }
            }

            level1Screen.score = gameState.getScore();
            level1Screen.level = gameState.getLevel();
            if (level1Screen.currentBird != null && gameState.getBirdPosition() != null) {
                level1Screen.currentBird.getBody().setTransform(gameState.getBirdPosition(), 0);
            }
//            level1Screen.currentBird.getBody().setTransform(gameState.getBirdPosition(), 0);

            System.out.println("Game loaded successfully!");
            return level1Screen;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Failed to load game!");
            return null;
        }
    }
}
