package com.project.game.Scenes;

import com.project.engine.Core.Engine;
import com.project.engine.Core.GameObject;
import com.project.engine.Core.Scene;
import com.project.engine.Core.Tuple;
import com.project.engine.Core.Window.GameWindow;
import com.project.engine.UI.GameUI;
import com.project.engine.UI.GameUIButton;
import com.project.engine.UI.GameUILabel;
import com.project.engine.UI.IOnClick;
import com.project.game.ObjectFactories.AbstractObjectFactory;
import com.project.game.ObjectFactories.CloudFactory;
import com.project.game.ObjectFactories.ObjectType;
import com.project.game.Scripts.SceneExit;
import com.project.game.UIFactories.UIFactory;
import com.project.game.editor.LevelEditorFactory;

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
        editor.onClickEvent = () -> LevelEditorFactory.loadLevelEditor(Engine.getInstance().getPrimaryWindow());


        // Adding everything to the scene
        scene.addUIElements(play, leave, title1, title2, editor);
        return scene;
    }

    public static void leaveGame() {
        Engine.getInstance().exitEngine();
    }

    public static void loadMainMenu() {
        Tuple<Engine, GameWindow> out = Engine.createAndWait();
        GameWindow w = out.getSecond();

        Scene s = MainMenuFactory.createScene();
        w.setActiveScene(s);
    }
}