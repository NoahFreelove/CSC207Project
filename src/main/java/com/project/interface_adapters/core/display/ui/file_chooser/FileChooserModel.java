package com.project.interface_adapters.core.display.ui.file_chooser;

import com.project.interface_adapters.core.display.ui.label.LabelViewManager;

public class FileChooserModel {
    private FileChooserViewManager view;

    public FileChooserModel() {
        view = new FileChooserViewManager() {};

        view = view.createView();
    }

    public FileChooserViewManager getViewManager() {
        return view;
    }

}
