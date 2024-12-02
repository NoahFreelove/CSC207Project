package com.project.app;

import com.project.use_cases.core.game.GameInteractor;
import com.project.entity.core.Tuple;
import com.project.use_cases.core.game.GameOutputData;
import com.project.use_cases.core.editor.LevelEditor;
import com.project.external_interfaces.player_death_count.PlayTTS;
import com.project.use_cases.load_level.LoadLevelInteractor;

public class GameQuickTest {
    public static void main(String[] args) {
        Tuple<GameInteractor, GameOutputData> out = GameInteractor.createAndWait();
        GameInteractor e = out.getFirst();
        GameOutputData w = out.getSecond();
        e.closeHook = PlayTTS::shutdown;
        LoadLevelInteractor.execute("levels/level1.json");


    }
}
