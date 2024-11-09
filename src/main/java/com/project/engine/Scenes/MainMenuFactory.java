package com.project.engine.Scenes;

import com.project.engine.Core.GameObject;
import com.project.engine.Core.Scene;
import com.project.engine.Rendering.SpriteRenderer;

import java.awt.*;

public class MainMenuFactory {
    public static Scene createScene() {
        Scene scene = new Scene();

        // Background
        GameObject bg = new GameObject("Background");
        SpriteRenderer bgRenderer = new SpriteRenderer("assets/CSC207_asset_bg.png", 800, 800);
        bg.getTransform().setPosition(0, 0);
        bg.addRenderable(bgRenderer);
        // Play Button
        PlayButton play = new PlayButton(0, 400, 100, 50);
        play.setBackground(Color.decode("#ffffff"));
        // Leave Button
        ExitButton leave = new ExitButton(0, 400, 100, 50);
        leave.setBackground(Color.decode("#ffffff"));

        // Adding everything to the scene
        scene.addSceneObject(bg);
        scene.addUIElement(play);
        scene.addUIElement(leave);
        return scene;
    }
}