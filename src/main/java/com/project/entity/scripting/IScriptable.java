package com.project.entity.scripting;

import com.project.entity.core.GameObject;
import com.project.entity.input.EInputType;
import com.project.entity.serialization.ISerializable;

public interface IScriptable extends ISerializable {
    default void start(GameObject parent) {}
    default void update(GameObject parent, double deltaTime) {}
    default void onInput(GameObject parent, String keyName, EInputType inputType, int inputMods) {}
    default void stop(GameObject parent) {}

    default void reset(GameObject parent){}

    default void pauseEvent(GameObject parent, boolean isPaused){}
}
