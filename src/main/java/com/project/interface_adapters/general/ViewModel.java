package com.project.interface_adapters.general;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ViewModel {
    private JFrame window;
    private ViewManager manager;
    private int width, height;

    private int initialWidth, initialHeight;

    private AtomicInteger targetWidth, targetHeight;

    private int actualWidth, actualHeight;

    private float scaleFactorX = 1.0f, scaleFactorY = 1.0f;

    public ViewModel(JFrame window) {
        manager = new ViewManager() {};
        this.window = window;
        manager.createView(this, 0, 0, "Gamw Window");
    }

    public ViewModel(int width, int height, String title) {
        manager = new ViewManager() {};
        this.targetWidth = new AtomicInteger((actualWidth = (initialWidth = width)));
        this.targetHeight = new AtomicInteger((actualHeight = (initialHeight = height)));
        manager.createView(this, width, height, title);
    }

    public ViewManager getManager() {
        return manager;
    }

    public JPanel getGameView() {
        return manager.getView().getGameView();
    }

    public JFrame getWindow() {
        return window;
    }

    public void setWindow(JFrame window) {
        this.window = window;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getInitialWidth() {
        return initialWidth;
    }

    public void setInitialWidth(int initialWidth) {
        this.initialWidth = initialWidth;
    }

    public int getInitialHeight() {
        return initialHeight;
    }

    public void setInitialHeight(int initialHeight) {
        this.initialHeight = initialHeight;
    }

    public int getTargetWidth() {
        return targetWidth.get();
    }

    public void setTargetWidth(int targetWidth) {
        this.targetWidth.set(targetWidth);
    }

    public int getTargetHeight() {
        return targetHeight.get();
    }

    public void setTargetHeight(int targetHeight) {
        this.targetHeight.set(targetHeight);
    }

    public int getActualWidth() {
        return actualWidth;
    }

    public void setActualWidth(int actualWidth) {
        this.actualWidth = actualWidth;
    }

    public int getActualHeight() {
        return actualHeight;
    }

    public void setActualHeight(int actualHeight) {
        this.actualHeight = actualHeight;
    }

    public float getScaleFactorX() {
        return scaleFactorX;
    }

    public void setScaleFactorX(float scaleFactorX) {
        this.scaleFactorX = scaleFactorX;
    }

    public float getScaleFactorY() {
        return scaleFactorY;
    }

    public void setScaleFactorY(float scaleFactorY) {
        this.scaleFactorY = scaleFactorY;
    }

}