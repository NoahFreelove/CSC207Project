package com.project.use_cases.editor;

import com.project.use_cases.core.editor.LevelEditor;
import com.project.use_cases.core.game.GameInteractor;
import com.project.use_cases.core.game.GameOutputData;
import com.project.use_cases.core.prebuilts.ui.types.file_chooser.FileChooserInputData;
import com.project.use_cases.core.prebuilts.ui.types.file_chooser.FileChooserOutputData;

public class EditorSaveInteractor {
    FileChooserOutputData fileChooser;
    FileChooserInputData fileInput;


    public static void execute(GameOutputData w, LevelEditor scene) {
        // scene.activeFile == "", we need to ask user for folder to store with a file picker
        // then in a message box ask for file name, append .json to the end of it and set scene.activeFile to that
        // now that scene.activeFile != "", call scene.saveToFile()
        if (scene.activeFile.isEmpty()) {
            FileChooserOutputData fileChooser = new FileChooserOutputData();
            fileChooser.setDialogTitle("Specify a file to save");
            int userSelection = fileChooser.showSaveDialog(w);
            if (userSelection == 0) {
                scene.activeFile = FileChooserInputData.choose(fileChooser).getAbsolutePath();
                if (!scene.activeFile.endsWith(".json")) {
                    scene.activeFile += ".json";
                }
                scene.saveToFile();
            }
        } else {
            scene.saveToFile();
        }
        GameInteractor.getInstance().getPrimaryWindow().refocusInWindow();
    }
}
