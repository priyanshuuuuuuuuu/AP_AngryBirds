package com.approject.angrybirds;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
public class ProjectileMotion {
    private final ArrayList<Vector2> trajectoryPoints = new ArrayList<>();
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();
    private final float gravity = -9.81f;
    private Vector2 lastCalculatedVelocity;

    // Calculate the trajectory (unchanged)
    public void calculateTrajectory(Vector2 startPosition, Vector2 startVelocity, float timeStep, int numberOfPoints) {
        // Store the last calculated velocity
        lastCalculatedVelocity = startVelocity;

        // Clear existing points
        trajectoryPoints.clear();

        // Calculate trajectory points based on projectile motion equations
        for (int i = 0; i < numberOfPoints; i++) {
            float time = i * timeStep;
            float x = startPosition.x + startVelocity.x * time; // x = x0 + v0x * t
            float y = startPosition.y + startVelocity.y * time + 0.5f * gravity * time * time; // y = y0 + v0y * t + 0.5 * g * t^2
            trajectoryPoints.add(new Vector2(x, y));
        }
    }

    // Render the trajectory (unchanged)


    // Get the last calculated velocity (for launching the bird)
    public Vector2 getCalculatedVelocity() {
        return lastCalculatedVelocity;
    }
    public void renderTrajectory(Matrix4 cameraMatrix) {
        if (trajectoryPoints.isEmpty()) return;

        shapeRenderer.setProjectionMatrix(cameraMatrix);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);

        float pointRadius = 3f; // Radius for trajectory points
        int segments = 8; // Smoothness of the circles

        for (Vector2 point : trajectoryPoints) {
            shapeRenderer.circle(point.x, point.y, pointRadius, segments);
        }

        shapeRenderer.end();
    }

    /**
     * Clears all trajectory data.
     */
    public void clearTrajectory() {
        trajectoryPoints.clear();
    }

    /**
     * Disposes resources used by the shape renderer.
     */
    public void dispose() {
        shapeRenderer.dispose();
    }
}
