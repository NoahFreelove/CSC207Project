package com.project.game.Scripts;

import com.project.engine.Core.GameObject;
import com.project.engine.Physics.Collision.BoxCollider;
import com.project.engine.Physics.Collision.CollisionVolume;
import com.project.engine.Physics.Collision.Types.ECollisionVolume;
import com.project.engine.Physics.PhysicsBody.RigidBody2D;
import com.project.engine.Scripting.IScriptable;
import com.sun.speech.freetts.en.us.FeatureProcessors;

public class EnemyController extends BoxCollider implements IScriptable {
    private int moveDirection = 1;
    private float moveSpeed = 1000;


    @Override
    public void update(GameObject parent, double deltaTime) {
        RigidBody2D rb = parent.getScriptable(RigidBody2D.class);

        if (rb != null) {
            rb.addForce(moveSpeed * moveDirection, 0);
        }
    }

    @Override
    public void onCollisionEnter(GameObject parent, GameObject other, CollisionVolume interactor) {
        super.onCollisionEnter(parent, other, interactor);

        RigidBody2D rb = parent.getScriptable(RigidBody2D.class);
        if (other.hasTag("player") && interactor.volumeType() == ECollisionVolume.COLLIDER) {
            if(parent.getTransform().getPositionY() - other.getTransform().getPositionY() >= 100){
                parent.disableScript(rb);
                moveDirection = 1;
                parent.getTransform().setPosition(-1000, -1000);
                return;
            }

            System.out.println("PLAYER");
            PlayerDeath pd = other.getScriptable(PlayerDeath.class);

            if (pd != null) {
                pd.queueDeath();
            }
        }


        if (other.hasTag("ground") && Math.abs(rb.getVelocityX()) <= 0.01) {
            moveDirection *= -1;

            if (moveDirection == -1) {
                parent.getTransform().faceLeft();
            }
            else{
                parent.getTransform().faceRight();
            }
        }
    }
}
