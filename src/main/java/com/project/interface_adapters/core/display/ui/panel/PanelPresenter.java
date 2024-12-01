package com.project.interface_adapters.core.display.ui.panel;

import com.project.use_cases.core.prebuilts.ui.types.panel.PanelOutputBoundary;

public class PanelPresenter implements PanelOutputBoundary {
    private PanelModel panelModel;

    private PanelPresenter() {
        panelModel = new PanelModel();
    }

    private PanelPresenter(int x, int y, int width, int height) {
        panelModel = new PanelModel(x, y, width, height);
    }


    public static PanelPresenter create() {
        return new PanelPresenter();
    }

    public static PanelPresenter create(int x, int y, int width, int height) {
        return new PanelPresenter(x, y, width, height);
    }

    public PanelModel getModel() {
        return panelModel;
    }
}
