package com.project.use_cases.core.prebuilts.scenes;

import com.project.entity.core.GameObject;
import com.project.entity.ui.GameUI;
import com.project.use_cases.core.game.GameInteractor;
import com.project.entity.core.Scene;
import com.project.use_cases.core.prebuilts.ui.types.label.LabelOutputData;
import com.project.use_cases.core.prebuilts.ui.UIFactory;
import com.project.use_cases.core.prebuilts.ui.types.button.ButtonOutputData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.project.interface_adapters.core.display.ui.UIConstants.FOREST_GREEN;

public class WinOverlayFactory {
    public static ArrayList<GameUI> WinElements = new ArrayList<>();

    public static void createWinOverlay(Scene scene) {

        // Label
        LabelOutputData label = UIFactory.LabelFactory("Level Completed", 50, 40, 700, 200, FOREST_GREEN);

        //Buttons
        ButtonOutputData restart = UIFactory.ButtonFactory("Restart Level", 150, 300, 500, 80, FOREST_GREEN);
        ButtonOutputData back = UIFactory.ButtonFactory("Back to Select", 150, 400, 500, 80, FOREST_GREEN);

        restart.setButtonCallback(LevelSelectionFactory::reloadCurrentLevel);

        back.setButtonCallback(LevelSelectionFactory::loadLevelSelection);

        GameUI l = new GameUI(label);
        GameUI b = new GameUI(back);
        scene.addUIElements(new GameUI(restart), b, l);

        WinElements.add(l);
        //WinElements.add(restart);
        WinElements.add(b);
    }

    public static void winGame() {
        Scene s = GameInteractor.getInstance().getPrimaryWindow().getActiveScene();
        createWinOverlay(s);
        removePauseButton();
        GameInteractor.getInstance().pauseGame();
        Iterator objects = s.getSceneObjects();
        while (objects.hasNext()) {
            GameObject object = (GameObject) objects.next();
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
        List<GameUI>  ui = s.getUIElements();

        for (GameUI uiElement : ui) {
            if(uiElement.getUnderlyingUI() instanceof ButtonOutputData && ((ButtonOutputData) uiElement.getUnderlyingUI()).getText().equals("Pause")) {
                s.removeUIElement(uiElement);
            }
        }
    }

    public static void removeWinOverlay() {
        Scene s = GameInteractor.getInstance().getPrimaryWindow().getActiveScene();

        for (GameUI c : WinElements) {
            s.removeUIElement(c);
        }
    }
}
