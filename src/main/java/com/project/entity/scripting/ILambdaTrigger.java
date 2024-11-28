package com.project.entity.scripting;

import com.project.entity.physics.collision.CollisionVolume;
import com.project.entity.core.GameObject;

public interface ILambdaTrigger {
    default void onTriggerEnter(GameObject parent, GameObject other, CollisionVolume interactor){}
    default void onTriggerExit(GameObject parent, GameObject other, CollisionVolume interactor){}
    default void onTriggerContinue(GameObject parent, GameObject other, CollisionVolume interactor){}
    default void onReset(){}
}
