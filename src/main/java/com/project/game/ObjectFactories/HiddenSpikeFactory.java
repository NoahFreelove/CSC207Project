package com.project.game.ObjectFactories;

import com.project.engine.Core.GameObject;
import com.project.engine.Rendering.SpriteRenderer;
import com.project.engine.Scripting.ILambdaTrigger;
import com.project.engine.Scripting.IScriptable;
import com.project.game.Scripts.InterpolationMove;
import com.project.game.Scripts.SimpleCollider;
import com.project.game.Scripts.SimpleTrigger;
import com.project.game.Scripts.SpawnPoint;
import com.project.game.Scripts.*;
import com.project.physics.Collision.CollisionVolume;
import com.project.physics.Collision.Types.ECollisionVolume;
import com.project.physics.PhysicsBody.RigidBody2D;

import javax.naming.spi.ObjectFactory;

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
        im.setTarget(x, y-63);
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
