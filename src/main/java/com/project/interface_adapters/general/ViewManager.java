package com.project.interface_adapters.general;

import com.project.view.View;

public interface ViewManager {
    default View getView() {
        return View.getInstance();
    }

    default void createView(ViewModel model, int width, int height, String title) {
        View.create(model, width, height, title);
    }
}
