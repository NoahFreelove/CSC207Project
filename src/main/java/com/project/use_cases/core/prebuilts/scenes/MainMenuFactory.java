package com.project.use_cases.core.prebuilts.scenes;

import com.project.entity.ui.GameUI;
import com.project.use_cases.core.game.GameInteractor;
import com.project.entity.core.GameObject;
import com.project.entity.core.Scene;
import com.project.entity.core.Tuple;
import com.project.use_cases.core.game.GameOutputData;
import com.project.use_cases.core.prebuilts.ui.types.label.LabelOutputData;
import com.project.use_cases.core.prebuilts.game_objects.*;
import com.project.use_cases.core.prebuilts.scripts.SceneExit;
import com.project.use_cases.core.prebuilts.ui.UIFactory;
import com.project.use_cases.core.editor.LevelEditorFactory;
import com.project.use_cases.core.prebuilts.game_objects.game_object_types.ObjectType;
import com.project.use_cases.core.prebuilts.ui.types.button.ButtonOutputData;

public class MainMenuFactory {

    public static Scene createScene() {
        Scene scene = new Scene();

        GameObject escapeDetector = new GameObject();
        escapeDetector.addBehavior(new SceneExit(MainMenuFactory::leaveGame));
        scene.addSceneObject(escapeDetector, true);

        UIFactory.generateMainMenuUI(scene);
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