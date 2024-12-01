package com.project.use_cases.prebuilts.game_objects;

import com.project.entity.core.GameObject;
import com.project.entity.physics.collision.CollisionVolume;
import com.project.entity.physics.collision.types.ECollisionVolume;
import com.project.entity.rendering.SpriteRenderer;
import com.project.entity.scripting.ILambdaTrigger;
import com.project.use_cases.player_death_count.PlayerDeathInteractor;
import com.project.use_cases.play_prebuilt_levels.scripts.SimpleTrigger;

public class SpikeFactory extends AbstractObjectFactory {
    protected SpikeFactory() {
        super();
    }

    @Override
    protected GameObject produceGameObject(double x, double y, int z, double width, double height) {
        GameObject obj = super.produceGameObject(x, y, z, width, height);
        obj.addTag("spike");

        SpriteRenderer sr = new SpriteRenderer("assets/spike.png", 64, 64);
        obj.addRenderable(sr);


        SimpleTrigger deathCollider = new SimpleTrigger(new ILambdaTrigger() {
            @Override
            public void onTriggerContinue(GameObject parent, GameObject other, CollisionVolume interactor) {
                if(other.hasTag("player") && interactor.volumeType() == ECollisionVolume.COLLIDER) {
                    PlayerDeathInteractor pd = other.getScriptable(PlayerDeathInteractor.class);
                    if (pd != null) {
                        pd.execute();
                    }
                    else {
                        System.err.println("No spawn point found");
                    }
                }
            }
        });
        deathCollider.setRelDimensions(0.4,0.4);
        deathCollider.setOffset(25,25);
        obj.addBehavior(deathCollider);

        return obj;
    }
}
