package com.project.engine.Scripting;

import com.project.engine.Core.GameObject;
import com.project.engine.Physics.Collision.CollisionVolume;

public interface ILambdaTrigger {
    default void onTriggerEnter(GameObject parent, GameObject other, CollisionVolume interactor){}
    default void onTriggerExit(GameObject parent, GameObject other, CollisionVolume interactor){}
    default void onTriggerContinue(GameObject parent, GameObject other, CollisionVolume interactor){}
    default void onReset(){}
}
