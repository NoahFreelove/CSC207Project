package com.project.app;

import com.project.use_cases.core.game.GameInteractor;
import com.project.entity.core.Tuple;
import com.project.use_cases.core.game.GameOutputData;
import com.project.use_cases.core.editor.LevelEditor;
import com.project.external_interfaces.player_death_count.PlayTTS;

public class GameQuickTest {
    public static void main(String[] args) {
        Tuple<GameInteractor, GameOutputData> out = GameInteractor.createAndWait();
        GameInteractor e = out.getFirst();
        GameOutputData w = out.getSecond();
        e.closeHook = PlayTTS::shutdown;
        LevelEditor.loadFromFileForMainGame("levels/level1.json");


    }
}
