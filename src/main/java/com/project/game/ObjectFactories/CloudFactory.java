package com.project.game.ObjectFactories;

import com.project.engine.Core.GameObject;
import com.project.engine.Physics.Collision.CollisionVolume;
import com.project.engine.Physics.Collision.Types.ECollisionVolume;
import com.project.engine.Rendering.SpriteRenderer;
import com.project.engine.Scripting.ILambdaTrigger;
import com.project.game.Scripts.PlayerDeath;
import com.project.game.Scripts.SimpleTrigger;

import javax.naming.spi.ObjectFactory;

public class CloudFactory extends AbstractObjectFactory {
    protected CloudFactory() {
        super();
    }

    @Override
    protected GameObject produceGameObject(double x, double y, int z, double width, double height) {
        GameObject obj = super.produceGameObject(x, y, z, width, height);
        SpriteRenderer cloudRenderer = new SpriteRenderer("assets/CSC207_asset_cloud.png", 128, 128);
        obj.addRenderable(cloudRenderer);
        return obj;
    }

    protected GameObject produceGameObject(double x, double y, int z, double width, double height, boolean death) {
        GameObject obj = super.produceGameObject(x, y, z, width, height);
        SpriteRenderer cloudRenderer = new SpriteRenderer("assets/CSC207_asset_cloud.png", 128, 128);
        obj.addRenderable(cloudRenderer);

        SimpleTrigger deathCollider = new SimpleTrigger(new ILambdaTrigger() {
            @Override
            public void onTriggerContinue(GameObject parent, GameObject other, CollisionVolume interactor) {
                if(other.hasTag("player") && interactor.volumeType() == ECollisionVolume.COLLIDER) {
                    PlayerDeath pd = other.getScriptable(PlayerDeath.class);
                    if (pd != null) {
                        pd.queueDeath();
                    }
                    else {
                        System.err.println("No spawn point found");
                    }
                }
            }
        });
        deathCollider.setRelDimensions(1,0.4);
        deathCollider.setOffset(0,38.4);
        obj.addBehavior(deathCollider);

        return obj;
    }
}
