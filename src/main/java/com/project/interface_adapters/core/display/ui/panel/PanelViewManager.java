package com.project.interface_adapters.core.display.ui.panel;

import com.project.view.core.ui.UIPanelView;

public interface PanelViewManager {
    default UIPanelView getView() {
        return (UIPanelView) this;
    }

    default UIPanelView createView() {

        return new UIPanelView();
    }

    default UIPanelView createView(int x, int y, int width, int height) {
        return new UIPanelView(x, y, width, height);
    }
}
