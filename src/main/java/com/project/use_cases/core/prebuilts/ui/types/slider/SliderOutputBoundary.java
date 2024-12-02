package com.project.use_cases.core.prebuilts.ui.types.slider;

import com.project.interface_adapters.core.display.ui.slider.SliderPresenter;

public interface SliderOutputBoundary {
    default SliderPresenter getPresenter() {
        return (SliderPresenter)this;
    }


    default SliderOutputBoundary createPresenter(int min, int max, int value) {
        return SliderPresenter.create(min, max, value);
    }
}
