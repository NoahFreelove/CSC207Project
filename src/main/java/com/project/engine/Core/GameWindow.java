package com.project.engine.Core;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameWindow {
    // region Atomic Fun Stuff
    private final AtomicBoolean shouldClose = new AtomicBoolean(false);
    private Thread windowThread = null;
    // endregion

    // region Swing Stuff
    private JFrame window = null;
    private JPanel root = null;
    // endregion

    // region Instance Variables
    private final String name;
    private Scene activeScene;
    // endregion

    // region Game Loop Variables
    private long lastUpdate = System.currentTimeMillis();
    private float desiredFPS = 60;
    private float desiredDelta = 1.0f / desiredFPS;
    private float delta = 0;
    // endregion
    
    public static GameWindow createGameWindow(int width, int height, String title){
        return new GameWindow(width, height, title);
    }
    
    private GameWindow(int width, int height, String title) {
        this.name = title;
        this.activeScene = Scene.NullScene();

        Thread t = new Thread(() -> {
            window = new JFrame(title);
            window.setSize(width, height);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setVisible(true);

            root = new JPanel();
            gameLoop();
        });
        this.windowThread = t;
        t.start();
    }



    public void gameLoop(){
        while (!shouldClose.get()){
            if (activeScene == null) {
                continue;
            }

            long now = System.currentTimeMillis();
            delta += (now - lastUpdate) / 1000.0f;
            lastUpdate = now;

            // Only draw at desired frame rate, but update can be changed to a faster time (for physics and other calculations)
            while (delta >= desiredDelta) {
                Engine.getInstance().render(activeScene);
                Engine.getInstance().update(activeScene, delta);
                delta -= desiredDelta;
            }

        }
        if (window != null){
            window.dispose();
        }
    }
    
    public void close(){
        shouldClose.set(true);
    }


    public String getName() {
        return name;
    }

    public Scene getActiveScene() {
        return activeScene;
    }

    public void setActiveScene(Scene activeScene) {
        if (activeScene == null) {
            System.err.println("Cannot set the active scene to null");
            return;
        }
        this.activeScene = activeScene;
    }

    public float getDelta() {
        return delta;
    }

    public float getDesiredFPS() {
        return desiredFPS;
    }

    public void setDesiredFPS(float desiredFPS) {
        this.desiredFPS = desiredFPS;
        this.desiredDelta = 1.0f / desiredFPS;
    }
}
