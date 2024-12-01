package com.project.interface_adapters.core.display.ui.button;

import com.project.use_cases.core.prebuilts.ui.types.button.ButtonOutputBoundary;

public class ButtonPresenter implements ButtonOutputBoundary {
    private ButtonModel buttonModel;

    private ButtonPresenter() {
        buttonModel = new ButtonModel();
    }

    private ButtonPresenter(String text, int x, int y, int width, int height) {
        buttonModel = new ButtonModel(text, x, y, width, height);
    }


    public static ButtonPresenter create() {
        return new ButtonPresenter();
    }

    public static ButtonPresenter create(String text, int x, int y, int width, int height) {
        return new ButtonPresenter(text, x, y, width, height);
    }

    public ButtonModel getModel() {
        return buttonModel;
    }
}
