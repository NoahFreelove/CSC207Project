package com.project.interface_adapters.general;

import com.project.use_cases.general.GameOutputBoundary;

import javax.swing.*;
import java.awt.*;

public class ViewPresenter implements GameOutputBoundary {
    private static ViewPresenter instance;
    private ViewModel viewModelData;

    private ViewPresenter(int width, int height, String title) {
        viewModelData = new ViewModel(width, height, title);
    }

    public static ViewPresenter getInstance() {
        return instance;
    }

    public static void makeInstance(int width, int height, String title) {
        instance = new ViewPresenter(width, height, title);
    }

    public void addUIComponent(JComponent component) {
        viewModelData.getManager().getView().addUIComponent(component);
    }

    public ViewModel getViewModel() {
        return viewModelData;
    }

    public void setWindowSize(int width, int height) {
        viewModelData.getManager().getView().setWindowSize(width, height);
    }

    public void removeUIElement(JComponent jComponent) {
        viewModelData.getManager().getView().removeUIElement(jComponent);
    }

    public void repaint() {
        viewModelData.getManager().getView().repaint();
    }

    public Component getUIView() {
        return viewModelData.getManager().getView().getUIView();
    }
}
