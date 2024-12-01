package com.project.use_cases.editor;

import com.project.use_cases.core.editor.LevelEditor;
import com.project.use_cases.core.game.GameInteractor;
import com.project.use_cases.core.game.GameOutputData;

import javax.swing.*;

public class EditorSaveInteractor {
    public static void execute(GameOutputData w, LevelEditor scene) {
        // scene.activeFile == "", we need to ask user for folder to store with a file picker
        // then in a message box ask for file name, append .json to the end of it and set scene.activeFile to that
        // now that scene.activeFile != "", call scene.saveToFile()
        if (scene.activeFile.isEmpty()) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to save");
            int userSelection = fileChooser.showSaveDialog(w.getRootPane());
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                scene.activeFile = fileChooser.getSelectedFile().getAbsolutePath();
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
