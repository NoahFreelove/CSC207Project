package com.project.game.ObjectFactories;

import com.project.engine.Core.GameObject;
import com.project.engine.Core.Tuple;
import com.project.engine.Rendering.SpriteRenderer;
import com.project.engine.Scripting.ILambdaTrigger;
import com.project.game.Scripts.*;
import com.project.physics.Collision.CollisionVolume;
import com.project.physics.PhysicsBody.RigidBody2D;

import java.util.Timer;
import java.util.TimerTask;

public class HiddenSpikeFactory {
    public static GameObject generateHiddenSpike(int xPos, int yPos) {
        return generateHiddenSpike(xPos, yPos, 2);
    }
    public static GameObject generateHiddenSpike(int xPos, int yPos, int zIndex) {
        GameObject output = new GameObject();
        SpriteRenderer sr = new SpriteRenderer("assets/spike.png", 64, 64);
        sr.setEnabled(false);
        output.addRenderable(sr);
        InterpolationMove im = new InterpolationMove(xPos, yPos, 500);
        im.setTarget(xPos, yPos - 64);
        output.addBehavior(im);

        SimpleTrigger triggerZone = new SimpleTrigger(new ILambdaTrigger() {
            @Override
            public void onTriggerEnter(GameObject parent, GameObject other, CollisionVolume interactor) {
                if(other.hasTag("player") && interactor instanceof SimpleTrigger) {
                    im.setActive(true);
                    sr.setEnabled(true);
                }
            }
        });
        triggerZone.setRelDimensions(2,1d);
        triggerZone.setOffset(-64,-100);
        output.addBehavior(triggerZone);

        output.getTransform().translate(xPos, yPos);
        output.getTransform().setZIndex(zIndex);
        output.addTag("spike");

        boolean[] triggered = new boolean[1];
        SimpleTrigger deathCollider = new SimpleTrigger(new ILambdaTrigger() {
            @Override
            public void onTriggerContinue(GameObject parent, GameObject other, CollisionVolume interactor) {
                if(other.hasTag("player")) {
                    if (triggered[0]) {
                        return;
                    }
                    SpawnPoint sp = other.getScriptable(SpawnPoint.class);
                    if (sp != null) {
                        Tuple<Double, Double> death_pt = new Tuple<>(other.getTransform().getPositionX(), other.getTransform().getPositionY() - 300);
                        other.getTransform().setPosition(death_pt);
                        other.getScriptable(RigidBody2D.class).resetY();
                        other.getScriptable(RigidBody2D.class).resetX();
                        other.getScriptable(RigidBody2D.class).addForce(0, 1.15f*-99600);
                        other.getTransform().setScaleX(2.0);
                        Timer timer = new Timer();
                        MovementController mc = other.getScriptable(MovementController.class);
                        if (mc != null) {
                            mc.setCanMove(false);
                            mc.setCanJump(false);
                        }
                        other.removeTag("player"); // dont trigger other events when dead
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                // Execute the delayed actions
                                sp.respawn();


                                // Cancel the timer after execution to clean up
                                timer.cancel();
                                other.getTransform().setScaleX(1.0);
                            }
                        }, 1420);
                        triggered[0] = true;
                    }
                    else {
                        System.err.println("No spawn point found");
                    }
                }
            }

            @Override
            public void onTriggerExit(GameObject parent, GameObject other, CollisionVolume interactor) {
                if(other.hasTag("player")) {
                    triggered[0] = false;
                    //im.setActive(false);
                    //output.getTransform().translate(0,64);
                    //sr.setEnabled(false);
                }
            }
        });
        deathCollider.setRelDimensions(1,0.25);
        deathCollider.setOffset(0,48);
        output.addBehavior(deathCollider);

        return output;
    }
}
