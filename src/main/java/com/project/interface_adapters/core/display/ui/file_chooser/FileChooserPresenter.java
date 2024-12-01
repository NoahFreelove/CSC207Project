package com.project.interface_adapters.core.display.ui.file_chooser;

import com.project.interface_adapters.core.display.ui.label.LabelModel;
import com.project.interface_adapters.core.display.ui.label.LabelPresenter;
import com.project.use_cases.core.prebuilts.ui.types.file_chooser.FileChooserOutputBoundary;

public class FileChooserPresenter implements FileChooserOutputBoundary {
    private FileChooserModel chooserModel;

    private FileChooserPresenter() {
        chooserModel = new FileChooserModel();
    }

    public static FileChooserPresenter create() {
        return new FileChooserPresenter();
    }
    public FileChooserModel getModel() {
        return chooserModel;
    }


}
