package com.project.engine.Physics.Collision;

import com.project.engine.Core.GameObject;
import com.project.engine.Physics.Collision.Types.ECollisionVolume;
import com.project.engine.Physics.PhysicsBody.Transform;
import com.project.engine.Physics.Collision.Types.ECollisionShape;
import com.project.engine.Physics.PhysicsBody.RigidBody2D;

/**
 * Note, to use a Box Collider, the object must also have a rigid body.
 */
public class BoxCollider extends Collider {
    private final double bufferSpace = 20;

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

        double x1 = t.getPositionX() + getXOffset(), y1 = t.getPositionY() + getYOffset();
        double x2 = to.getPositionX() + interactor.getXOffset(), y2 = to.getPositionY() + interactor.getYOffset();
        double w1 = Math.abs(t.getWidth()) *  getRelWidth(), h1 = Math.abs(t.getHeight()) * getRelHeight();
        double w2 = Math.abs(to.getWidth()) * interactor.getRelWidth(), h2 = Math.abs(to.getHeight()) * interactor.getRelHeight();


        if (y1 + h1 <= y2 + bufferSpace && rb.getVelocityY() >= 0){
            t.setPositionY(y2 - h1 - 0.01 - getYOffset());
            rb.resetY();
        }
        else if (x1 + w1 <= x2 + bufferSpace) {
            t.setPositionX(x2 - w1 - 0.01 - getXOffset());
            rb.resetX();
        }
        else if (x1 >= x2 + w2 - bufferSpace) {
            t.setPositionX(x2 + w2 + 0.01 - getXOffset());
            rb.resetX();
        }
        else if (y1 >= h2 + y2 - bufferSpace && rb.getVelocityY() < 0) {
            t.setPositionY(y2 + h2 + 0.01 - getYOffset());
            rb.resetY();
        }
    }

    public void onCollisionContinue(GameObject parent, GameObject other, CollisionVolume interactor) {
        RigidBody2D rb = parent.getScriptable(RigidBody2D.class);
        onCollisionEnter(parent, other, interactor);
    }
}
