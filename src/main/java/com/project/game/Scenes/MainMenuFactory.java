package com.project.game.Scenes;

import com.project.engine.Core.Engine;
import com.project.engine.Core.GameObject;
import com.project.engine.Core.Scene;
import com.project.engine.Rendering.SpriteRenderer;
import com.project.engine.UI.FontCreator;
import com.project.engine.UI.GameUIButton;
import com.project.engine.UI.GameUILabel;
import com.project.game.ObjectFactories.AbstractObjectFactory;
import com.project.game.ObjectFactories.CloudFactory;
import com.project.game.ObjectFactories.ObjectType;
import com.project.game.Scripts.SceneExit;
import com.project.game.UIFactories.EngineFactory;
import com.project.game.UIFactories.UIFactory;

import javax.swing.*;
import java.awt.*;

import static com.project.engine.UI.UIConstants.FOREST_GREEN;

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
        Engine e = EngineFactory.createEngine();
        Scene s = MainMenuFactory.createScene();
        e.getPrimaryWindow().setActiveScene(s);
    }
}