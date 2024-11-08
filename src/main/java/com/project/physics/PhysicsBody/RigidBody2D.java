package com.project.physics.PhysicsBody;

import com.project.engine.Core.GameObject;
import com.project.engine.Scripting.IScriptable;
import org.json.JSONObject;

/**
 * A rigid body for a game object.
 *
 * NOTE: You do NOT need to fully understand the math in this class,
 * to use it, simply plug and chug values until you find something that feels right.
 *
 * Please DO NOT edit any of the math in this class without consulting Theran.
 */
public class RigidBody2D implements IScriptable {
    public RigidBodyAttribs attribs = new RigidBodyAttribs();

    private double currForceX;
    private double currForceY;

    private double velocityX;
    private double velocityY;

    /**
     * Make sure you know what you are doing when you call this explicitly.
     * @param forceX Force in X direction
     * @param forceY Force in Y direction
     */
    public void addForce(double forceX, double forceY) {
        currForceX = forceX;
        currForceY = forceY;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void resetX() {
        currForceX = 0;
        velocityX = 0;
    }

    public void resetY() {
        currForceY = 0;
        velocityY = 0;
    }

    public void update(GameObject parent, double deltaTime) {
        double friction = 0f;

        if(attribs.gravityEnabled && !attribs.grounded) {
            currForceY += attribs.gravityForce;
            if (velocityY > 0) {
                currForceY *= attribs.fallMultiplier;
            }
            else if (velocityY < 0) {
                currForceY = currForceY * attribs.fallMultiplier;
            }
        }
        else if (attribs.grounded) {
            velocityY = Math.min(0, velocityY);
        }

        if (attribs.grounded && attribs.groundFrictionEnabled) {
            if (Math.abs(velocityX) > 0.5) {
                friction = attribs.groundFrictionCoefficient * attribs.mass * attribs.gravityForce;
                if (velocityX < 0) {
                    friction = -friction;
                }
            }
            else {
                friction = 0;
            }
        }

        velocityX += (currForceX - friction) / attribs.mass * deltaTime;
        velocityY += currForceY / attribs.mass * deltaTime;

        velocityX = Math.min(attribs.maxVelocityX, Math.abs(velocityX)) * Math.signum(velocityX);
        velocityY = Math.min(attribs.maxVelocityY, Math.abs(velocityY)) * Math.signum(velocityY);

        parent.getTransform().translate(velocityX * deltaTime, velocityY * deltaTime);

        currForceX = 0;
        currForceY = 0;
    }
}
