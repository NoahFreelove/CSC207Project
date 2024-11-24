package com.project.game.Scripts;

import com.project.engine.Core.GameObject;
import com.project.engine.Input.EInputType;
import com.project.engine.Scripting.IScriptable;
import org.json.JSONObject;

public class SceneExit implements IScriptable {

    public Runnable onExit = () -> {};

    public SceneExit() {}

    public SceneExit(Runnable onExit) {
        this.onExit = onExit;
    }

    public void setOnExit(Runnable onExit) {
        this.onExit = onExit;
    }

    @Override
    public void onInput(GameObject parent, String keyName, EInputType inputType, int inputMods) {
        if (inputType == EInputType.RELEASE && "ESC".equals(keyName)) {
            onExit.run();
        }
    }

    @Override
    public JSONObject serialize() {
        return new JSONObject();
    }
}
