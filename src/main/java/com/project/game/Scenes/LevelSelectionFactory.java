package com.project.game.Scenes;

import com.project.engine.Core.Engine;
import com.project.engine.Core.GameObject;
import com.project.engine.Core.Scene;
import com.project.engine.Rendering.SpriteRenderer;
import com.project.engine.UI.FontCreator;
import com.project.engine.UI.GameUIButton;
import com.project.engine.UI.IOnClick;
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
        levelMap.put(0, EasyLevelFactory.createEasyLevel());
    }

    private static Scene createScene() {
        Scene scene = new Scene();

        GameObject escapeDetector = new GameObject();
        escapeDetector.addBehavior(new SceneExit(MainMenuFactory::loadMainMenu));

        // Background
        GameObject bg = new GameObject("Background");
        SpriteRenderer bgRenderer = new SpriteRenderer("assets/CSC207_asset_bg.png", 800, 800);
        bg.getTransform().setPosition(0, 0);
        bg.addRenderable(bgRenderer);
        // Level 1 button
//        GameUIButton level1 = new GameUIButton("Level 1", 295, 250, 210, 80);
//        level1.setFont(FontCreator.createFont(40f));
//        level1.setForeground(Color.decode("0x00b119"));
//        level1.setHorizontalAlignment(SwingConstants.CENTER);
//        level1.setTransparent(true);
//
//        level1.onClickEvent = EasyLevelFactory::loadEasyLevel;
//
//        // Level 2 button
//        GameUIButton level2 = new GameUIButton("Level 2", 295, 350, 210, 80);
//        level2.setFont(FontCreator.createFont(40f));
//        level2.setForeground(Color.decode("0x00b119"));
//        level2.setHorizontalAlignment(SwingConstants.CENTER);
//        level2.setTransparent(true);
//        level2.onClickEvent = EngineMain::loadTestScene;

        // Back button
        GameUIButton back = new GameUIButton("Back to Main", 200, 450, 400, 80);
        back.setFont(FontCreator.createFont(40f));
        back.setForeground(Color.decode(colorcode));
        back.setHorizontalAlignment(SwingConstants.CENTER);
        back.setTransparent(true);
        back.onClickEvent = MainMenuFactory::loadMainMenu;

        createLevel(1, scene);

        // Adding everything
        scene.addSceneObject(escapeDetector, true);
        scene.addSceneObject(bg);
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
