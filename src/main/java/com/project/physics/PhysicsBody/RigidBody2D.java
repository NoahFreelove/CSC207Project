package com.project.physics.PhysicsBody;

/**
 * A rigid body for a game object.
 *
 * NOTE: You do NOT need to fully understand the math in this class,
 * to use it, simply plug and chug values until you find something that feels right.
 *
 * Please DO NOT edit any of the math in this class without consulting Theran.
 */
public class RigidBody2D {
    private boolean gravityEnabled;
    private boolean airFrictionEnabled;
    private boolean groundFrictionEnabled;

    private boolean grounded;
    private boolean isGround;

    public void useGravity(boolean gravityOn) {
        this.gravityEnabled = gravityOn;
    }

    public void checkGrounded() {
        // Requires collision, coming soon.
    }

    /**
     * Make sure you know what you are doing when you call this explicitly.
     * @param forceX
     * @param forceY
     */
    public void addForce(double forceX, double forceY) {

    }

}
