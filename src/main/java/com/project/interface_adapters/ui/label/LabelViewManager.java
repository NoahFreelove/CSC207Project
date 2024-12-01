package com.project.interface_adapters.ui.label;

import com.project.view.ui.GameButtonView;
import com.project.view.ui.GameLabelView;

public interface LabelViewManager {
    default GameLabelView getView() {
        return (GameLabelView) this;
    }

    default GameLabelView createView() {

        return new GameLabelView();
    }

    default GameLabelView createView(String text, int x, int y, int width, int height) {
        return new GameLabelView(text, x, y, width, height);
    }
}
