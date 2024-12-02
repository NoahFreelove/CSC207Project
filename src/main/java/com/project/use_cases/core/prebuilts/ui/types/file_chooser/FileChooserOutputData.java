package com.project.use_cases.core.prebuilts.ui.types.file_chooser;

import com.project.entity.ui.GameUIObject;
import com.project.use_cases.core.game.GameOutputData;

import javax.swing.*;
import java.io.File;

public class FileChooserOutputData implements GameUIObject {
    private FileChooserOutputBoundary out;


    public FileChooserOutputData() {
        super();
        out = new FileChooserOutputBoundary() {};
        out = out.createPresenter();
    }


    public void openFileChooser() {
        out.getPresenter().getModel().getViewManager().getView().openFileChooser();
    }

    public String getPath() {
        return out.getPresenter().getModel().getViewManager().getView().getPath();
    }



    @Override
    public JComponent getComponent() {
        return out.getPresenter().getModel().getViewManager().getView();
    }

    public void setDialogTitle(String specifyAFileToSave) {
        out.getPresenter().getModel().getViewManager().getView().setDialogTitle(specifyAFileToSave);
    }

    public int showSaveDialog(GameOutputData w) {
        return out.getPresenter().getModel().getViewManager().getView().showSaveDialog(w.getRootPane());
    }

    public File getSelectedFile() {
        return out.getPresenter().getModel().getViewManager().getView().getSelectedFile();
    }
}
