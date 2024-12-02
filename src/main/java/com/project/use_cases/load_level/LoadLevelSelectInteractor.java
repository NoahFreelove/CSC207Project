package com.project.use_cases.load_level;

import com.project.entity.core.Scene;
import com.project.entity.core.Tuple;
import com.project.entity.ui.GameUI;
import com.project.use_cases.core.game.GameInteractor;
import com.project.use_cases.core.game.GameOutputData;
import com.project.use_cases.core.prebuilts.scenes.LevelSelectionFactory;
import com.project.use_cases.game_pause.GameUnpauseInteractor;
import com.project.use_cases.player_death_count.PlayerDeathInteractor;

import java.util.List;

public class LoadLevelSelectInteractor {
    public static void execute() {
        Tuple<GameInteractor, GameOutputData> out = GameInteractor.createAndWait();
        GameOutputData w = out.getSecond();

        GameUnpauseInteractor.execute();
        PlayerDeathInteractor.resetDeathCount();

        Scene s = LevelSelectionFactory.createScene();

        GameInteractor.getInstance().unpauseGame();
        w.setActiveScene(s);
    }
}
