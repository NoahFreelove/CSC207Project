package com.project.engine.Scenes;

import com.project.engine.Core.Engine;
import com.project.engine.Core.GameObject;
import com.project.engine.Core.Scene;
import com.project.engine.EngineMain;
import com.project.engine.Rendering.SpriteRenderer;
import com.project.engine.UI.GameUIButton;
import com.project.game.editor.LevelEditor;

import javax.swing.*;

public class LevelSelectionFactory {
    public static Scene createScene() {
        Scene scene = new Scene();

        // Background
        GameObject bg = new GameObject("Background");
        SpriteRenderer bgRenderer = new SpriteRenderer("assets/CSC207_asset_bg.png", 800, 800);
        bg.getTransform().setPosition(0, 0);
        bg.addRenderable(bgRenderer);
        // Cloud
        GameObject cloud = new GameObject("Cloud");
        SpriteRenderer cloudRenderer = new SpriteRenderer("assets/CSC207_asset_cloud.png", 800, 800);
        cloud.getTransform().setPosition(200, 40);
        cloud.getTransform().setZIndex(1);
        cloud.getTransform().setScaleX(0.5);
        cloud.getTransform().setScaleY(0.2);
        cloud.addRenderable(cloudRenderer);
        // Level 1 button
        GameUIButton level1 = new GameUIButton("", 295, 250, 210, 80);
        level1.setImage("ui/CSC207_asset_level1.png", 190, 100);
        level1.setTransparent(true);
        level1.setHorizontalAlignment(SwingConstants.LEADING);

        level1.onClickEvent = EngineMain::loadTestScene;

        // Level 2 button
        GameUIButton level2 = new GameUIButton("", 295, 350, 210, 80);
        level2.setImage("ui/CSC207_asset_level2.png", 190, 100);
        level2.setTransparent(true);
        level2.setHorizontalAlignment(SwingConstants.LEADING);

        // Back button
        GameUIButton back = new GameUIButton("", 295, 450, 210, 80);
        // TODO: change to leave button
        back.setImage("ui/CSC207_asset_leave_game.png", 190, 100);
        back.setTransparent(true);
        back.setHorizontalAlignment(SwingConstants.LEADING);
        back.onClickEvent = MainMenuFactory::loadMainMenu;

        // Adding everything
        scene.addSceneObject(bg);
        scene.addSceneObject(cloud);
        scene.addUIElement(level1);
        scene.addUIElement(level2);
        scene.addUIElement(back);
        return scene;
    }

    public static void loadLevelSelection() {
        Engine.getInstance().getPrimaryWindow().setWindowSizeForce(800, 800);
        Scene s = LevelSelectionFactory.createScene();
        Engine.getInstance().getPrimaryWindow().setActiveScene(s);
    }
}
