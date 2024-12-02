package com.project.use_cases.editor;

import com.project.use_cases.core.editor.LevelEditor;
import com.project.use_cases.core.game.GameInteractor;
import com.project.use_cases.core.game.GameOutputData;
import com.project.use_cases.core.prebuilts.ui.types.file_chooser.FileChooserOutputData;

public class EditorLoadInteractor {
    public static void execute(GameOutputData w, LevelEditor scene) {
        FileChooserOutputData fileChooser = new FileChooserOutputData();
        fileChooser.openFileChooser();
        if(fileChooser.getPath().isEmpty()) {
            return;
        }
        scene.loadFromFile(fileChooser.getPath(), false);


        GameInteractor.getInstance().getPrimaryWindow().refocusInWindow();
    }
}
