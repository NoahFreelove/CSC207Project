package com.project.use_cases.ui.label;

import com.project.interface_adapters.ui.button.ButtonPresenter;
import com.project.interface_adapters.ui.label.LabelPresenter;

public interface LabelOutputBoundary {
    default LabelPresenter getPresenter() {
        return (LabelPresenter)this;
    }

    default LabelOutputBoundary createPresenter() {
        return LabelPresenter.create();
    }

    default LabelOutputBoundary createPresenter(String text, int x, int y, int width, int height) {
        return LabelPresenter.create(text, x, y, width, height);
    }
}
