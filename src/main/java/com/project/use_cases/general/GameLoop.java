package com.project.use_cases.general;

import javax.swing.*;

public class GameLoop implements Runnable {
    public static final int BASE_FPS = 60; // Default FPS for all new windows
    private float desiredFPS = BASE_FPS;
    private float delta = 0;
    private float physicsUpdateRatio = 4f;
    private float physicsUpdateInterval = 1.0f / (desiredFPS * physicsUpdateRatio);

    @Override
    public void run() {
        GameInteractor e = GameInteractor.getInstance();
        final int TARGET_FPS = (int) desiredFPS;
        final long OPTIMAL_TIME = 1_000_000_000 / TARGET_FPS;
        long lastLoopTime = System.nanoTime();
        double physicsAccumulator = 0.0;
        e.getInputCache().init();

        while (!e.getPrimaryWindow().shouldClose()) {
            if (e.getPrimaryWindow().getActiveScene() == null) {
                continue;
            }

            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            delta = updateLength / 1_000_000_000.0f;

            physicsAccumulator += delta;

            GameInteractor.getInstance().update(e.getPrimaryWindow().getActiveScene(), delta);

            if(!e.getPrimaryWindow().isFocused()) {
                e.getInputCache().resetInput();
            }

            // Physics updates at fixed intervals
            while (physicsAccumulator >= physicsUpdateInterval) {
                GameInteractor.getInstance().physicsUpdate(e.getPrimaryWindow().getActiveScene());
                physicsAccumulator -= physicsUpdateInterval;
            }

            SwingUtilities.invokeLater(() -> {
                if (!e.getPrimaryWindow().changingScene()) {
                    e.getPrimaryWindow().repaint();
                }
            });

            try {
                long sleepTime = (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1_000_000;
                if (sleepTime > 0) {
                    Thread.sleep(sleepTime);
                } else {
                    Thread.yield();
                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }

        if (e.getPrimaryWindow().getUnderlyingWindow() != null) {
            e.getPrimaryWindow().getUnderlyingWindow().dispose();
        }
    }
}
