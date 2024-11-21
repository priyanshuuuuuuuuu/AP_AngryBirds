package com.approject.angrybirds;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Trajectory {
    private ShapeRenderer shapeRenderer;
    private World world;
    private Body birdBody;

    public Trajectory(World world) {
        this.world = world;
        shapeRenderer = new ShapeRenderer();
        createBirdBody();
    }

    private void createBirdBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        birdBody = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.5f, 0.5f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0.5f;

        birdBody.createFixture(fixtureDef);
        shape.dispose();
    }

    public void render(Vector2 start, Vector2 velocity) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 1, 1, 1);

        birdBody.setTransform(start, 0);
        birdBody.setLinearVelocity(velocity);

        Vector2 position = new Vector2(start);
        float timeStep = 0.1f;

        for (int i = 0; i < 100; i++) {
            world.step(timeStep, 6, 2);
            position.set(birdBody.getPosition());
            shapeRenderer.circle(position.x, position.y, 0.1f);
        }

        shapeRenderer.end();
    }

    public void dispose() {
        shapeRenderer.dispose();
        world.destroyBody(birdBody);
    }
}
