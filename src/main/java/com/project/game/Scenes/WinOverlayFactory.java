package com.project.game.Scenes;

import com.project.engine.Core.Engine;
import com.project.engine.Core.Scene;
import com.project.engine.UI.GameUIButton;
import com.project.engine.UI.GameUILabel;
import com.project.game.Scenes.Levels.EasyLevel;
import com.project.game.UIFactories.UIFactory;

import static com.project.engine.UI.UIConstants.FOREST_GREEN;

public class WinOverlayFactory {

    public static void createWinOverlay(Scene scene) {

        // Background

        // Label
        GameUILabel label = UIFactory.LabelFactory("Level Completed", 50, 40, 700, 200, FOREST_GREEN);

        //Buttons
        GameUIButton restart = UIFactory.ButtonFactory("Restart Level", 150, 300, 500, 80, FOREST_GREEN);
        GameUIButton back = UIFactory.ButtonFactory("Back to Select", 150, 400, 500, 80, FOREST_GREEN);

        restart.onClickEvent = EasyLevel::loadEasyLevel;

        back.onClickEvent = LevelSelectionFactory::loadLevelSelection;

        scene.addUIElements(restart, back, label);
    }

    public static void winGame() {
        Scene s = Engine.getInstance().getPrimaryWindow().getActiveScene();
        createWinOverlay(s);
        removePauseButton();
        Engine.getInstance().pauseGame();
    }

    public static void removePauseButton() {
        Scene s = Engine.getInstance().getPrimaryWindow().getActiveScene();
        GameUIButton pause = s.getButton("Pause");
        s.removeUIElement(pause);
    }
}
