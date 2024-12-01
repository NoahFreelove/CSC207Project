package com.project.use_cases.play_prebuilt_levels.scenes;

import com.project.use_cases.general.GameInteractor;
import com.project.entity.core.GameObject;
import com.project.entity.core.Scene;
import com.project.entity.core.Tuple;
import com.project.use_cases.play_prebuilt_levels.game_objects.AbstractObjectFactory;
import com.project.use_cases.play_prebuilt_levels.game_objects.ObjectType;
import com.project.use_cases.general.GameOutputData;
import com.project.entity.ui.GameUIButton;
import com.project.use_cases.play_prebuilt_levels.scripts.SceneExit;
import com.project.use_cases.play_prebuilt_levels.scripts.WinScript;
import com.project.use_cases.play_prebuilt_levels.ui.UIFactory;
import com.project.use_cases.level_editing.LevelEditor;
import com.project.use_cases.player_death_count.PlayerDeath;

import java.util.HashMap;

import static com.project.entity.ui.UIConstants.*;

public class LevelSelectionFactory {
    private static HashMap<Integer, String> levelMap = new HashMap<>();
    private static String loadedLevel;
    public static boolean isInEditor = false;

    static {
        levelMap.put(0, "levels/level1.json");
        levelMap.put(1, "levels/level2.json");
        levelMap.put(2, "levels/level3.json");
        levelMap.put(3, "levels/level4.json");
        levelMap.put(4, "levels/level5.json");
        levelMap.put(5, "levels/level6.json");
        levelMap.put(6, "levels/level7.json");
        levelMap.put(7, "levels/level8.json");
        levelMap.put(8, "levels/level9.json");
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
        Tuple<GameInteractor, GameOutputData> out = GameInteractor.createAndWait();
        GameOutputData w = out.getSecond();

        if (PauseOverlayFactory.isPaused) {
            PauseOverlayFactory.unpauseGame();
        }

        WinOverlayFactory.removeWinOverlay();
        PlayerDeath.resetDeathCount();

        Scene s = LevelSelectionFactory.createScene();
        GameInteractor.getInstance().unpauseGame();
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
                loadedLevel = levelMap.get(finalI);
                isInEditor = false;
                LevelEditor.loadFromFileForMainGame(loadedLevel);
            };

            scene.addUIElements(temp);
            XReset++;
        }
    }

    public static void reloadCurrentLevel() {
        LevelEditor.loadFromFileForMainGame(loadedLevel);
        GameInteractor.getInstance().unpauseGame();
        PlayerDeath.resetDeathCount();
        WinScript.restartGameStatus();
    }
}
