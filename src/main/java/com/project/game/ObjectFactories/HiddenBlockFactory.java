package com.project.game.ObjectFactories;

import com.project.engine.Core.GameObject;
import com.project.engine.Rendering.SpriteRenderer;
import com.project.engine.Scripting.ILambdaTrigger;
import com.project.game.Scripts.GroundStats;
import com.project.game.Scripts.HiddenBlockScript;
import com.project.game.Scripts.SimpleCollider;
import com.project.game.Scripts.SimpleTrigger;
import com.project.physics.Collision.CollisionVolume;
import com.project.physics.PhysicsBody.RigidBody2D;

public class HiddenBlockFactory {

    public static GameObject generateHiddenBlock(int xPos, int yPos) {
        return generateHiddenBlock(xPos, yPos, false);
    }

    public static GameObject generateHiddenBlock(int xPos, int yPos, boolean debug) {
        GameObject output = new GameObject();
        output.getTransform().setPosition(xPos,yPos);
        output.getTransform().setZIndex(2);
        output.addBehavior(new GroundStats(0.5));
        SpriteRenderer sr = new SpriteRenderer("assets/brick.png", 64, 64);
        if(!debug) {
            sr.setEnabled(false);
        }
        output.addRenderable(sr);

        SimpleTrigger st = new SimpleTrigger(new ILambdaTrigger() {
            @Override
            public void onTriggerEnter(GameObject parent, GameObject other, CollisionVolume interactor) {
                if(other.hasTag("player") && interactor instanceof SimpleTrigger) {
                    //System.out.println(interactor.getTag());

                    RigidBody2D rb = other.getScriptable(RigidBody2D.class);
                    if (rb == null) {
                        System.err.println("RigidBody2D not found on player");
                        return;
                    }
                    if(rb.getVelocityY() < 0) {
                        //System.out.println("activate");
                        output.addBehavior(new SimpleCollider());
                        output.addTag("ground");
                        SimpleTrigger st = output.getScriptable(SimpleTrigger.class);
                        if (st == null) {
                            System.err.println("SimpleTrigger not found on hidden block");
                            return;
                        }
                        output.removeBehavior(st);
                        sr.setEnabled(true);

                        rb.resetY();
                    }
                }
            }
        });

        st.setRelDimensions(0.1d, 0.01);
        st.setOffset(32,50);

        output.addBehavior(st);

        output.addBehavior(new HiddenBlockScript());
        return output;
    }
}
