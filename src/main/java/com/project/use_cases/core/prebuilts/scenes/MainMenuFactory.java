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
import com.project.use_cases.game_reset.LoadLevelSelectInteractor;

public class MainMenuFactory {
    public static Scene createScene() {
        Scene scene = new Scene();

        GameObject escapeDetector = new GameObject();
        escapeDetector.addBehavior(new SceneExit(MainMenuFactory::leaveGame));
        scene.addSceneObject(escapeDetector, true);

        // Background
        scene.addSceneObject(AbstractObjectFactory.generateOfType(ObjectType.BACKGROUND));
        // Cloud
        CloudFactory cloud = (CloudFactory) AbstractObjectFactory.makeFactory(ObjectType.CLOUD);
        scene.addSceneObject(cloud.generate(300, 30, 3, 1.5));
        // Game Label
        LabelOutputData title1 = UIFactory.LabelFactory("Froggy's", 212, 20, 375, 200);
        LabelOutputData title2 = UIFactory.LabelFactory("Adventure", 175, 120, 450, 200);
        // Play Button
        ButtonOutputData play = UIFactory.ButtonFactory("Play Game", 265, 280, 270, 80);
        play.setButtonCallback(LoadLevelSelectInteractor::execute);

        // Leave Button
        ButtonOutputData leave = UIFactory.ButtonFactory("Leave Game", 265, 380, 270, 80);
        leave.setButtonCallback(MainMenuFactory::leaveGame);

        // Editor Button
        ButtonOutputData editor = UIFactory.ButtonFactory("Level Editor", 200, 480, 400, 80);
        editor.setButtonCallback(() -> LevelEditorFactory.loadLevelEditor(GameInteractor.getInstance().getPrimaryWindow()));


        // Adding everything to the scene
        scene.addUIElements(new GameUI(play), new GameUI(leave), new GameUI(title1), new GameUI(title2), new GameUI(editor));
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