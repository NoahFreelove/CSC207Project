package com.project.use_cases.game_pause;

import com.project.use_cases.core.game.GameInteractor;

public class GamePauseState {
    private static Boolean isPaused = false;

    public static boolean isPaused() {
        return isPaused;
    }

    protected GamePauseState() {}

    protected static void setIsPaused(Boolean isPaused) {
        GamePauseState.isPaused = isPaused;

        if (isPaused) {
            GameInteractor.getInstance().pauseGame();
        }
        else {
            GameInteractor.getInstance().unpauseGame();
        }
    }
}
