package com.project.engine.Scripting;

import com.project.engine.Core.GameObject;
import com.project.physics.Collision.BoxTrigger;

public class SimpleTrigger extends BoxTrigger implements IScriptable {
    public void onTriggerEnter(GameObject parent, GameObject other) {}
}
