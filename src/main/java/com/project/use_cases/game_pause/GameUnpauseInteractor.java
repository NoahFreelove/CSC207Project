package com.project.use_cases.game_pause;

import com.project.entity.core.Scene;
import com.project.use_cases.core.game.GameInteractor;
import com.project.use_cases.core.prebuilts.ui.UIFactory;

public class GameUnpauseInteractor extends GamePauseState {
    public static void execute() {
        if(!GamePauseState.isPaused())
            return;
        unloadPauseMenu();
        GamePauseState.setIsPaused(false);
    }

    private static void unloadPauseMenu() {
        Scene s = GameInteractor.getInstance().getPrimaryWindow().getActiveScene();
        UIFactory.removePause(s);
        GameInteractor.getInstance().getPrimaryWindow().refocusInWindow();
    }
}
