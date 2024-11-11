package com.project.engine.Scripting;

import com.project.engine.Core.GameObject;
import com.project.engine.Input.EInputType;
import com.project.engine.Serialization.ISerializable;

public interface IScriptable extends ISerializable {
    default void start(GameObject parent) {}
    default void update(GameObject parent, double deltaTime) {}
    default void onInput(GameObject parent, String keyName, EInputType inputType, int inputMods) {}
    default void stop(GameObject parent) {}

    default void reset(GameObject parent){}
}
