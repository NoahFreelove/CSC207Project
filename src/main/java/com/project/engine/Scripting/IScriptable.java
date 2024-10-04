package com.project.engine.Scripting;

import com.project.engine.Core.GameObject;
import com.project.engine.Input.InputMods;
import com.project.engine.Input.EInputType;

public interface IScriptable {
    default void start(GameObject parent) {}
    default void update(GameObject parent, double deltaTime) {}
    default void onInput(GameObject parent, String keyName, EInputType inputType, int inputMods) {}
    default void stop(GameObject parent) {}
}
