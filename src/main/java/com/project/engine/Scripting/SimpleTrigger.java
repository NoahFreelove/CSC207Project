package com.project.engine.Scripting;

import com.project.engine.Core.GameObject;
import com.project.physics.Collision.BoxTrigger;

public class SimpleTrigger extends BoxTrigger implements IScriptable {
    private final ILambdaTrigger trigger;

    public SimpleTrigger() {
        trigger = new ILambdaTrigger() {};
    }

    public SimpleTrigger(ILambdaTrigger trigger) {
        this.trigger = trigger;
    }

    @Override
    public void onTriggerEnter(GameObject parent, GameObject other) {
        trigger.onTriggerEnter(parent, other);
    }

    @Override
    public void onTriggerExit(GameObject parent, GameObject other) {
        trigger.onTriggerExit(parent, other);
    }

    @Override
    public void onTriggerContinue(GameObject parent, GameObject other) {
        trigger.onTriggerContinue(parent, other);
    }
}
