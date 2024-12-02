package com.project.use_cases.core.prebuilts.scenes;

import com.project.use_cases.core.game.GameInteractor;
import com.project.entity.core.Scene;
import com.project.entity.core.Tuple;
import com.project.use_cases.core.game.GameOutputData;
import com.project.use_cases.core.prebuilts.ui.UIBuilder;

public class MainMenuFactory {
    public static Scene createScene() {
        Scene scene = new Scene();

        UIBuilder.generateMainMenuUI(scene);

        return scene;
    }

    public static void leaveGame() {
        GameInteractor.getInstance().exitEngine();
    }

    public static void loadMainMenu() {
        Tuple<GameInteractor, GameOutputData> out = GameInteractor.createAndWait();
        GameOutputData w = out.getSecond();

        Scene s = MainMenuFactory.createScene();
        w.setActiveScene(s);
    }
}