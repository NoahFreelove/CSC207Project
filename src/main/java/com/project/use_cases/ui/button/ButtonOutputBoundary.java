package com.project.use_cases.ui.button;

import com.project.interface_adapters.ui.button.ButtonPresenter;

import java.awt.*;

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
