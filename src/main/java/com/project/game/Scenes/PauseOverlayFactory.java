package com.project.game.Scenes;

import com.project.engine.Core.Engine;
import com.project.engine.Core.Scene;
import com.project.engine.UI.GameUIButton;
import com.project.engine.UI.GameUILabel;
import com.project.game.UIFactories.UIFactory;

import static com.project.engine.UI.UIConstants.LIGHT_GREEN;

public class PauseOverlayFactory {
    // public static GameObject pauseBackground = AbstractObjectFactory.generateOfType(ObjectType.PAUSE_BACKGROUND);
    public static Boolean isPaused = false;

    public static void createPauseOverlay(Scene scene) {

        // Background

        // Label
        GameUILabel label = UIFactory.LabelFactory("Game Paused", 125, 40, 550, 200, LIGHT_GREEN);

        //Buttons
        GameUIButton resume = UIFactory.ButtonFactory("Resume Game", 250, 300, 300, 80, LIGHT_GREEN);
        GameUIButton exit = UIFactory.ButtonFactory("Exit Game", 265, 400, 270, 80, LIGHT_GREEN);
        //GameUIButton darken_bg = UIFactory.ButtonFactory("", 0, 0, 800, 800, LIGHT_GREEN);
        //darken_bg.setImage("ui/darken_bg.png");
        resume.onClickEvent = PauseOverlayFactory::unpauseGame;

        exit.onClickEvent = LevelSelectionFactory::loadLevelSelection;

        scene.addUIElements(resume, exit, label/*, darken_bg*/);

    }

    public static void pauseGame() {
        if (isPaused)
            return;
        loadPauseMenu();
        isPaused = true;
    }

    public static void unpauseGame() {
        if(!isPaused)
            return;
        unloadPauseMenu();
        isPaused = false;
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
