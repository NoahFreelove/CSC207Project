package com.project.view.core.ui;

import com.project.interface_adapters.core.display.ui.file_chooser.FileChooserViewManager;
import com.project.use_cases.core.game.GameInteractor;

import javax.swing.*;

public class UIFileChooserView extends JFileChooser implements FileChooserViewManager {
    private String chosenPath = "";
    public UIFileChooserView() {
        super();
        this.setDialogTitle("Specify a file to open");

    }

    public void openFileChooser(){
        int userSelection = this.showOpenDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            chosenPath = this.getSelectedFile().getAbsolutePath();
        }

        GameInteractor.getInstance().getPrimaryWindow().refocusInWindow();
    }


    public String getPath() {
        return chosenPath;
    }
}
