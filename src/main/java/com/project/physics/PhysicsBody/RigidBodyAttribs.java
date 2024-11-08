package com.project.physics.PhysicsBody;

public class RigidBodyAttribs {
    /**
     * Constants for a rigid body.
     * These can be overridden if required but they are
     * good benchmarks for what 'feels good' for movement.
     */
    public boolean grounded = false;
    public boolean gravityEnabled = true;
    public boolean groundFrictionEnabled = true;

    /**
     * This flag is essential for objects which should not move.
     * This flag ensures that the game object does not move when collided with.
     */
    public boolean isStatic = false;

    public float gravityForce = 350f;
    public float fallMultiplier = 13f;
    public double maxVelocityX = 200;
    public double maxVelocityY = 2000;

    public double groundFrictionCoefficient = 0.73f;
    public double mass = 6;
}
