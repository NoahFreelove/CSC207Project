package com.project.use_cases.game_pause;

import com.project.entity.core.Scene;
import com.project.use_cases.core.game.GameInteractor;
import com.project.use_cases.core.prebuilts.ui.UIFactory;

public class GamePauseInteractor extends GamePauseState {

    public static void execute() {
        if (GamePauseState.isPaused())
            return;
        loadPauseMenu();
        GamePauseState.setIsPaused(true);
    }

    private static void loadPauseMenu() {
        Scene s = GameInteractor.getInstance().getPrimaryWindow().getActiveScene();
        UIFactory.createPauseOverlay(s);
    }
}
