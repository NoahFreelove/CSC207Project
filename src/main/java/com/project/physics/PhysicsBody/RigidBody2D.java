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

    public boolean grounded = false;

    public boolean checkGrounded() {
        // Requires collision, coming soon.
        return grounded;
    }

    /**
     * Make sure you know what you are doing when you call this explicitly.
     * @param forceX Force in X direction
     * @param forceY Force in Y direction
     */
    public void addForce(double forceX, double forceY) {
        currForceX = forceX;
        currForceY = forceY;
    }

    public void update(GameObject parent, double deltaTime) {
        double friction = 0f;

        if(attribs.gravityEnabled && !checkGrounded()) {
            currForceY += attribs.gravityForce;
            if (velocityY > 0) {
                currForceY *= attribs.fallMultiplier;
            }
            else if (velocityY < 0) {
                currForceY = currForceY * attribs.fallMultiplier;
            }
        }
        else if (checkGrounded()) {
            velocityY = Math.min(0, velocityY);
        }

        if (checkGrounded() && attribs.groundFrictionEnabled) {
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

        System.out.println(currForceX);

        velocityY += currForceY / attribs.mass * deltaTime;

        parent.getTransform().translate(velocityX * deltaTime, velocityY * deltaTime);

        currForceX = 0;
        currForceY = 0;
    }

    @Override
    public Class<?> attachedClass() {
        return getClass();
    }

    @Override
    public void deserialize(JSONObject data) {
        throw new RuntimeException(new Exception("Not Implemented"));
    }

    @Override
    public JSONObject serialize() {
        throw new RuntimeException(new Exception("Not Implemented"));
    }
}
