package com.project.use_cases.core.prebuilts.scripts;

import com.project.entity.core.GameObject;
import com.project.entity.input.EInputType;
import com.project.entity.scripting.IScriptable;
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
