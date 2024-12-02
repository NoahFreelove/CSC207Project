package com.project.use_cases.core.prebuilts.ui.types.file_chooser;

import com.project.entity.ui.GameUIObject;

import javax.swing.*;

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
}
