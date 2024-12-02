package com.project.use_cases.game_win;

import com.project.entity.core.Scene;
import com.project.use_cases.core.prebuilts.ui.UIFactory;
import com.project.use_cases.game_pause.GamePauseState;

public class GameWinInteractor extends GamePauseState {
    public static void execute(Scene scene) {
        UIFactory.createWinOverlay(scene);
        GamePauseState.setIsPaused(true);
    }
}
