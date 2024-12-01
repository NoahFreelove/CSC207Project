package com.project.use_cases.core.editor;

import com.project.use_cases.core.game.GameInteractor;
import com.project.entity.core.Scene;
import com.project.use_cases.core.game.GameOutputData;
import com.project.use_cases.core.prebuilts.ui.UIFactory;


import static com.project.use_cases.core.editor.LevelEditor.levelEditorScreenSize;

public class LevelEditorFactory {
    public static Scene generateLevelEditor() {
        LevelEditor scene = new LevelEditor();
        GameOutputData w = GameInteractor.getInstance().getPrimaryWindow();

        if (w == null) {
            System.err.println("No window found");
            return Scene.NullScene();
        }

        scene.addUIElement(UIFactory.generateEditorUI(w, scene));
        return scene;
    }

    public static void loadLevelEditor(GameOutputData w) {
        w.setWindowSizeForce(levelEditorScreenSize.getFirst(),levelEditorScreenSize.getSecond());

        Scene s = LevelEditorFactory.generateLevelEditor();
        w.setActiveScene(s);
    }
}
