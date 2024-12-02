package com.project.use_cases.core.prebuilts.scripts;

import com.project.entity.core.GameObject;
import com.project.entity.input.EInputType;
import com.project.entity.scripting.IScriptable;
import com.project.use_cases.core.editor.LevelEditor;
import com.project.use_cases.core.game.GameInteractor;
import com.project.use_cases.core.game.GameOutputData;
import com.project.use_cases.core.prebuilts.scenes.LevelSelectionFactory;
import com.project.use_cases.game_pause.GamePauseInteractor;
import com.project.use_cases.load_level.LoadLevelInteractor;
import org.json.JSONObject;

public class SceneExit implements IScriptable {
    public Runnable onExit = new Runnable() {
        @Override
        public void run() {
            if (LevelSelectionFactory.isInEditor) {
                GameOutputData w = GameInteractor.getInstance().getPrimaryWindow();
                w.setWindowSizeForce(LevelEditor.levelEditorScreenSize.getFirst(), LevelEditor.levelEditorScreenSize.getSecond());
                w.setActiveScene(LoadLevelInteractor.getLevelEditor());
            } else {
                GamePauseInteractor.execute();
            }
        }
    };

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
