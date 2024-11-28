package com.project.app;

import com.project.use_cases.general.GameInteractor;
import com.project.entity.core.Scene;
import com.project.entity.core.Tuple;
import com.project.use_cases.general.GameOutputData;
import com.project.use_cases.play_prebuilt_levels.scenes.MainMenuFactory;
import com.project.external_interfaces.PlayTTS;

public class GameMain {
    public static void main(String[] args) {
        Tuple<GameInteractor, GameOutputData> out = GameInteractor.createAndWait();
        GameInteractor e = out.getFirst();
        GameOutputData w = out.getSecond();
        e.closeHook = PlayTTS::shutdown;

        Scene main = MainMenuFactory.createScene();
        w.setActiveScene(main);
    }
}   