package com.project.interface_adapters.ui.button;

import com.project.use_cases.ui.button.ButtonInputBoundary;

public class ButtonModel {
    private ButtonController callback;
    private ButtonViewManager view;

    public ButtonModel() {
        callback = new ButtonController();
        view = new ButtonViewManager() {};

        view = view.createView(callback);
    }

    public ButtonModel(String text, int x, int y, int width, int height) {
        callback = new ButtonController();
        view = new ButtonViewManager() {};

        view = view.createView(callback, text, x, y, width, height);
    }

    public ButtonViewManager getViewManager() {
        return view;
    }

    public void setCallback(ButtonInputBoundary call) {
        callback.setCallback(call);
    }
}
