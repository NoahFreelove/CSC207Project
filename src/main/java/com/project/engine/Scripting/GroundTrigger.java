package com.project.engine.Scripting;

import com.project.engine.Core.GameObject;
import com.project.physics.Collision.BoxTrigger;
import com.project.physics.Collision.CollisionVolume;
import com.project.physics.PhysicsBody.RigidBody2D;
import com.project.physics.PhysicsBody.RigidBodyAttribs;

public class GroundTrigger extends BoxTrigger implements IScriptable {
    public GroundTrigger(double xOff, double yOff, double widthOff, double heightOff) {
        setOffset(xOff, yOff);
        setRelDimensions(widthOff, heightOff);
    }

    @Override
    public void onTriggerEnter(GameObject parent, GameObject other, CollisionVolume interactor) {
        RigidBody2D rb = parent.getScriptable(RigidBody2D.class);

        if (other.getTag().equals("ground") && rb.getVelocityY() >= 0){
            rb.attribs.grounded = true;
            rb.attribs.groundFrictionCoefficient = other.getScriptable(GroundStats.class).getFriction();
            System.out.println("Ground entered");
        }
    }

    @Override
    public void onTriggerExit(GameObject parent, GameObject other, CollisionVolume interactor) {
        if (other.getTag().equals("ground")){
            parent.getScriptable(RigidBody2D.class).attribs.grounded = false;
            System.out.println("Ground exit");
        }
    }
}
