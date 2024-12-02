package com.project.use_cases.core.prebuilts.ui.types.panel;

import com.project.entity.ui.GameUIObject;

import javax.swing.*;

public class PanelOutputData implements GameUIObject {
    private PanelOutputBoundary out;

    public PanelOutputData() {
        out = new PanelOutputBoundary() {};
        out = out.createPresenter();
    }

    public PanelOutputData(int x, int y, int width, int height){
        out = new PanelOutputBoundary() {};
        out = out.createPresenter(x, y, width, height);
    }

    public void add(GameUIObject obj) {
        getComponent().add(obj.getComponent());
    }

    public PanelOutputData setBackground(String hex) {
        out.getPresenter().getModel().getViewManager().getView().setBackground(hex);
        return this;
    }

    @Override
    public JComponent getComponent() {
        return out.getPresenter().getModel().getViewManager().getView();
    }
}
