package com.project.engine.Scripting;

import com.project.engine.Input.EInputType;

public interface IScriptable {
    default void start() {}
    default void update(double deltaTime) {}
    default void onInput(String keyName, EInputType inputType) {}
    default void stop() {}
}
