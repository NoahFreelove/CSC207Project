package com.project.engine.Scripting;

import com.project.engine.Core.Engine;
import com.project.engine.Core.GameObject;
import com.project.engine.Core.Window.GameWindow;
import com.project.engine.Input.EInputType;
import org.json.JSONObject;

public class WindowStatsDebug implements IScriptable {
    private GameWindow target = Engine.getInstance().getPrimaryWindow();

    public WindowStatsDebug() {}

    public WindowStatsDebug(GameWindow target) {
        this.target = target;
    }

    @Override
    public void onInput(GameObject parent, String keyName, EInputType inputType, int inputMods) {
        if (inputType == EInputType.RELEASE && "F1".equals(keyName)) {
            target.fancyStatsPrint();
        }
    }

    @Override
    public Class<?> attachedClass() {
        return getClass();
    }

    @Override
    public JSONObject serialize() {
        return new JSONObject();
    }
}
