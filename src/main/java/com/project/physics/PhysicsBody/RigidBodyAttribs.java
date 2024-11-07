package com.project.physics.PhysicsBody;

public class RigidBodyAttribs {
    /**
     * Constants for a rigid body.
     * These can be overridden if required but they are
     * good benchmarks for what 'feels good' for movement.
     */
    public boolean gravityEnabled = true;
    boolean airFrictionEnabled;
    boolean groundFrictionEnabled = true;

    private boolean grounded;
    private boolean isGround;

    public float gravityForce = 350f;
    public float fallMultiplier = 13f;
    public double maxForceX = 1000;
    public double maxForceY = 1000;

    public double groundFrictionCoefficient = 0.73f;
    public double mass = 6;
}
