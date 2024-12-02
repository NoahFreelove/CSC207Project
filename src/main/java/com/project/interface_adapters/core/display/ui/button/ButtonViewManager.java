package com.project.interface_adapters.core.display.ui.button;

import com.project.view.core.ui.UIButtonView;

public interface ButtonViewManager {
    default UIButtonView getView() {
        return (UIButtonView) this;
    }

    default UIButtonView createView(ButtonController controller) {
        return new UIButtonView(controller);
    }

    default UIButtonView createView(ButtonController controller, String text, int x, int y, int width, int height) {
        return new UIButtonView(controller, text, x, y, width, height);
    }
}
