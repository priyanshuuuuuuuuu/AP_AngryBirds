package com.approject.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class GlassBlock {
    protected SpriteBatch batch;
    protected Texture texture;
    protected Vector2 position;
    protected Body body;
    protected World world;
    protected int health;

    public GlassBlock(Texture texture, SpriteBatch batch, Vector2 position, World world, int health) {
        this.texture = texture;
        this.batch = batch;
        this.position = position;
        this.world = world;
        this.health = health;
        initializeBody();
        body.setUserData(this);
    }

    public void initializeBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position);
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(texture.getWidth() / 2 / 100f, texture.getHeight() / 2 / 100f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        body.createFixture(fixtureDef);
        shape.dispose();
    }

    public void render() {
        batch.draw(texture, body.getPosition().x * 100f - texture.getWidth() / 2f, body.getPosition().y * 100f - texture.getHeight() / 2f);
    }

    public void dispose() {
        texture.dispose();
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            destroy();
        }
    }

    private void destroy() {
        if (world != null) {
            world.destroyBody(body);
        }
    }

    public Body getBody() {return body;
    }
}
