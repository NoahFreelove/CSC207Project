package com.project.app;

import com.project.use_cases.core.game.GameInteractor;
import com.project.use_cases.core.game.GameOutputData;
import com.project.use_cases.core.editor.LevelEditorFactory;

public class EditorMain {
    public static void main(String[] args) {
        GameOutputData w = GameInteractor.getInstance().getPrimaryWindow();
        if (w == null)
            return;
        while (!w.isReady()){}
        LevelEditorFactory.loadLevelEditor(w);
    }
}
