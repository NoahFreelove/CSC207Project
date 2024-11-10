package com.project.game.Scripts;

import com.project.engine.Core.GameObject;
import com.project.engine.Scripting.ILambdaTrigger;
import com.project.engine.Scripting.IScriptable;
import com.project.physics.Collision.BoxTrigger;
import com.project.physics.Collision.CollisionVolume;

public class SimpleTrigger extends BoxTrigger implements IScriptable {
    private final ILambdaTrigger trigger;

    public SimpleTrigger() {
        trigger = new ILambdaTrigger() {};
    }

    public SimpleTrigger(ILambdaTrigger trigger) {
        this.trigger = trigger;
    }

    @Override
    public void onTriggerEnter(GameObject parent, GameObject other, CollisionVolume interactor) {
        trigger.onTriggerEnter(parent, other, interactor);
    }

    @Override
    public void onTriggerExit(GameObject parent, GameObject other, CollisionVolume interactor) {
        trigger.onTriggerExit(parent, other, interactor);
    }

    @Override
    public void onTriggerContinue(GameObject parent, GameObject other, CollisionVolume interactor) {
        trigger.onTriggerContinue(parent, other, interactor);
    }
}
