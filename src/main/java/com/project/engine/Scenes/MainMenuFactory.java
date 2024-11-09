package com.project.engine.Scenes;

import com.project.engine.Core.GameObject;
import com.project.engine.Core.Scene;
import com.project.engine.Core.Tuple;
import com.project.engine.Rendering.SpriteRenderer;
import com.project.engine.UI.GameUILabel;

import java.awt.*;

public class MainMenuFactory {
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
        // Game Label
        GameUILabel title = new GameUILabel("", 50, 100, 600, 300);
        title.setImage("ui/CSC207_asset_game_title.png");
        // Play Button
        PlayButton play = new PlayButton(300, 400, 150, 50);
        play.setBackground(Color.decode("#ffffff"));
        // Leave Button
        ExitButton leave = new ExitButton(300, 500, 150, 50);
        leave.setBackground(Color.decode("#ffffff"));

        // Adding everything to the scene
        scene.addSceneObject(bg);
        scene.addSceneObject(cloud);
        scene.addUIElement(title);
        scene.addUIElement(play);
        scene.addUIElement(leave);
        return scene;
    }
}