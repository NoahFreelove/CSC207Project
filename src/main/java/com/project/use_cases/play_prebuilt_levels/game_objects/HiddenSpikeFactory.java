package com.project.use_cases.play_prebuilt_levels.game_objects;

import com.project.entity.core.GameObject;
import com.project.entity.rendering.SpriteRenderer;
import com.project.entity.scripting.ILambdaTrigger;
import com.project.use_cases.play_prebuilt_levels.scripts.InterpolationMove;
import com.project.use_cases.play_prebuilt_levels.scripts.SimpleTrigger;
import com.project.entity.physics.collision.CollisionVolume;
import com.project.entity.physics.collision.types.ECollisionVolume;
import com.project.use_cases.player_death_count.PlayerDeath;

public class HiddenSpikeFactory extends AbstractObjectFactory {
    protected HiddenSpikeFactory() {
        super();
    }

    @Override
    protected GameObject produceGameObject(double x, double y, int z, double width, double height) {
        GameObject obj = super.produceGameObject(x, y, z, width, height);
        obj.addTag("spike");

        SpriteRenderer sr = new SpriteRenderer("assets/spike.png", 64, 64);
        sr.setEnabled(false);
        obj.addRenderable(sr);

        InterpolationMove im = new InterpolationMove(x, y, 300);
        im.setTarget(x, y-64);
        obj.addBehavior(im);

        SimpleTrigger st = new SimpleTrigger(new ILambdaTrigger() {
            @Override
            public void onTriggerEnter(GameObject parent, GameObject other, CollisionVolume interactor) {
                if(other.hasTag("player") && interactor.volumeType() == ECollisionVolume.COLLIDER) {
                    im.setActive(true);
                    sr.setEnabled(true);
                }
            }
        });
        st.setRelDimensions(1.15,0.45);
        st.setOffset(-4.8,-64 + 35.2);
        obj.addBehavior(st);

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
