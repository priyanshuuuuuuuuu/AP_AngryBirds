package com.approject.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
public class VerticalWoodBlock extends WoodBlocks {

    public VerticalWoodBlock(SpriteBatch batch, Vector2 position, World world) {
        super(new Texture("woodVerticalBlock.png"), batch, position, world, 100);
    }

    @Override
    public void health() {
        System.out.println("Vertical Wood Block health: " + getBody().getUserData());
    }

    @Override
    public void initializeBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;  // Ensure dynamic body for falling
        bodyDef.position.set(position);
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(texture.getWidth() / 2 / 100f, texture.getHeight() / 2 / 100f);  // Adjust size

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;  // Reasonable density for wooden block
        fixtureDef.friction = 0.2f;  // Reduced friction for natural falling behavior
        fixtureDef.restitution = 0.1f; // Low restitution to avoid bouncing
        body.createFixture(fixtureDef);
        shape.dispose();

        // Optionally, check if gravity is applied (default is gravity(0, -9.8f))
        world.setGravity(new Vector2(0, -9.8f));  // Ensure gravity is set
    }
}
