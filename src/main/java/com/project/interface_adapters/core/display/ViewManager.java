package com.project.interface_adapters.core.display;

import com.project.view.core.View;

public interface ViewManager {
    default View getView() {
        return View.getInstance();
    }

    default void createView(ViewModel model, int width, int height, String title) {
        View.create(model, width, height, title);
    }
}
