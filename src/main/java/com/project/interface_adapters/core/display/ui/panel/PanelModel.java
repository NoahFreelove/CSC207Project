package com.project.interface_adapters.core.display.ui.panel;

public class PanelModel {
    private PanelViewManager view;

    public PanelModel() {
        view = new PanelViewManager() {};

        view = view.createView();
    }

    public PanelModel(int x, int y, int width, int height) {
        view = new PanelViewManager() {};

        view = view.createView(x, y, width, height);
    }

    public PanelViewManager getViewManager() {
        return view;
    }
}
