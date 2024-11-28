package com.project.use_cases.play_prebuilt_levels.scenes;

import com.project.use_cases.general.GameInteractor;
import com.project.entity.core.Scene;
import com.project.entity.ui.GameUIButton;
import com.project.entity.ui.GameUILabel;
import com.project.use_cases.play_prebuilt_levels.ui.UIFactory;

import static com.project.entity.ui.UIConstants.LIGHT_GREEN;

public class PauseOverlayFactory {
    // public static GameObject pauseBackground = AbstractObjectFactory.generateOfType(ObjectType.PAUSE_BACKGROUND);
    public static Boolean isPaused = false;

    public static void createPauseOverlay(Scene scene) {

        // Background

        // Label
        GameUILabel label = UIFactory.LabelFactory("Game Paused", 125, 40, 550, 200, LIGHT_GREEN);

        //Buttons
        GameUIButton resume = UIFactory.ButtonFactory("Resume Game", 250, 300, 300, 80, LIGHT_GREEN);
        GameUIButton restart = UIFactory.ButtonFactory("Restart Game", 240, 400, 320, 80, LIGHT_GREEN);
        GameUIButton exit = UIFactory.ButtonFactory("Exit Game", 265, 500, 270, 80, LIGHT_GREEN);
        //GameUIButton darken_bg = UIFactory.ButtonFactory("", 0, 0, 800, 800, LIGHT_GREEN);
        //darken_bg.setImage("ui/darken_bg.png");
        resume.onClickEvent = PauseOverlayFactory::unpauseGame;
        restart.onClickEvent = PauseOverlayFactory::reloadCurrentLevel;
        exit.onClickEvent = LevelSelectionFactory::loadLevelSelection;

        scene.addUIElements(resume, restart, exit, label/*darken_bg*/);

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

    public static void reloadCurrentLevel() {
        unpauseGame();
        LevelSelectionFactory.reloadCurrentLevel();
    }

    public static void loadPauseMenu() {
        Scene s = GameInteractor.getInstance().getPrimaryWindow().getActiveScene();
        createPauseOverlay(s);
        // pauseBackground.getScriptable(SpriteRenderer.class).setEnabled(true);
        GameInteractor.getInstance().pauseGame();
    }

    public static void unloadPauseMenu() {
        Scene s = GameInteractor.getInstance().getPrimaryWindow().getActiveScene();
        UIFactory.removePause(s);
        // pauseBackground.getScriptable(SpriteRenderer.class).setEnabled(false);
        GameInteractor.getInstance().unpauseGame();
        GameInteractor.getInstance().getPrimaryWindow().refocusInWindow();
    }

}
