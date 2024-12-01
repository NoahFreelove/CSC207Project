package com.project.use_cases.editor;

import com.project.use_cases.core.editor.LevelEditor;
import com.project.use_cases.core.game.GameInteractor;
import com.project.use_cases.core.game.GameOutputData;
import com.project.use_cases.core.prebuilts.scenes.LevelSelectionFactory;
import com.project.use_cases.load_level.LoadLevelInteractor;

public class EditorTestInteractor {
    public static void execute(GameOutputData w, LevelEditor scene) {
        w.setWindowSizeForce(800, 800);
        w.setActiveScene(LoadLevelInteractor.loadLevelScene(true, scene));
        LevelSelectionFactory.isInEditor = true;
        GameInteractor.getInstance().getPrimaryWindow().refocusInWindow();
    }
}
