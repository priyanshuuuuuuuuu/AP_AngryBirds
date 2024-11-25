package com.approject.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public abstract class WoodBlocks {
    protected SpriteBatch batch;
    protected Texture texture;
    protected Vector2 position;
    protected Body body;
    public int health;
    protected World world;

    public WoodBlocks(Texture texture, SpriteBatch batch, Vector2 position,World world, int health) {
        this.texture = texture;
        this.batch = batch;
        this.health = health;
        this.position = position;
        this.world = world;
        initializeBody();
        body.setUserData(this);
    }

    public abstract void health();

    public void initializeBody(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position);
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(texture.getWidth() /2 /100f, texture.getHeight() /2 /100f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 1f;
        body.createFixture(fixtureDef);
        shape.dispose();
    }
    public void render(){

        batch.draw(texture,body.getPosition().x * 100f - texture.getWidth()/2f, body.getPosition().y * 100f - texture.getHeight() /2f);
//        batch.draw(texture, 1000, 147);
    }
    public Body getBody() {
        return body;
    }
    public void dispose(){
        texture.dispose();
    }
    public void addCollisionListener(World world, Body bird) {
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                // Get fixtures involved in the collision
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();

                // Check if one of the fixtures is the bird
                boolean isBirdFixture = (fixtureA.getBody() == bird || fixtureB.getBody() == bird);

                // Check if the other fixture is a structure
                Body structure = (fixtureA.getBody() == bird) ? fixtureB.getBody() : fixtureA.getBody();

                if (isBirdFixture && structure.getType() == BodyDef.BodyType.StaticBody) {
                    System.out.println("Bird collided with structure!");

                    // Convert the structure to dynamic
                    structure.setType(BodyDef.BodyType.DynamicBody);

                    // Optional: Add stability adjustments
                    structure.setLinearDamping(5.0f);
                    structure.setAngularDamping(5.0f);
                }
            }
            public void reduceHealth(int damage) {
                health -= damage;
                System.out.println("Block health: " + health);
            }

            public boolean isDestroyed() {
                return health <= 0;
            }
            public void render() {
                batch.draw(texture, body.getPosition().x * 100f - texture.getWidth() / 2f,
                    body.getPosition().y * 100f - texture.getHeight() / 2f);
            }

            public Body getBody() {
                return body;
            }
            public void dispose() {
                texture.dispose();
            }

            @Override
            public void endContact(Contact contact) {
                // Not needed for this functionality
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
                // Not needed for this functionality
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
                // Not needed for this functionality
            }
        });
    }

}
