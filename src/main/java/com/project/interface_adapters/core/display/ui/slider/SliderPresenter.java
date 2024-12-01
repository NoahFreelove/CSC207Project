package com.project.interface_adapters.core.display.ui.slider;

import com.project.use_cases.core.prebuilts.ui.types.slider.SliderOutputBoundary;

public class SliderPresenter implements SliderOutputBoundary {
    private SliderModel buttonModel;

    private SliderPresenter(int min, int max, int v) {
        buttonModel = new SliderModel(min, max, v);
    }

    public static SliderPresenter create(int min, int max, int v) {
        return new SliderPresenter(min, max, v);
    }

    public SliderModel getModel() {
        return buttonModel;
    }
}
