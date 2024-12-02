package com.project.interface_adapters.core.display.ui.slider;

import com.project.view.core.ui.UISliderView;

public interface SliderViewManager {
    default UISliderView getView() {
        return (UISliderView) this;
    }

    default UISliderView createView(SliderController controller, int min, int max, int value) {
        return new UISliderView(controller, min, max, value);
    }
}
