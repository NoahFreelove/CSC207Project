package com.project.interface_adapters.core.display.ui.slider;

import com.project.use_cases.core.prebuilts.ui.types.button.ButtonInputBoundary;
import com.project.use_cases.core.prebuilts.ui.types.slider.SliderInputBoundary;

public class SliderController {
    SliderInputBoundary callback = () -> {};

    public SliderController() {

    }

    public void setCallback(SliderInputBoundary callback) {
        this.callback = callback;
    }

    public void callback() {
        this.callback.onClick();
    }
}
