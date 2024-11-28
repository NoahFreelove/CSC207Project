package com.project.app;

import com.project.use_cases.general.GameInteractor;
import com.project.entity.core.Tuple;
import com.project.use_cases.general.GameOutputData;
import com.project.external_interfaces.FileIO;
import com.project.use_cases.level_editing.LevelEditor;
import com.project.external_interfaces.PlayTTS;

public class GameQuickTest {
    public static void main(String[] args) {
        Tuple<GameInteractor, GameOutputData> out = GameInteractor.createAndWait();
        GameInteractor e = out.getFirst();
        GameOutputData w = out.getSecond();
        e.closeHook = PlayTTS::shutdown;
        LevelEditor.loadFromFileForMainGame(FileIO.GetAbsPathOfResource("/levels/level1.json"));


    }
}
