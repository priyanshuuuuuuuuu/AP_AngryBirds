package com.approject.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

    public Bird(Texture texture, SpriteBatch batch, Vector2 position, Vector2 velocity) {
        this.texture = texture;
        this.batch = batch;
        this.position = position;
        this.velocity = velocity;

        sprite = new Sprite(texture);
    }
    public void initializeBody(World world){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position);
        bodyDef.linearVelocity.set(velocity);
        body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(BIRD_WIDTH/2/100f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.7f;
        body.createFixture(fixtureDef);

        shape.dispose();
    }
    public abstract void specialAbility();

    public void render() {
        if(body != null){
            position.set(body.getPosition().x*100f- BIRD_WIDTH/2, body.getPosition().y*100f- BIRD_HEIGHT/2);
        }
        batch.draw(texture, position.x, position.y, BIRD_WIDTH, BIRD_HEIGHT);
    }

    public void dispose() {
        texture.dispose();
    }
}
