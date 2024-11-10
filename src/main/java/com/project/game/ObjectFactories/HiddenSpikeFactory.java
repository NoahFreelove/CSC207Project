package com.project.game.ObjectFactories;

import com.project.engine.Core.GameObject;
import com.project.engine.Rendering.SpriteRenderer;
import com.project.engine.Scripting.ILambdaTrigger;
import com.project.game.Scripts.InterpolationMove;
import com.project.game.Scripts.SimpleCollider;
import com.project.game.Scripts.SimpleTrigger;
import com.project.game.Scripts.SpawnPoint;
import com.project.physics.Collision.CollisionVolume;
import com.project.physics.PhysicsBody.RigidBody2D;

public class HiddenSpikeFactory {

    public static GameObject generateHiddenSpike(int xPos, int yPos) {
        GameObject output = new GameObject();
        SpriteRenderer sr = new SpriteRenderer("assets/spike.png", 64, 64);
        sr.setEnabled(false);
        output.addRenderable(sr);
        InterpolationMove im = new InterpolationMove(xPos, yPos, 300);
        im.setTarget(xPos, yPos - 64);
        output.addBehavior(im);
        SimpleTrigger st = new SimpleTrigger(new ILambdaTrigger() {
            @Override
            public void onTriggerEnter(GameObject parent, GameObject other, CollisionVolume interactor) {
                if(other.hasTag("player") && interactor instanceof SimpleTrigger) {
                    im.setActive(true);
                    sr.setEnabled(true);
                }
            }
        });
        st.setOffset(0,-100);
        output.addBehavior(st);

        output.getTransform().translate(xPos, yPos);
        output.getTransform().setZIndex(5);
        output.addTag("spike");

        SimpleTrigger deathCollider = new SimpleTrigger(new ILambdaTrigger() {
            @Override
            public void onTriggerContinue(GameObject parent, GameObject other, CollisionVolume interactor) {
                if(other.hasTag("player")) {
                    SpawnPoint sp = other.getScriptable(SpawnPoint.class);
                    if (sp != null) {
                        sp.respawn();
                    }
                    else {
                        System.err.println("No spawn point found");
                    }
                }
            }
        });
        deathCollider.setRelDimensions(1,0.25);
        deathCollider.setOffset(0,48);
        output.addBehavior(deathCollider);

        return output;
    }
}
