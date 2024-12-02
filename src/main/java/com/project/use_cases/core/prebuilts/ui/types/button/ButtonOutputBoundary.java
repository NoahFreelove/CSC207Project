package com.project.use_cases.core.prebuilts.ui.types.button;

import com.project.interface_adapters.core.display.ui.button.ButtonPresenter;

public interface ButtonOutputBoundary {
    default ButtonPresenter getPresenter() {
        return (ButtonPresenter)this;
    }

    default ButtonOutputBoundary createPresenter() {
        return ButtonPresenter.create();
    }

    default ButtonOutputBoundary createPresenter(String text, int x, int y, int width, int height) {
        return ButtonPresenter.create(text, x, y, width, height);
    }
}
