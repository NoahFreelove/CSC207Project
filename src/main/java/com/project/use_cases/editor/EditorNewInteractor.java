package com.project.use_cases.editor;

import com.project.use_cases.core.editor.LevelEditor;
import com.project.use_cases.core.game.GameInteractor;

public class EditorNewInteractor {
    public static void execute(LevelEditor scene) {
        scene.newFile();
        GameInteractor.getInstance().getPrimaryWindow().refocusInWindow();
    }
}
