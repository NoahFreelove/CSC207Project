package com.project.game.Scenes;

import com.project.engine.Core.Engine;
import com.project.engine.Core.GameObject;
import com.project.engine.Core.Scene;
import com.project.engine.UI.FontCreator;
import com.project.engine.UI.GameUIButton;
import com.project.game.ObjectFactories.AbstractObjectFactory;
import com.project.game.ObjectFactories.ObjectType;
import com.project.game.Scenes.Levels.EasyLevel;
import com.project.game.Scripts.SceneExit;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

import static com.project.game.Scenes.MainMenuFactory.colorcode;

public class LevelSelectionFactory {

    public static int buttonXOffset = 56;
    public static int buttonXWidth = 210;
    public static int buttonYOffset = 100;

    private static HashMap<Integer, Scene> levelMap = new HashMap<>();

    static {
        levelMap.put(0, EasyLevel.loadEasyLevel());
    }

    private static Scene createScene() {
        Scene scene = new Scene();

        GameObject escapeDetector = new GameObject();
        escapeDetector.addBehavior(new SceneExit(MainMenuFactory::loadMainMenu));

        GameUIButton back = new GameUIButton("Back to Main", 200, 450, 400, 80);
        back.setFont(FontCreator.createFont(40f));
        back.setForeground(Color.decode(colorcode));
        back.setHorizontalAlignment(SwingConstants.CENTER);
        back.setTransparent(true);
        back.onClickEvent = MainMenuFactory::loadMainMenu;

        createLevel(2, scene);

        // Adding everything
        scene.addSceneObject(escapeDetector, true);
        scene.addSceneObject(AbstractObjectFactory.generateOfType(ObjectType.BACKGROUND));
//        scene.addUIElement(level1);
//        scene.addUIElement(level2);
        scene.addUIElement(back);
        return scene;
    }

    public static void loadLevelSelection() {
        Engine.getInstance().getPrimaryWindow().setWindowSizeForce(800, 800);
        Scene s = LevelSelectionFactory.createScene();
        Engine.getInstance().getPrimaryWindow().setActiveScene(s);
    }

    private static void createLevel(int levels, Scene scene) {
        int j = 0;
        int XReset = 0;
        for (int i = 0; i < levels; i++) {
            if (i % 3 == 0) {
                j += 1;
                XReset = 0;
            }
            GameUIButton temp = new GameUIButton("Level " + (i + 1), buttonXOffset + XReset * buttonXWidth, j * buttonYOffset, buttonXWidth, 80);
            temp.setFont(FontCreator.createFont(40f));
            temp.setForeground(Color.decode(colorcode));
            temp.setHorizontalAlignment(SwingConstants.CENTER);
            temp.setTransparent(true);
            int finalI = i;
            temp.onClickEvent = () -> Engine.getInstance().getPrimaryWindow().setActiveScene(levelMap.get(finalI));
            scene.addUIElement(temp);
            XReset++;
        }
    }
}
