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

        double x1 = t.getPositionX() + getXOffset(), y1 = t.getPositionY() + getYOffset();
        double x2 = to.getPositionX() + interactor.getXOffset(), y2 = to.getPositionY() + interactor.getYOffset();
        double w1 = t.getWidth() *  getRelWidth(), h1 = t.getHeight() * getRelHeight();
        double w2 = to.getWidth() * interactor.getRelWidth(), h2 = to.getHeight() * interactor.getRelHeight();


        if (y1 + h1 <= y2 + 4){
            t.setPositionY(y2 - h1 - 0.01 - getYOffset());
            rb.resetY();
        }
        else if (y1 >= h2 + y2 - 2){
            t.setPositionY(y2 + h2 + 0.01 - getYOffset());
            rb.resetY();
        }

        if (x1 + w1 <= x2 + 2) {
            t.setPositionX(x2 - w1 - 0.01 - getXOffset());
            rb.resetX();
        }
        else if (x1 >= x2 + w2 - 2) {
            t.setPositionX(x2 + w2 + 0.01 - getXOffset());
            rb.resetX();
        }
    }

    public void onCollisionContinue(GameObject parent, GameObject other, CollisionVolume interactor) {
        RigidBody2D rb = parent.getScriptable(RigidBody2D.class);
        onCollisionEnter(parent, other, interactor);
    }
}
