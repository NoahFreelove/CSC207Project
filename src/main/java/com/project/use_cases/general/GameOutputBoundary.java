package com.project.use_cases.general;

import com.project.interface_adapters.general.ViewPresenter;

public interface GameOutputBoundary {
    default ViewPresenter getViewPresenter() {
        return ViewPresenter.getInstance();
    }

    default void createViewPresenter(int width, int height, String title) {
        ViewPresenter.makeInstance(width, height, title);
    }
}
