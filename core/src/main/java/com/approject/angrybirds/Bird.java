package com.approject.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public abstract class Bird {
    protected Texture texture;
    protected SpriteBatch batch;
    protected Vector2 position;
    protected Vector2 velocity;
    public static final float BIRD_WIDTH = 100f;
    public static final float BIRD_HEIGHT = 100f;
    protected Body body;
    protected Sprite sprite;
    private Vector2 slingStartPosition;
    protected Rectangle bounds;

    public Bird(Texture texture, SpriteBatch batch, Vector2 position, Vector2 velocity) {
        this.texture = texture;
        this.batch = batch;
        this.position = position;
        this.velocity = velocity;
        bounds = new Rectangle(position.x, position.y, BIRD_WIDTH, BIRD_HEIGHT);
        sprite = new Sprite(texture);
    }
    public void initializeBody(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;  // Initial state
        bodyDef.position.set(position);
        body = world.createBody(bodyDef);

        // Define a hexagon shape
        PolygonShape shape = new PolygonShape();
        float radius = BIRD_WIDTH / 2 / 100f;  // Radius in Box2D units

        // Approximate a hexagon with 6 vertices
        Vector2[] vertices = new Vector2[6];
        for (int i = 0; i < 6; i++) {
            float angle = (float) (i * Math.PI / 3);  // Divide full circle into 6 parts
            vertices[i] = new Vector2(
                radius * (float) Math.cos(angle),
                radius * (float) Math.sin(angle)
            );
        }
        shape.set(vertices);

        // Fixture definition
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0.1f;
        body.createFixture(fixtureDef);

        shape.dispose();  // Clean up
    }


    public abstract void specialAbility();
    public void updateBounds() {
        bounds.setPosition(body.getPosition().x - BIRD_WIDTH/2, body.getPosition().y - BIRD_HEIGHT/2);
    }


    public void render() {
        if(body != null){
            position.set(body.getPosition().x*100f- BIRD_WIDTH/2, body.getPosition().y*100f- BIRD_HEIGHT/2);
        }
        batch.draw(texture, position.x, position.y, BIRD_WIDTH, BIRD_HEIGHT);;
    }

    public void dispose() {
        texture.dispose();
    }

    public void setPosition(float x, float y) {
        position.set(x, y);
    }
    public Rectangle getBounds() {
        return new Rectangle(
            sprite.getX(),
            sprite.getY(),
            sprite.getWidth(),
            sprite.getHeight()
        );
    }

    public Body getBody() {
        return body;
    }
}
