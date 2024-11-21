package com.project.game.Scenes;

import com.project.engine.Core.Engine;
import com.project.engine.Core.GameObject;
import com.project.engine.Core.Scene;
import com.project.engine.Core.Tuple;
import com.project.engine.Core.Window.GameWindow;
import com.project.engine.IO.FileIO;
import com.project.engine.UI.FontCreator;
import com.project.engine.UI.GameUIButton;
import com.project.engine.UI.IOnClick;
import com.project.game.ObjectFactories.AbstractObjectFactory;
import com.project.game.ObjectFactories.ObjectType;
import com.project.game.Scenes.Levels.EasyLevel;
import com.project.game.Scripts.SceneExit;
import com.project.game.UIFactories.UIFactory;
import com.project.game.editor.LevelEditor;
import com.project.game.editor.LevelGenerationInterface;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

import static com.project.engine.UI.UIConstants.*;

public class LevelSelectionFactory {

    private static HashMap<Integer, LevelGenerationInterface> levelMap = new HashMap<>();

    static {
        levelMap.put(0, EasyLevel::createScene);
        levelMap.put(1, LevelEditor.loadFromFileForMainGame(FileIO.GetAbsPathOfResource("/levels/level1.json")));
    }

    private static Scene createScene() {
        Scene scene = new Scene();

        GameObject escapeDetector = new GameObject();
        escapeDetector.addBehavior(new SceneExit(MainMenuFactory::loadMainMenu));

        GameUIButton back = UIFactory.ButtonFactory("Back to Main", 200, 450, 400, 80);
        back.onClickEvent = MainMenuFactory::loadMainMenu;

        createLevel(2, scene);

        // Adding everything
        scene.addSceneObject(escapeDetector, true);
        scene.addSceneObject(AbstractObjectFactory.generateOfType(ObjectType.BACKGROUND));
        scene.addUIElements(back);
        return scene;
    }

    public static void loadLevelSelection() {
        Tuple<Engine, GameWindow> out = Engine.createAndWait();
        GameWindow w = out.getSecond();
        if (PauseOverlayFactory.isPaused) {
            PauseOverlayFactory.unpauseGame();
        }
        Scene s = LevelSelectionFactory.createScene();
        Engine.getInstance().unpauseGame();
        w.setActiveScene(s);
    }

    private static void createLevel(int levels, Scene scene) {
        int j = 0;
        int XReset = 0;
        for (int i = 0; i < levels; i++) {
            if (i % 3 == 0) {
                j += 1;
                XReset = 0;
            }
            GameUIButton temp = UIFactory.ButtonFactory("Level " + (i + 1), BUTTON_X_OFFSET + XReset * BUTTON_X_WIDTH, j * BUTTON_Y_OFFSET, BUTTON_X_WIDTH, 80);
            int finalI = i;
            temp.onClickEvent = () -> Engine.getInstance().getPrimaryWindow().setActiveScene(levelMap.get(finalI).genScene());
            scene.addUIElements(temp);
            XReset++;
        }
    }
}
