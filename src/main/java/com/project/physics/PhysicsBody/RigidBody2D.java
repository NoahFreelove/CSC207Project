package com.project.physics.PhysicsBody;

import com.project.engine.Core.GameObject;
import com.project.engine.Scripting.IScriptable;

/**
 * A rigid body for a game object.
 *
 * NOTE: You do NOT need to fully understand the math in this class,
 * to use it, simply plug and chug values until you find something that feels right.
 *
 * Please DO NOT edit any of the math in this class without consulting Theran.
 */
public class RigidBody2D implements IScriptable {
    private boolean gravityEnabled;
    private boolean airFrictionEnabled;
    private boolean groundFrictionEnabled;

    private boolean grounded;
    private boolean isGround;

    private double currForceX;
    private double currForceY;

    public void useGravity(boolean gravityOn) {
        this.gravityEnabled = gravityOn;
    }

    public void checkGrounded() {
        // Requires collision, coming soon.
    }

    /**
     * Make sure you know what you are doing when you call this explicitly.
     * @param forceX Force in X direction
     * @param forceY Force in Y direction
     */
    public void addForce(double forceX, double forceY) {
        currForceX += forceX;
        currForceY += forceY;
    }

    public void update(GameObject parent, double deltaTime) {
        
        parent.getTransform().translate(currForceX * deltaTime, currForceY * deltaTime);
    }
}
