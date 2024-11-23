package com.approject.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public abstract class GlassBlock {
    protected SpriteBatch batch;
    protected Texture texture;
    protected Vector2 position;
    protected World world;
    protected Body body;


    public GlassBlock(Texture texture, SpriteBatch batch, Vector2 position, World world) {
        this.texture = texture;
        this.batch = batch;
        this.position = position;
        this.world = world;
        initializeBody();
        body.setUserData(this);
    }

    public abstract void health();
    public void initializeBody(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(position);
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(texture.getWidth() / 2/100f, texture.getHeight() / 2/100f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        body.createFixture(fixtureDef);
        shape.dispose();
    }
    public void render(){
        batch.draw(texture, body.getPosition().x * 100f - texture.getWidth() / 2f, body.getPosition().y * 100f - texture.getHeight() / 2f);
    }

    public void dispose(){
        texture.dispose();
    }
}
