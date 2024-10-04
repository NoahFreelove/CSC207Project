package com.project.engine.Scripting;

import com.project.engine.Input.EInputType;

public interface IScriptable {
    void start();
    void update(double deltaTime);
    void onInput(String keyName, EInputType inputType);
}
