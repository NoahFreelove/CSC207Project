package com.project.engine.Scripting;

import com.project.engine.Core.GameObject;
import com.project.physics.Collision.BoxTrigger;
import com.project.physics.Collision.Trigger;
import com.project.physics.PhysicsBody.RigidBody2D;
import com.project.physics.PhysicsBody.RigidBodyAttribs;

public class GroundTrigger extends BoxTrigger implements IScriptable {
    public GroundTrigger(double xOff, double yOff, double widthOff, double heightOff) {
        setOffset(xOff, yOff);
        setRelDimensions(widthOff, heightOff);
    }

    @Override
    public void onTriggerEnter(GameObject parent, GameObject other) {
        if (other.getTag().equals("ground")){
            RigidBodyAttribs rba = parent.getScriptable(RigidBody2D.class).attribs;
            rba.grounded = true;
            rba.groundFrictionCoefficient = other.getScriptable(GroundStats.class).getFriction();
            System.out.println("Ground entered");
        }
    }

    @Override
    public void onTriggerExit(GameObject parent, GameObject other) {
        if (other.getTag().equals("ground")){
            parent.getScriptable(RigidBody2D.class).attribs.grounded = false;
            System.out.println("Ground exit");
        }
    }
}
