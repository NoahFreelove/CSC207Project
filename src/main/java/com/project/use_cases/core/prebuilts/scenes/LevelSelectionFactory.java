package com.project.use_cases.core.prebuilts.scenes;

import com.project.entity.ui.GameUI;
import com.project.entity.core.GameObject;
import com.project.entity.core.Scene;
import com.project.use_cases.core.prebuilts.game_objects.AbstractObjectFactory;
import com.project.use_cases.core.prebuilts.game_objects.game_object_types.ObjectType;
import com.project.use_cases.core.prebuilts.scripts.SceneExit;
import com.project.use_cases.core.prebuilts.ui.UIBuilder;
import com.project.use_cases.load_level.LoadLevelInteractor;
import com.project.use_cases.core.prebuilts.ui.types.button.ButtonOutputData;

import java.util.HashMap;

import static com.project.use_cases.core.prebuilts.ui.UIConstants.*;

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

    public static Scene createScene() {
        Scene scene = new Scene();

        GameObject escapeDetector = new GameObject();
        escapeDetector.addBehavior(new SceneExit(MainMenuFactory::loadMainMenu));

        ButtonOutputData back = UIBuilder.ButtonFactory("Back to Main", 200, 450, 400, 80);
        back.setButtonCallback(MainMenuFactory::loadMainMenu);

        createLevel(levelMap.size(), scene);

        // Adding everything
        scene.addSceneObject(escapeDetector, true);
        scene.addSceneObject(AbstractObjectFactory.generateOfType(ObjectType.BACKGROUND));
        scene.addUIElements(new GameUI(back));
        return scene;
    }

    private static void createLevel(int levels, Scene scene) {
        int j = 0;
        int XReset = 0;
        for (int i = 0; i < levels; i++) {
            if (i % 3 == 0) {
                j += 1;
                XReset = 0;
            }
            ButtonOutputData temp = UIBuilder.ButtonFactory("Level " + (i + 1), BUTTON_X_OFFSET + XReset * BUTTON_X_WIDTH, j * BUTTON_Y_OFFSET, BUTTON_X_WIDTH, 80);
            int finalI = i;
            temp.setButtonCallback(() -> {
                loadedLevel = levelMap.get(finalI);
                isInEditor = false;
                LoadLevelInteractor.execute(loadedLevel);
            });

            scene.addUIElements(new GameUI(temp));
            XReset++;
        }
    }

    public static boolean isInEditor() {
        return isInEditor;
    }

    public static void reloadCurrentLevel() {
        LoadLevelInteractor.execute(loadedLevel);
    }
}
