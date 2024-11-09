package com.project.engine.Scripting;

import com.project.engine.Core.GameObject;

public interface ILambdaTrigger {
    default void onTriggerEnter(GameObject parent, GameObject other){}
    default void onTriggerExit(GameObject parent, GameObject other){}
    default void onTriggerContinue(GameObject parent, GameObject other){}
}
