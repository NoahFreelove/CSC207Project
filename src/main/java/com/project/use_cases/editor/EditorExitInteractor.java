package com.project.use_cases.editor;

import com.project.use_cases.core.game.GameInteractor;
import com.project.use_cases.core.game.GameOutputData;
import com.project.use_cases.core.prebuilts.scenes.MainMenuFactory;

public class EditorExitInteractor {
    public static void execute() {
        GameOutputData w = GameInteractor.getInstance().getPrimaryWindow();
        if (w == null)
            return;

        w.setWindowSizeForce(800, 800);
        w.setActiveScene(MainMenuFactory.createScene());
    }
}
