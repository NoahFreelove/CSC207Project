package com.project.game.Scenes;

import com.project.engine.Core.Engine;
import com.project.engine.Core.GameObject;
import com.project.engine.Core.Scene;
import com.project.engine.Core.Tuple;
import com.project.engine.Core.Window.GameWindow;
import com.project.engine.UI.GameUIButton;
import com.project.game.ObjectFactories.AbstractObjectFactory;
import com.project.game.ObjectFactories.CloudFactory;
import com.project.game.ObjectFactories.ObjectType;
import com.project.game.Scripts.SceneExit;
import com.project.game.UIFactories.UIFactory;

public class MainMenuFactory {

    public static Scene createScene() {
        Scene scene = new Scene();

        GameObject escapeDetector = new GameObject();
        escapeDetector.addBehavior(new SceneExit(MainMenuFactory::leaveGame));

        // Background
        scene.addSceneObject(AbstractObjectFactory.generateOfType(ObjectType.BACKGROUND));
        // Cloud
        CloudFactory cloud = (CloudFactory) AbstractObjectFactory.makeFactory(ObjectType.CLOUD);
        scene.addSceneObject(cloud.generate(300, 30, 3, 1.5));
        // Game Label
        UIFactory.LabelFactory("Froggy's", 212, 40, 375, 200);
        UIFactory.LabelFactory("Adventure", 175, 140, 450, 200);
        // Play Button
        GameUIButton play = UIFactory.ButtonFactory("Play Game", 265, 300, 270, 80);

        play.onClickEvent = LevelSelectionFactory::loadLevelSelection;

        // Leave Button
        GameUIButton leave = UIFactory.ButtonFactory("Leave game", 265, 400, 270, 80);

        leave.onClickEvent = MainMenuFactory::leaveGame;


        // Testing
        GameUIButton test = UIFactory.ButtonFactory("Test Game", 265, 500, 270, 80);

        test.onClickEvent = PauseMenuFactory::pauseGame;

        // Adding everything to the scene
        UIFactory.addToScene(scene);
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