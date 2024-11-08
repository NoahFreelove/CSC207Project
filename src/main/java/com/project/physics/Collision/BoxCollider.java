package com.project.physics.Collision;

import com.project.engine.Core.GameObject;
import com.project.physics.Collision.Types.ECollisionVolume;
import com.project.physics.PhysicsBody.Transform;
import com.project.physics.Collision.Types.ECollisionShape;
import com.project.physics.PhysicsBody.RigidBody2D;

/**
 * Note, to use a Box Collider, the object must also have a rigid body.
 */
public class BoxCollider extends Collider {
    public ECollisionShape volumeShape() { return ECollisionShape.BOX; }


    @Override
    public void onCollisionEnter(GameObject parent, GameObject other, CollisionVolume interactor) {
        RigidBody2D rb = parent.getScriptable(RigidBody2D.class);

        if (rb == null) return;

        if(rb.attribs.isStatic) {
            return;
        }

        if (interactor.volumeType() != ECollisionVolume.COLLIDER) {
            return;
        }

        Transform t = parent.getTransform();
        Transform to = other.getTransform();


        if (t.getPositionY() + Math.abs(t.getHeight()) <= to.getPositionY() + 4){
            t.setPositionY(to.getPositionY() - Math.abs(t.getHeight()) - 0.01);
            rb.resetY();
        }
        else if (t.getPositionY() >= Math.abs(to.getHeight()) + to.getPositionY() - 2){
            t.setPositionY(to.getPositionY() + Math.abs(to.getHeight()) + 0.01);
            rb.resetY();
        }

        else if (t.getPositionX() + Math.abs(t.getWidth()) <= to.getPositionX() + 2) {
            t.setPositionX(to.getPositionX() - Math.abs(t.getWidth()) - 0.01);
            rb.resetX();
        }
        else if (t.getPositionX() >= to.getPositionX() + Math.abs(to.getWidth()) - 2) {
            t.setPositionX(to.getPositionX() + Math.abs(to.getWidth()) + 0.01);
            rb.resetX();
        }
    }

    public void onCollisionContinue(GameObject parent, GameObject other, CollisionVolume interactor) {
        RigidBody2D rb = parent.getScriptable(RigidBody2D.class);
        onCollisionEnter(parent, other, interactor);
    }
}
