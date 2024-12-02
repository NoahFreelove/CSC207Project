package com.project.use_cases.core.game;

import com.project.interface_adapters.core.display.ViewPresenter;

public interface GameOutputBoundary {
    default ViewPresenter getViewPresenter() {
        return ViewPresenter.getInstance();
    }

    default void createViewPresenter(int width, int height, String title) {
        ViewPresenter.makeInstance(width, height, title);
    }
}
