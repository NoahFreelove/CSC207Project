package com.project.use_cases.core.prebuilts.ui.types.panel;

import com.project.interface_adapters.core.display.ui.panel.PanelPresenter;

public interface PanelOutputBoundary {
    default PanelPresenter getPresenter() {
        return (PanelPresenter) this;
    }

    default PanelOutputBoundary createPresenter() {
        return PanelPresenter.create();
    }

    default PanelOutputBoundary createPresenter(int x, int y, int width, int height) {
        return PanelPresenter.create(x, y, width, height);
    }
}
