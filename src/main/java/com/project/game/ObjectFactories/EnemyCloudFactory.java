package com.project.game.ObjectFactories;

import com.project.engine.Core.GameObject;
import com.project.engine.Physics.Collision.CollisionVolume;
import com.project.engine.Physics.Collision.Types.ECollisionVolume;
import com.project.engine.Rendering.SpriteRenderer;
import com.project.engine.Scripting.ILambdaTrigger;
import com.project.game.Scripts.PlayerDeath;
import com.project.game.Scripts.SimpleTrigger;

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
