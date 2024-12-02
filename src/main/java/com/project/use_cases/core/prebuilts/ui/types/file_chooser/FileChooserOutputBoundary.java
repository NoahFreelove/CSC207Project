package com.project.use_cases.core.prebuilts.ui.types.file_chooser;

import com.project.interface_adapters.core.display.ui.file_chooser.FileChooserPresenter;
import com.project.interface_adapters.core.display.ui.label.LabelPresenter;
import com.project.use_cases.core.prebuilts.ui.types.label.LabelOutputBoundary;

public interface FileChooserOutputBoundary {
    default FileChooserPresenter getPresenter() {
        return (FileChooserPresenter)this;
    }

    default FileChooserOutputBoundary createPresenter() {
        return FileChooserPresenter.create();
    }

}
