package com.project.view.core.ui;

import com.project.external_interfaces.core.ui.FontCreator;
import com.project.interface_adapters.core.display.ui.slider.SliderController;
import com.project.interface_adapters.core.display.ui.slider.SliderViewManager;
import com.project.interface_adapters.core.editor.EditorTileManager;

import javax.swing.*;
import java.awt.*;

public class UISliderView extends JSlider implements SliderViewManager {
    SliderController controller;

    public UISliderView(SliderController controller, int min, int max, int value) {
        super(JSlider.HORIZONTAL, min, fixMax(max), value);

        setOpaque(true);
        setPaintTicks(true);
        setMajorTickSpacing(1);
        setPaintTicks(true);
        setSnapToTicks(true);
        setBackground(Color.decode("#404040"));
        setForeground(Color.WHITE);
        setFont(new Font("Arial", Font.PLAIN, 20));

        this.controller = controller;
        setFont(FontCreator.createFont(20));

        // set on click listener
        addChangeListener(e -> onSlide());
    }

    private static int fixMax(int max) {
        if (max == 0) {
            return EditorTileManager.spriteCache.size();
        }

        return max;
    }

    public void setSpacing(int spacing) {
        setMajorTickSpacing(spacing);
        setPaintLabels(true);
    }

    public void setFontSize(int fontSize) {
        Font f = getFont();
        setFont(new Font(f.getFontName(), f.getStyle(), fontSize));
    }

    public void onSlide() {
        controller.callback();
    }
}
