package com.project.interface_adapters.ui.button;

import com.project.view.ui.GameButtonView;

public interface ButtonViewManager {
    default GameButtonView getView() {
        return (GameButtonView) this;
    }

    default GameButtonView createView(ButtonController controller) {
        return new GameButtonView(controller);
    }

    default GameButtonView createView(ButtonController controller, String text, int x, int y, int width, int height) {
        return new GameButtonView(controller, text, x, y, width, height);
    }
}
