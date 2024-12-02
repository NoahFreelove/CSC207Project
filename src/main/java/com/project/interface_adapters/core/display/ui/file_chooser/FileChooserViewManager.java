package com.project.interface_adapters.core.display.ui.file_chooser;

import com.project.view.core.ui.UIFileChooserView;

public interface FileChooserViewManager {
    default UIFileChooserView getView() {
        return (UIFileChooserView) this;
    }

    default UIFileChooserView createView() {

        return new UIFileChooserView();
    }
}
