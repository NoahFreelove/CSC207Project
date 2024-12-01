package com.project.use_cases.ui.button;

import com.project.entity.ui.GameUIObject;

import javax.swing.*;

public class ButtonOutputData implements GameUIObject {
    private String imagePath = "";
    private ButtonOutputBoundary out;

    public ButtonOutputData() {
        out = new ButtonOutputBoundary() {};
        out = out.createPresenter();
    }

    public ButtonOutputData(String text, int x, int y, int width, int height){
        out = new ButtonOutputBoundary() {};
        out = out.createPresenter(text, x, y, width, height);
    }

    public void setTransparent(boolean transparent){
        out.getPresenter().getModel().getViewManager().getView().setTransparent(transparent);
    }

    public void setButtonCallback(ButtonInputBoundary callback) {
        out.getPresenter().getModel().setCallback(callback);
    }

    public void setFontSize(int size) {
        out.getPresenter().getModel().getViewManager().getView().setFontSize(size);
    }

    public void setImage(String path) {
        imagePath = path;
        out.getPresenter().getModel().getViewManager().getView().setImage(imagePath);
    }

    public void setImage(String path, int width, int height) {
        imagePath = path;
        out.getPresenter().getModel().getViewManager().getView().setImage(imagePath, width, height);
    }

    public String getText() {
        return out.getPresenter().getModel().getViewManager().getView().getText();
    }

    @Override
    public JComponent getComponent() {
        return out.getPresenter().getModel().getViewManager().getView();
    }
}
