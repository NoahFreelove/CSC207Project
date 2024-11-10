package com.project.game.Scenes;

import com.project.engine.Core.Engine;
import com.project.engine.Core.GameObject;
import com.project.engine.Core.Scene;
import com.project.engine.Rendering.SpriteRenderer;
import com.project.engine.UI.FontCreator;
import com.project.engine.UI.GameUIButton;
import com.project.engine.UI.GameUILabel;
import com.project.game.Scripts.SceneExit;
import jdk.dynalink.beans.BeansLinker;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

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
        cloud.getTransform().setPosition(250, 40);
        cloud.getTransform().setZIndex(1);
        cloud.getTransform().setScaleX(0.5);
        cloud.getTransform().setScaleY(0.2);
        cloud.addRenderable(cloudRenderer);
        // Game Label
        GameUILabel title = new GameUILabel("Froggy's", 212, 40, 375, 200);
        // title.setBorder(new BevelBorder(BevelBorder.LOWERED));
        title.setFont(FontCreator.createFont(80f));
        title.setForeground(Color.decode("0x00b119"));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        GameUILabel title2 = new GameUILabel("Adventure", 175, 140, 450, 200);
        // title2.setBorder(new BevelBorder(BevelBorder.LOWERED));
        title2.setFont(FontCreator.createFont(80f));
        title2.setForeground(Color.decode("0x00b119"));
        title2.setHorizontalAlignment(SwingConstants.CENTER);
        // Play Button
        GameUIButton play = new GameUIButton("Play Game", 265, 300, 270, 80);
        play.setFont(FontCreator.createFont(40f));
        play.setForeground(Color.decode("0x00b119"));
        play.setHorizontalAlignment(SwingConstants.CENTER);
        play.setTransparent(true);

        play.onClickEvent = LevelSelectionFactory::loadLevelSelection;

        // Leave Button
        GameUIButton leave = new GameUIButton("Leave game", 265, 400, 270, 80);
        leave.setFont(FontCreator.createFont(40f));
        leave.setForeground(Color.decode("0x00b119"));
        leave.setHorizontalAlignment(SwingConstants.CENTER);
        leave.setTransparent(true);

         leave.onClickEvent = MainMenuFactory::leaveGame;

        // Adding everything to the scene
        scene.addSceneObject(escapeDetector, true);
        scene.addSceneObject(bg);
        scene.addSceneObject(cloud);
        scene.addUIElement(title);
        scene.addUIElement(title2);
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