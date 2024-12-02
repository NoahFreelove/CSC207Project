package com.project.interface_adapters.core.display.ui.label;

import com.project.view.core.ui.UILabelView;

public interface LabelViewManager {
    default UILabelView getView() {
        return (UILabelView) this;
    }

    default UILabelView createView() {

        return new UILabelView();
    }

    default UILabelView createView(String text, int x, int y, int width, int height) {
        return new UILabelView(text, x, y, width, height);
    }
}
