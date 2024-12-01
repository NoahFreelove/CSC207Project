package com.project.interface_adapters.ui.label;

import com.project.use_cases.ui.label.LabelOutputBoundary;

public class LabelPresenter implements LabelOutputBoundary {
    private LabelModel buttonModel;

    private LabelPresenter() {
        buttonModel = new LabelModel();
    }

    private LabelPresenter(String text, int x, int y, int width, int height) {
        buttonModel = new LabelModel(text, x, y, width, height);
    }


    public static LabelPresenter create() {
        return new LabelPresenter();
    }

    public static LabelPresenter create(String text, int x, int y, int width, int height) {
        return new LabelPresenter(text, x, y, width, height);
    }

    public LabelModel getModel() {
        return buttonModel;
    }
}
