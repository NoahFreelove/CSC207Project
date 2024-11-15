package com.project.game.Scenes;

import com.project.engine.Core.Engine;
import com.project.engine.Core.GameObject;
import com.project.engine.Core.Scene;
import com.project.engine.Core.Tuple;
import com.project.engine.Core.Window.GameWindow;
import com.project.engine.Rendering.SpriteRenderer;
import com.project.engine.UI.GameUIButton;
import com.project.engine.UI.GameUILabel;
import com.project.engine.UI.GameUIPanel;
import com.project.game.ObjectFactories.AbstractObjectFactory;
import com.project.game.ObjectFactories.ObjectType;
import com.project.game.Scenes.Levels.EasyLevel;
import com.project.game.Scripts.SceneExit;
import com.project.game.UIFactories.UIFactory;
import com.project.engine.Core.GameObject;

import javax.swing.*;
import java.util.ArrayList;

import static com.project.engine.UI.UIConstants.LIGHT_GREEN;

public class PauseOverlayFactory {
    // public static GameObject pauseBackground = AbstractObjectFactory.generateOfType(ObjectType.PAUSE_BACKGROUND);

    public static void createPauseOverlay(Scene scene) {

        // Background

        // Label
        GameUILabel label = UIFactory.LabelFactory("Game Paused", 125, 40, 550, 200, LIGHT_GREEN);

        //Buttons
        GameUIButton resume = UIFactory.ButtonFactory("Resume Game", 250, 300, 300, 80, LIGHT_GREEN);
        GameUIButton exit = UIFactory.ButtonFactory("Exit Game", 265, 400, 270, 80, LIGHT_GREEN);

        // TODO: make resume game button more dynamic
        resume.onClickEvent = PauseOverlayFactory::unpauseGame;

        exit.onClickEvent = LevelSelectionFactory::loadLevelSelection;

        scene.addUIElements(resume, exit, label);

    }

    public static void pauseGame() {
        loadPauseMenu();
    }

    public static void unpauseGame() {
        unloadPauseMenu();
    }

    public static void loadPauseMenu() {
        Scene s = Engine.getInstance().getPrimaryWindow().getActiveScene();
        createPauseOverlay(s);
        // pauseBackground.getScriptable(SpriteRenderer.class).setEnabled(true);
        Engine.getInstance().pauseGame();
    }

    public static void unloadPauseMenu() {
        Scene s = Engine.getInstance().getPrimaryWindow().getActiveScene();
        UIFactory.removePause(s);
        // pauseBackground.getScriptable(SpriteRenderer.class).setEnabled(false);
        Engine.getInstance().unpauseGame();
        Engine.getInstance().getPrimaryWindow().refocusInWindow();
    }

}
