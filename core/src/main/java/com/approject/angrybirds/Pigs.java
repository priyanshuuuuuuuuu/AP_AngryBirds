package com.approject.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public abstract class Pigs {
    protected Texture texture;
    protected SpriteBatch batch;
    protected Vector2 position;
    protected Vector2 velocity;
    public static final float PIG_WIDTH = 90f;
    public static final float PIG_HEIGHT = 90f;
    protected Body body;
    protected World world;
    protected Sprite sprite;
    private int health;
    int type;

    // Modified constructor to accept health as a parameter
    public Pigs(Texture texture, SpriteBatch batch, Vector2 position, Vector2 velocity, int health, int type) {
        this.texture = texture;
        this.batch = batch;
        this.position = position;
        sprite = new Sprite(texture);
        this.health = health;  // Set health to the provided value
        this.type = type;
    }

    public void initializeBody(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;  // Dynamic body for physical interaction
        bodyDef.position.set(position);
//        bodyDef.linearVelocity.set(velocity);
        body = world.createBody(bodyDef);

        // Define a hexagonal shape
        PolygonShape shape = new PolygonShape();
        float radius = PIG_WIDTH / 2 / 100f;  // Convert to Box2D units

        // Approximate a hexagon with 6 vertices
        Vector2[] vertices = new Vector2[6];
        for (int i = 0; i < 6; i++) {
            float angle = (float) (i * Math.PI / 3);  // Divide full circle into 6 parts
            vertices[i] = new Vector2(
                radius * (float) Math.cos(angle),
                radius * (float) Math.sin(angle)
            );
        }
        shape.set(vertices);  // Set the shape using the vertices

        // Fixture definition
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;  // Adjust density as needed
        fixtureDef.friction = 1f;   // Adjust friction for realism
        fixtureDef.restitution = 0.7f;  // Elasticity for bouncing effect
        body.createFixture(fixtureDef);

        shape.dispose();  // Clean up the shape after creating the fixture
    }

//    public void render() {
//        if (body != null) {
//            position.set(body.getPosition().x * 100f - PIG_WIDTH / 2, body.getPosition().y * 100f - PIG_HEIGHT / 2);
//        }
//        batch.draw(texture, position.x, position.y, PIG_WIDTH, PIG_HEIGHT);
//    }

    public Body getBody() {
        return body;
    }

    public void dispose() {
        texture.dispose();
    }
    public void setPosition(float x, float y) {
        position.set(x, y);
    }


    // Method to handle the damage when pig is hit
    public void takeDamage(int damage) {
        health -= damage;
        System.out.println("Pig took damage. Remaining health: " + health);
        if (health <= 0) {
            destroy();
        }
    }

    // Method to destroy the pig when health reaches 0
    private void destroy() {
        if (world != null) {
            System.out.println("Pig destroyed!");
            // Remove from the Box2D world
            world.destroyBody(body);
        } else {
            System.out.println("World is null, cannot destroy pig.");
        }
    }

    public int getHealth() {
        return health;
    }
    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }
    public void setWorld(World world) {
        initializeBody(world);
    }
}
