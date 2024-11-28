package com.project.use_cases.play_prebuilt_levels.scenes;

import com.project.use_cases.general.GameInteractor;
import com.project.entity.core.GameObject;
import com.project.entity.core.Scene;
import com.project.entity.core.Tuple;
import com.project.use_cases.general.GameOutputData;
import com.project.entity.ui.GameUIButton;
import com.project.entity.ui.GameUILabel;
import com.project.use_cases.play_prebuilt_levels.game_objects.*;
import com.project.use_cases.play_prebuilt_levels.scripts.SceneExit;
import com.project.use_cases.play_prebuilt_levels.ui.UIFactory;
import com.project.use_cases.level_editing.LevelEditorFactory;

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
        GameUILabel title1 = UIFactory.LabelFactory("Froggy's", 212, 20, 375, 200);
        GameUILabel title2 = UIFactory.LabelFactory("Adventure", 175, 120, 450, 200);
        // Play Button
        GameUIButton play = UIFactory.ButtonFactory("Play Game", 265, 280, 270, 80);
        play.onClickEvent = LevelSelectionFactory::loadLevelSelection;

        // Leave Button
        GameUIButton leave = UIFactory.ButtonFactory("Leave Game", 265, 380, 270, 80);
        leave.onClickEvent = MainMenuFactory::leaveGame;

        // Editor Button
        GameUIButton editor = UIFactory.ButtonFactory("Level Editor", 200, 480, 400, 80);
        editor.onClickEvent = () -> LevelEditorFactory.loadLevelEditor(GameInteractor.getInstance().getPrimaryWindow());


        // Adding everything to the scene
        scene.addUIElements(play, leave, title1, title2, editor);
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