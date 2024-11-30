package com.project.use_cases.play_prebuilt_levels.scenes;

import com.project.entity.core.GameObject;
import com.project.use_cases.general.GameInteractor;
import com.project.entity.core.Scene;
import com.project.entity.ui.GameUIButton;
import com.project.entity.ui.GameUILabel;
import com.project.use_cases.play_prebuilt_levels.scripts.SceneExit;
import com.project.use_cases.play_prebuilt_levels.ui.UIFactory;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;

import static com.project.entity.ui.UIConstants.FOREST_GREEN;

public class WinOverlayFactory {
    public static ArrayList<JComponent> WinElements = new ArrayList<>();

    public static void createWinOverlay(Scene scene) {

        // Label
        GameUILabel label = UIFactory.LabelFactory("Level Completed", 50, 40, 700, 200, FOREST_GREEN);

        //Buttons
        GameUIButton restart = UIFactory.ButtonFactory("Restart Level", 150, 300, 500, 80, FOREST_GREEN);
        GameUIButton back = UIFactory.ButtonFactory("Back to Select", 150, 400, 500, 80, FOREST_GREEN);

        restart.onClickEvent = LevelSelectionFactory::reloadCurrentLevel;

        back.onClickEvent = LevelSelectionFactory::loadLevelSelection;

        scene.addUIElements(restart, back, label);

        WinElements.add(label);
        //WinElements.add(restart);
        WinElements.add(back);
    }

    public static void winGame() {
        Scene s = GameInteractor.getInstance().getPrimaryWindow().getActiveScene();
        createWinOverlay(s);
        removePauseButton();
        GameInteractor.getInstance().pauseGame();
        Iterator objects = s.getSceneObjects();
        while (objects.hasNext()) {
            GameObject object = (GameObject) objects.next();
            System.out.println(object);
        }
        removeEscPause(s);

    }

    public static void removeEscPause(Scene scene) {
        Iterator objects = scene.getSceneObjects();
        while (objects.hasNext()) {
            GameObject object = (GameObject) objects.next();
            if (object.hasTag("Escape")) {
                scene.removeSceneObject(object);
            }
        }
    }

    public static void removePauseButton() {
        Scene s = GameInteractor.getInstance().getPrimaryWindow().getActiveScene();
        GameUIButton pause = s.getButton("Pause");
        s.removeUIElement(pause);
    }

    public static void removeWinOverlay() {
        Scene s = GameInteractor.getInstance().getPrimaryWindow().getActiveScene();

        for (JComponent c : WinElements) {
            s.removeUIElement(c);
        }
    }
}
