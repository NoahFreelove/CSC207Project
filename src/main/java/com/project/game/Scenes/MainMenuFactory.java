package com.project.game.Scenes;

import com.project.engine.Core.Engine;
import com.project.engine.Core.GameObject;
import com.project.engine.Core.Scene;
import com.project.engine.Rendering.SpriteRenderer;
import com.project.engine.UI.GameUIButton;
import com.project.engine.UI.GameUILabel;
import com.project.game.Scripts.SceneExit;

import javax.swing.*;

public class MainMenuFactory {
    public static Scene createScene() {
        Scene scene = new Scene();

        GameObject escapeDetector = new GameObject();
        escapeDetector.addBehavior(new SceneExit(MainMenuFactory::leaveGame));

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
        // Game Label
        GameUILabel title = new GameUILabel("", 75, 100, 600, 300);
        title.setImage("ui/CSC207_asset_game_title.png");
        // Play Button
        GameUIButton play = new GameUIButton("", 295, 350, 210, 80);
        play.setImage("ui/CSC207_asset_play_game.png", 190, 100);
        play.setTransparent(true);
        play.setHorizontalAlignment(SwingConstants.LEADING);

        play.onClickEvent = LevelSelectionFactory::loadLevelSelection;

        // Leave Button
        GameUIButton leave = new GameUIButton("", 295, 400, 210, 80);
        leave.setImage("ui/CSC207_asset_leave_game.png", 190, 100);
        leave.setTransparent(true);
        leave.setHorizontalAlignment(SwingConstants.LEADING);

         leave.onClickEvent = MainMenuFactory::leaveGame;

        // Adding everything to the scene
        scene.addSceneObject(escapeDetector, true);
        scene.addSceneObject(bg);
        scene.addSceneObject(cloud);
        scene.addUIElement(title);
        scene.addUIElement(play);
        scene.addUIElement(leave);
        return scene;
    }

    public static void leaveGame() {
        Engine.getInstance().exitEngine();
    }

    public static void loadMainMenu() {
        Engine.getInstance().getPrimaryWindow().setWindowSizeForce(800, 800);
        Scene s = MainMenuFactory.createScene();
        Engine.getInstance().getPrimaryWindow().setActiveScene(s);
    }
}