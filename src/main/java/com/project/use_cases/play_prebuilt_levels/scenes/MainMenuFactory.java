package com.project.use_cases.play_prebuilt_levels.scenes;

import com.project.entity.ui.GameUI;
import com.project.use_cases.general.GameInteractor;
import com.project.entity.core.GameObject;
import com.project.entity.core.Scene;
import com.project.entity.core.Tuple;
import com.project.use_cases.general.GameOutputData;
import com.project.use_cases.ui.label.LabelOutputData;
import com.project.view.ui.GameLabelView;
import com.project.use_cases.prebuilts.game_objects.*;
import com.project.use_cases.play_prebuilt_levels.scripts.SceneExit;
import com.project.use_cases.prebuilts.game_ui.UIFactory;
import com.project.use_cases.level_editing.LevelEditorFactory;
import com.project.use_cases.prebuilts.game_objects.game_object_types.ObjectType;
import com.project.use_cases.ui.button.ButtonOutputData;

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
        play.setButtonCallback(LevelSelectionFactory::loadLevelSelection);

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