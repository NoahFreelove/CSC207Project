package com.project.use_cases.core.prebuilts.ui.types.label;

import com.project.entity.ui.GameUIObject;

import javax.swing.*;

public class LabelOutputData implements GameUIObject {
    private String imagePath = "";
    private LabelOutputBoundary out;

    public LabelOutputData() {
        out = new LabelOutputBoundary() {};
        out = out.createPresenter();
    }

    public LabelOutputData(String text) {
        out = new LabelOutputBoundary() {};
        out = out.createPresenter(text, 0, 0, 0, 0);
    }

    public LabelOutputData(String text, int x, int y, int width, int height){
        out = new LabelOutputBoundary() {};
        out = out.createPresenter(text, x, y, width, height);
    }

    public void setFontSize(int fontSize) {
        out.getPresenter().getModel().getViewManager().getView().setFontSize(fontSize);
    }

    public void setImage(String path) {
        imagePath = path;
        out.getPresenter().getModel().getViewManager().getView().setImage(imagePath);
    }

    public void setEditorIcon(int index) {
        out.getPresenter().getModel().getViewManager().getView().setEditorIcon(index);
    }

    public void setFancyFontSize(int size) {
        out.getPresenter().getModel().getViewManager().getView().setFancyFontSize(size);
    }

    public String getText() {
        return out.getPresenter().getModel().getViewManager().getView().getText();
    }

    public void setText(String text) {
        out.getPresenter().getModel().getViewManager().getView().setText(text);
    }

    @Override
    public JComponent getComponent() {
        return out.getPresenter().getModel().getViewManager().getView();
    }
}
