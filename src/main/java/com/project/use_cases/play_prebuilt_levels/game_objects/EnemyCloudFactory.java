package com.project.use_cases.play_prebuilt_levels.game_objects;

import com.project.entity.core.GameObject;
import com.project.entity.physics.collision.CollisionVolume;
import com.project.entity.physics.collision.types.ECollisionVolume;
import com.project.entity.rendering.SpriteRenderer;
import com.project.entity.scripting.ILambdaTrigger;
import com.project.use_cases.player_death_count.PlayerDeath;
import com.project.use_cases.play_prebuilt_levels.scripts.SimpleTrigger;

public class EnemyCloudFactory extends AbstractObjectFactory {
    protected EnemyCloudFactory() {
        super();
    }

    @Override
    protected GameObject produceGameObject(double x, double y, int z, double width, double height) {
        GameObject obj = super.produceGameObject(x, y, z, width, height);
        SpriteRenderer cloudRenderer = new SpriteRenderer("assets/cloud.png", 128, 128);
        obj.addRenderable(cloudRenderer);

        SimpleTrigger deathCollider = new SimpleTrigger(new ILambdaTrigger() {
            @Override
            public void onReset() {
                cloudRenderer.setImage("assets/cloud.png", 128, 128);
            }

            @Override
            public void onTriggerContinue(GameObject parent, GameObject other, CollisionVolume interactor) {
                if(other.hasTag("player") && interactor.volumeType() == ECollisionVolume.COLLIDER) {
                    PlayerDeath pd = other.getScriptable(PlayerDeath.class);
                    if (pd != null) {
                        pd.queueDeath();
                        cloudRenderer.setImage("assets/enemy_cloud.png", 128, 128);
                    }
                    else {
                        System.err.println("No spawn point found");
                    }
                }



            }
        });
        deathCollider.setRelDimensions(0.7,0.9);
        deathCollider.setOffset(128/2f -16 , 0);
        obj.addBehavior(deathCollider);

        return obj;
    }
}
