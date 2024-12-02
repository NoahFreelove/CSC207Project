package com.project.interface_adapters.core.display.ui.slider;

import com.project.use_cases.core.prebuilts.ui.types.button.ButtonInputBoundary;
import com.project.use_cases.core.prebuilts.ui.types.slider.SliderInputBoundary;

public class SliderModel {
    private SliderController callback;
    private SliderViewManager view;

    public SliderModel(int min, int max, int v) {
        callback = new SliderController();
        view = new SliderViewManager() {};

        view = view.createView(callback, min, max, v);
    }

    public SliderViewManager getViewManager() {
        return view;
    }

    public void setCallback(SliderInputBoundary call) {
        callback.setCallback(call);
    }
}
