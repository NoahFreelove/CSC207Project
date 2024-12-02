package com.project.interface_adapters.core.display.ui.label;

public class LabelModel {
    private LabelViewManager view;

    public LabelModel() {
        view = new LabelViewManager() {};

        view = view.createView();
    }

    public LabelModel(String text, int x, int y, int width, int height) {
        view = new LabelViewManager() {};

        view = view.createView(text, x, y, width, height);
    }

    public LabelViewManager getViewManager() {
        return view;
    }
}
