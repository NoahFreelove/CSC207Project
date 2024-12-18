package com.project.entity.physics.physics_body;

import com.project.entity.core.GameObject;
import com.project.entity.scripting.IScriptable;
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

    public RigidBody2D() {}

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

            if (Math.abs(velocityY) > 0) {
                currForceY *= attribs.fallMultiplier;
            }

            currForceX *= attribs.airControlMultiplier;
        }
        else if (attribs.grounded) {
            velocityY = Math.min(0, velocityY);
        }

        if (attribs.grounded && attribs.groundFrictionEnabled) {
            if (Math.abs(velocityX) > 5) {
                friction = attribs.groundFrictionCoefficient * attribs.mass * attribs.gravityForce;

                if (velocityX < 0) {
                    friction = -friction;
                }
            }
            else if (Math.abs(velocityX) > 0) {
                friction = 0;
                resetX();
            }
            else {
                friction = 0;
            }
        }

        if (Math.abs(velocityX) > attribs.maxVelocityX && Math.signum(currForceX) == Math.signum(velocityX)) {
            velocityX -= friction / attribs.mass * deltaTime;
        }
        else {
            velocityX += (currForceX - friction) / attribs.mass * deltaTime;
        }
        if (Math.abs(velocityY) > attribs.maxVelocityY) {
            velocityY = attribs.maxVelocityY * Math.signum(velocityY);
        }
        else {
            velocityY += currForceY / attribs.mass * deltaTime;
        }

        parent.getTransform().translate(velocityX * deltaTime, velocityY * deltaTime);

        currForceX = 0;
        currForceY = 0;
    }

    @Override
    public JSONObject serialize() {
        return new JSONObject();
    }
}
