package com.project.use_cases.core.prebuilts.scenes;

import com.project.entity.core.GameObject;
import com.project.entity.ui.GameUI;
import com.project.use_cases.core.game.GameInteractor;
import com.project.entity.core.Scene;
import com.project.use_cases.core.prebuilts.ui.types.label.LabelOutputData;
import com.project.use_cases.core.prebuilts.game_objects.AbstractObjectFactory;
import com.project.use_cases.core.prebuilts.game_objects.game_object_types.ObjectType;
import com.project.use_cases.core.prebuilts.ui.UIFactory;
import com.project.use_cases.core.prebuilts.ui.types.button.ButtonOutputData;

import static com.project.interface_adapters.core.display.ui.UIConstants.LIGHT_GREEN;

public class PauseOverlayFactory {
     public static GameObject pauseBackground = AbstractObjectFactory.generateOfType(ObjectType.PAUSE_BACKGROUND);
    public static Boolean isPaused = false;

    public static void createPauseOverlay(Scene scene) {

        // Background

        // Label
        LabelOutputData label = UIFactory.LabelFactory("Game Paused", 125, 40, 550, 200, LIGHT_GREEN);

        //Buttons
        ButtonOutputData resume = UIFactory.ButtonFactory("Resume Game", 250, 300, 300, 80, LIGHT_GREEN);
        ButtonOutputData restart = UIFactory.ButtonFactory("Restart Game", 240, 400, 320, 80, LIGHT_GREEN);
        ButtonOutputData exit = UIFactory.ButtonFactory("Exit Game", 265, 500, 270, 80, LIGHT_GREEN);
        ButtonOutputData darken_bg = UIFactory.ButtonFactory("", 0, 0, 800, 800, LIGHT_GREEN);
        darken_bg.setImage("ui/darken_bg.png");

        resume.setButtonCallback(PauseOverlayFactory::unpauseGame);
        restart.setButtonCallback(() -> PauseOverlayFactory.reloadCurrentLevel());
        exit.setButtonCallback(() -> LevelSelectionFactory.loadLevelSelection());

        scene.addUIElements(new GameUI(resume),
                new GameUI(restart),
                new GameUI(exit),
                new GameUI(label),
                new GameUI(darken_bg));

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
        //pauseBackground.getScriptable(SpriteRenderer.class).setEnabled(true);
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
