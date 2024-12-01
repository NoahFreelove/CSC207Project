package com.project.interface_adapters.ui.button;

import com.project.use_cases.ui.button.ButtonInputBoundary;

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
