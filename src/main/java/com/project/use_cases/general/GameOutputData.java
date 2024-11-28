package com.project.use_cases.general;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import com.project.entity.core.Scene;
import com.project.entity.core.Tuple;
import com.project.entity.windowing.WindowUICallback;

public final class GameOutputData {
    // region Window
    private String name;
    private volatile boolean shouldClose = false;
    private final AtomicBoolean ready = new AtomicBoolean(false);
    private volatile Scene activeScene;
    private boolean changingScene = true;
    private GameOutputBoundary outputBoundary;

    // endregion
    private GameOutputData(){}

    public static GameOutputData nullWindow(){
        return new GameOutputData();
    }

    private GameOutputData(int width, int height, String title, Runnable gameLoop) {
        this.outputBoundary = new GameOutputBoundary() {};
        this.name = title;
        setActiveScene(Scene.NullScene());

        SwingUtilities.invokeLater(() -> configGameWindow(width, height, title, gameLoop));
    }

    public static GameOutputData createGameWindow(int width, int height, String title, Runnable gameLoop) {
        return new GameOutputData(width, height, title, gameLoop);
    }

    private void configGameWindow(int width, int height, String title, Runnable gameLoop) {
        outputBoundary.createViewPresenter(width, height, title);
        this.ready.set(true);
        new Thread(gameLoop).start();
    }

    public boolean isReady() {
        return ready.get();
    }

    public boolean shouldClose() {
        return shouldClose;
    }

    public boolean changingScene() {
        return changingScene;
    }

    public boolean isFocused() {
        return getUnderlyingWindow().isFocused();
    }

    public void addUIElement(JComponent component) {
        outputBoundary.getViewPresenter().addUIComponent(component);
    }

    public void setWindowSizeForce(int width, int height) {
        outputBoundary.getViewPresenter().getViewModel().setInitialWidth(width);
        outputBoundary.getViewPresenter().getViewModel().setInitialHeight(height);
        setWindowSize(width, height);
    }

    public JFrame getUnderlyingWindow() {
        try {
            return outputBoundary.getViewPresenter().getViewModel().getWindow();
        } catch (Exception e) {
            return null;
        }
    }

    public JPanel getGamePanel() {
        return outputBoundary.getViewPresenter().getViewModel().getGameView();
    }

    public void setWindowSize(int width, int height) {
        outputBoundary.getViewPresenter().getViewModel().setTargetWidth(width);
        outputBoundary.getViewPresenter().getViewModel().setTargetHeight(height);
        outputBoundary.getViewPresenter().setWindowSize(width, height);
    }

    public void refocusInWindow() {
        getUnderlyingWindow().requestFocusInWindow();
    }

    public Tuple<Integer, Integer> getWindowSize() {
        return new Tuple<>(
                outputBoundary.getViewPresenter().getViewModel().getTargetWidth(),
                outputBoundary.getViewPresenter().getViewModel().getTargetHeight());
    }

    public Tuple<Integer, Integer> getActualWindowSize() {
        return new Tuple<>(
                outputBoundary.getViewPresenter().getViewModel().getActualWidth(),
                outputBoundary.getViewPresenter().getViewModel().getActualHeight());
    }

    public void closeWindow() {
        unloadActiveScene();
        shouldClose = true;
    }

    public String getName() {
        return name;
    }

    public Scene getActiveScene() {
        return activeScene;
    }

    public void removeUIElement(JComponent jComponent) {
        outputBoundary.getViewPresenter().removeUIElement(jComponent);
    }

    private void unloadActiveScene() {
        if (this.activeScene != null) {
            GameInteractor.getInstance().stopScene(this.activeScene);

            CopyOnWriteArrayList<JComponent> sceneUI = this.activeScene.getUIElements();
            for (JComponent jComponent : sceneUI) {
                removeUIElement(jComponent);
            }
        }
        this.activeScene = Scene.NullScene();
    }

    public void setActiveScene(Scene activeScene) {
        if (activeScene == null) {
            throw new RuntimeException(new IllegalArgumentException("Scene cannot be null"));
        }

        changingScene = true;

        unloadActiveScene();
        this.activeScene = activeScene;

        this.activeScene.addUICallback(new WindowUICallback() {
            @Override
            public void addUIComponent(JComponent component) {
                addUIElement(component);
            }

            @Override
            public void removeUIComponent(JComponent component) {
                removeUIElement(component);
            }
        });

        this.activeScene.getUIElements().forEach(this::addUIElement);
        GameInteractor.getInstance().start(activeScene);
        GameInteractor.getInstance().update(activeScene, 0);

        if (getUnderlyingWindow() != null) {
            getUnderlyingWindow().requestFocusInWindow();
        }

        changingScene = false;
    }


    public void repaint() {
        outputBoundary.getViewPresenter().repaint();
    }

    public float getScaleFactorX() {
        return outputBoundary.getViewPresenter().getViewModel().getScaleFactorX();
    }

    public float getScaleFactorY() {
        return outputBoundary.getViewPresenter().getViewModel().getScaleFactorY();
    }

    public Component getRootPane() {
        return outputBoundary.getViewPresenter().getUIView();
    }
}
