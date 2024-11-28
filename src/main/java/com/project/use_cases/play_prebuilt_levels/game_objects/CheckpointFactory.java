package com.project.use_cases.play_prebuilt_levels.game_objects;

import com.project.entity.core.GameObject;
import com.project.entity.physics.collision.CollisionVolume;
import com.project.entity.physics.collision.types.ECollisionVolume;
import com.project.entity.rendering.SpriteRenderer;
import com.project.entity.scripting.ILambdaTrigger;
import com.project.use_cases.play_prebuilt_levels.scripts.SimpleTrigger;
import com.project.use_cases.play_prebuilt_levels.scripts.SpawnPoint;

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
