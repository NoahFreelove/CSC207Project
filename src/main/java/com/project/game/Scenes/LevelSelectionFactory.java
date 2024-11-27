package com.project.game.Scenes;

import com.project.engine.Core.Engine;
import com.project.engine.Core.GameObject;
import com.project.engine.Core.Scene;
import com.project.engine.Core.Tuple;
import com.project.engine.Core.Window.GameWindow;
import com.project.engine.IO.FileIO;
import com.project.engine.UI.GameUIButton;
import com.project.game.ObjectFactories.AbstractObjectFactory;
import com.project.game.ObjectFactories.ObjectType;
import com.project.game.Scripts.SceneExit;
import com.project.game.UIFactories.UIFactory;
import com.project.game.editor.LevelEditor;

import java.util.HashMap;

import static com.project.engine.UI.UIConstants.*;

public class LevelSelectionFactory {
    private static HashMap<Integer, String> levelMap = new HashMap<>();

    static {
        levelMap.put(0, "/levels/level1.json");
        levelMap.put(1, "/levels/level2.json");
        levelMap.put(2, "/levels/level3.json");
        levelMap.put(3, "/levels/level4.json");
        levelMap.put(4, "/levels/level5.json");
        levelMap.put(5, "/levels/level6.json");
        levelMap.put(6, "/levels/level7.json");
        levelMap.put(7, "/levels/level8.json");
    }

    private static Scene createScene() {
        Scene scene = new Scene();

        GameObject escapeDetector = new GameObject();
        escapeDetector.addBehavior(new SceneExit(MainMenuFactory::loadMainMenu));

        GameUIButton back = UIFactory.ButtonFactory("Back to Main", 200, 450, 400, 80);
        back.onClickEvent = MainMenuFactory::loadMainMenu;

        createLevel(levelMap.size(), scene);

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
        WinOverlayFactory.removeWinOverlay();
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
            temp.onClickEvent = () -> {
                LevelEditor.loadFromFileForMainGame(FileIO.GetAbsPathOfResource(levelMap.get(finalI)));
            };
            scene.addUIElements(temp);
            XReset++;
        }
    }
}
