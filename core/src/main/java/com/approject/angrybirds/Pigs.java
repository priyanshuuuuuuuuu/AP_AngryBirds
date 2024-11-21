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
    public static final float PIG_HEIGHT =90f;
    protected Body body;
    protected World world;
    protected Sprite sprite;
    public Pigs(Texture texture, SpriteBatch batch, Vector2 position, Vector2 velocity) {
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
        shape.setRadius(PIG_WIDTH/2/100f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.7f;
        body.createFixture(fixtureDef);

        shape.dispose();
    }

    public void render(){
        if(body != null){
            position.set(body.getPosition().x*100f- PIG_WIDTH/2, body.getPosition().y*100f- PIG_HEIGHT/2);
        }
        batch.draw(texture, position.x, position.y, PIG_WIDTH, PIG_HEIGHT);
    }

    public void dispose(){
        texture.dispose();
    }
}
