package com.project.engine.Core;

import com.project.engine.Input.EInputType;

import javax.swing.*;
import java.awt.event.*;
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
    private int mouseX = 0;
    private int mouseY = 0;
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
        setActiveScene(Scene.NullScene());
        Thread t = new Thread(() -> {
            window = new JFrame(title);
            window.setSize(width, height);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setVisible(true);

            root = new JPanel();

            // set input listeners
            addKeyboardListeners();

            // add mouse listeners
            addMouseListeners();

            // add mouse motion listener
            addMouseMotionListeners();

            gameLoop();
        });
        this.windowThread = t;
        t.start();
    }

    private void addKeyboardListeners() {
        window.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                Engine.getInstance().input(activeScene, String.valueOf(e.getKeyChar()), EInputType.TYPED);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                Engine.getInstance().input(activeScene, String.valueOf(e.getKeyChar()), EInputType.PRESS);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                Engine.getInstance().input(activeScene, String.valueOf(e.getKeyChar()), EInputType.RELEASE);
            }
        });
    }

    private void addMouseMotionListeners() {
        window.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });
    }

    private void addMouseListeners() {
        window.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Engine.getInstance().input(activeScene, mouseNumToString(e.getButton()), EInputType.TYPED);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                Engine.getInstance().input(activeScene, mouseNumToString(e.getButton()), EInputType.PRESS);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Engine.getInstance().input(activeScene, mouseNumToString(e.getButton()), EInputType.RELEASE);
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
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

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public void setActiveScene(Scene activeScene) {
        if (activeScene == null) {
            System.err.println("Cannot set the active scene to null");
            return;
        }

        if(this.activeScene != null) {
            Engine.getInstance().stop(this.activeScene);
        }

        this.activeScene = activeScene;
        Engine.getInstance().start(activeScene);
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

    private static String mouseNumToString(int button){
        switch (button){
            case MouseEvent.BUTTON1:
                return "LMB";
            case MouseEvent.BUTTON2:
                return "MMB";
            case MouseEvent.BUTTON3:
                return "RMB";
            default:
                return "?MB";
        }
    }


}
