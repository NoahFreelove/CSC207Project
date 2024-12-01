package com.project.use_cases.editor;

import com.project.use_cases.core.editor.LevelEditor;
import com.project.use_cases.core.game.GameInteractor;
import com.project.use_cases.core.game.GameOutputData;

import javax.swing.*;

public class EditorLoadInteractor {
    public static void execute(GameOutputData w, LevelEditor scene) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to load");
        int userSelection = fileChooser.showOpenDialog(w.getRootPane());

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            scene.loadFromFile(fileChooser.getSelectedFile().getAbsolutePath(), false);
        }

        GameInteractor.getInstance().getPrimaryWindow().refocusInWindow();
    }
}
