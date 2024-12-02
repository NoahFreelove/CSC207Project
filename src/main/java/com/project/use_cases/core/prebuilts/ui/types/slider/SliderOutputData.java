package com.project.use_cases.core.prebuilts.ui.types.slider;

import com.project.entity.ui.GameUIObject;

import javax.swing.*;
import java.awt.*;

public class SliderOutputData implements GameUIObject {
    private SliderOutputBoundary out;

    public SliderOutputData(int min, int max, int value){
        out = new SliderOutputBoundary() {};
        out = out.createPresenter(min, max, value);
    }


    public void setSliderCallback(SliderInputBoundary callback) {
        out.getPresenter().getModel().setCallback(callback);
    }

    public int getValue() {
        return out.getPresenter().getModel().getViewManager().getView().getValue();
    }

    public void setSpacing(int spacing) {
        out.getPresenter().getModel().getViewManager().getView().setSpacing(spacing);
    }

    public void setFont(Font f) {
        out.getPresenter().getModel().getViewManager().getView().setFont(f);
    }

    @Override
    public JComponent getComponent() {
        return out.getPresenter().getModel().getViewManager().getView();
    }
}
