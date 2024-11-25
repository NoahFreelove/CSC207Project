package com.project.game.ObjectFactories;

import com.project.engine.Core.GameObject;
import com.project.engine.Physics.Collision.CollisionVolume;
import com.project.engine.Physics.Collision.Types.ECollisionVolume;
import com.project.engine.Physics.PhysicsBody.RigidBody2D;
import com.project.engine.Rendering.SpriteRenderer;
import com.project.engine.Scripting.ILambdaTrigger;
import com.project.game.Scripts.EnemyController;
import com.project.game.Scripts.SimpleCollider;
import com.project.game.Scripts.SimpleTrigger;
import com.project.game.Scripts.SpawnPoint;

public class CheckpointFactory extends AbstractObjectFactory {
    protected CheckpointFactory() {
        super();
    }

    @Override
    protected GameObject produceGameObject(double x, double y, int z, double width, double height) {
        GameObject obj = super.produceGameObject(x, y, z, width, height);
        obj.addTag("checkpoint");

        SpriteRenderer sr = new SpriteRenderer("assets/checkpoint.png", 64,64);
        sr.setIndependentOfCamera(false);
        sr.setEnabled(true);
        obj.addRenderable(sr);

        SimpleTrigger sc = new SimpleTrigger(new ILambdaTrigger() {
            @Override
            public void onTriggerEnter(GameObject parent, GameObject other, CollisionVolume interactor) {
                if (other.hasTag("player") && interactor.volumeType() == ECollisionVolume.COLLIDER) {
                    sr.setImage("assets/checkpointCleared.png", 64, 64);
                    SpawnPoint sp = other.getScriptable(SpawnPoint.class);
                    if (sp != null) {
                        sp.setSpawnPoint(parent.getTransform().getPositionX(), parent.getTransform().getPositionY() - 32);
                    }
                }
            }
        });
        obj.addBehavior(sc);
        return obj;
    }
}
