package com.project.app;

import com.project.use_cases.general.GameInteractor;
import com.project.use_cases.general.GameOutputData;
import com.project.use_cases.level_editing.LevelEditorFactory;

public class EditorMain {
    public static void main(String[] args) {
        GameOutputData w = GameInteractor.getInstance().getPrimaryWindow();
        if (w == null)
            return;
        while (!w.isReady()){}
        LevelEditorFactory.loadLevelEditor(w);
    }
}
