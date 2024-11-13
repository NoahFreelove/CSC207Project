package com.project.game.Scenes;

import com.project.engine.Core.Engine;
import com.project.engine.Core.GameObject;
import com.project.engine.Core.Scene;
import com.project.engine.Rendering.SpriteRenderer;
import com.project.engine.UI.FontCreator;
import com.project.engine.UI.GameUILabel;
import com.project.engine.UI.GameUIPanel;
import com.project.game.Scripts.SceneExit;

import javax.swing.*;
import java.awt.*;
import java.awt.AlphaComposite;

public class PauseMenuFactory {
    public static String colorcode = "0x007e12";
    public static String colorcodeOffset = "0x00b01c";
    public static Boolean paused = false;

    public static Scene createScene() {
        Scene scene = new Scene();

        GameObject escapeDetector = new GameObject();
        escapeDetector.addBehavior(new SceneExit(MainMenuFactory::leaveGame));

        // Background
        GameObject bg = new GameObject("Background");
        SpriteRenderer bgRenderer = new SpriteRenderer("ui/darken_bg.png", 800, 800);
        bg.getTransform().setPosition(0, 0);
        bg.addRenderable(bgRenderer);

        GameUILabel title = new GameUILabel("Game Paused", 125, 40, 550, 200);
        // title.setBorder(new BevelBorder(BevelBorder.LOWERED));
        title.setFont(FontCreator.createFont(80f));
        title.setForeground(Color.decode(colorcodeOffset));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        // Add to Scene
        scene.addSceneObject(bg);
        scene.addUIElement(title);

        return scene;
    }

    public static void pauseGame() {
        if (!paused) {
            paused = true;
            loadPauseMenu();
        }
        else {
            paused = false;
        }
    }

    public static void loadPauseMenu() {
        Engine.getInstance().getPrimaryWindow().setWindowSizeForce(800, 800);
        Scene s = PauseMenuFactory.createScene();
        Engine.getInstance().getPrimaryWindow().setActiveScene(s);
    }
}
