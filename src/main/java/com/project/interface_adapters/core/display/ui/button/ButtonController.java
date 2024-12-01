package com.project.interface_adapters.core.display.ui.button;

import com.project.use_cases.core.prebuilts.ui.types.button.ButtonInputBoundary;

public class ButtonController {
    ButtonInputBoundary callback = () -> {};
    public ButtonController() {

    }

    public void setCallback(ButtonInputBoundary callback) {
        this.callback = callback;
    }

    public void callback() {
        this.callback.onClick();
    }
}
