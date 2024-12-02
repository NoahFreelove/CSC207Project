package com.project.use_cases.game_reset;

import com.project.use_cases.core.prebuilts.scenes.LevelSelectionFactory;
import com.project.use_cases.game_pause.GamePauseState;
import com.project.use_cases.player_death_count.PlayerDeathInteractor;

public class LevelResetInteractor extends GamePauseState {
    public static void execute() {
        GamePauseState.setIsPaused(false);
        LevelSelectionFactory.reloadCurrentLevel();
        GamePauseState.setIsPaused(false);
        PlayerDeathInteractor.resetDeathCount();
    }
}
