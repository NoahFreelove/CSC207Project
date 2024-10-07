package com.project.engine.Core.Window;

import javax.swing.*;

import java.awt.event.*;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import com.project.engine.Core.Engine;
import com.project.engine.Core.Scene;
import com.project.engine.Input.InputMods;
import com.project.engine.Input.EInputType;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a window for the game to be displayed in.
 */
public final class GameWindow {

    public static final int BASE_FPS = 60;

    // region Atomic Fun Stuff
    private final AtomicBoolean shouldClose = new AtomicBoolean(false);
    private Thread windowThread = null;
    // endregion

    // region Swing Stuff
    private JFrame window;
    private JPanel root;
    // endregion

    // region Instance Variables
    private final String name;
    private Scene activeScene;

    // endregion

    // region Game Loop Variables
    private long lastUpdate = System.currentTimeMillis();
    private float desiredFPS = BASE_FPS;
    private float desiredDelta = 1.0f / desiredFPS;
    private float delta = 0;
    // endregion

    // region Input
    private int mouseX;
    private int mouseY;
    private HashMap<String, Boolean> keys = new HashMap<>();
    // endregion

    /**
     * Create a new game window on a new thread.
     * @param width window width
     * @param height window height
     * @param title window title (will also be the same name if you want to uniquely identify the window)
     */
    private GameWindow(int width, int height, String title) {
        this.name = title;
        setActiveScene(Scene.NullScene());
        Thread t = new Thread(() -> configGameWindow(width, height, title));
        this.windowThread = t;
        t.start();
    }

    /**
     * Create a new game window. This is a factory method.
     * @param width window width
     * @param height window height
     * @param title window title (will also be the same name if you want to uniquely identify the window)
     * @return the new game window
     */
    public static GameWindow createGameWindow(int width, int height, String title) {
        return new GameWindow(width, height, title);
    }

    private void configGameWindow(int width, int height, String title) {
        window = new JFrame(title);

        root = new JPanel();
        root.setLayout(null);
        root.setBounds(0,0,width,height);

        JPanel firstFrame = new JPanel();
        firstFrame.setLayout(null);
        firstFrame.setBounds(0,0,width,height);
        root.add(firstFrame);

        window.add(root);

        window.setSize(width, height);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setFocusTraversalPolicy(new NoFocusTraversalPolicy());
        window.setVisible(true);
        // set input listeners
        addKeyboardListeners();

        // add mouse listeners
        addMouseListeners();

        // add mouse motion listener
        addMouseMotionListeners();

        gameLoop();
    }

    private void addKeyboardListeners() {
        window.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                Engine.getInstance().input(activeScene, extractKeyName(e), EInputType.TYPED, getMods(e));
            }

            @Override
            public void keyPressed(KeyEvent e) {
                String key = extractKeyName(e);
                Engine.getInstance().input(activeScene, key, EInputType.PRESS, getMods(e));
                keys.put(key, true);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                String key = extractKeyName(e);
                Engine.getInstance().input(activeScene, key, EInputType.RELEASE, getMods(e));
                keys.put(key, false);
            }
        });
    }

    private static @NotNull String extractKeyName(KeyEvent e) {
        // if its an arrow key, we want to use the key code instead of the character and convert it to a string
        String key = String.valueOf(e.getKeyChar());
        // switch with arrow keys
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                key = "UP";
                break;
            case KeyEvent.VK_DOWN:
                key = "DOWN";
                break;
            case KeyEvent.VK_LEFT:
                key = "LEFT";
                break;
            case KeyEvent.VK_RIGHT:
                key = "RIGHT";
                break;
                // we need cases for function keys and escape and backspace, etc.
            case KeyEvent.VK_ESCAPE:
                key = "ESC";
                break;
            case KeyEvent.VK_BACK_SPACE:
                key = "BACKSPACE";
                break;
            case KeyEvent.VK_ENTER:
                key = "ENTER";
                break;
            case KeyEvent.VK_SPACE:
                key = "SPACE";
                break;
            case KeyEvent.VK_SHIFT:
                key = "SHIFT";
                break;
            case KeyEvent.VK_CONTROL:
                key = "CTRL";
                break;
            case KeyEvent.VK_ALT:
                key = "ALT";
                break;
            case KeyEvent.VK_TAB:
                key = "TAB";
                break;
            case KeyEvent.VK_CAPS_LOCK:
                key = "CAPS";
                break;
            case KeyEvent.VK_INSERT:
                key = "INSERT";
                break;
            case KeyEvent.VK_DELETE:
                key = "DELETE";
                break;
            case KeyEvent.VK_HOME:
                key = "HOME";
                break;
            case KeyEvent.VK_END:
                key = "END";
                break;
            case KeyEvent.VK_PAGE_UP:
                key = "PAGEUP";
                break;
            case KeyEvent.VK_PAGE_DOWN:
                key = "PAGEDOWN";
                break;
            case KeyEvent.VK_F1:
                key = "F1";
                break;
            case KeyEvent.VK_F2:
                key = "F2";
                break;
            case KeyEvent.VK_F3:
                key = "F3";
                break;
            case KeyEvent.VK_F4:
                key = "F4";
                break;
            case KeyEvent.VK_F5:
                key = "F5";
                break;
            case KeyEvent.VK_F6:
                key = "F6";
                break;
            case KeyEvent.VK_F7:
                key = "F7";
                break;
            case KeyEvent.VK_F8:
                key = "F8";
                break;
            case KeyEvent.VK_F9:
                key = "F9";
                break;
            case KeyEvent.VK_F10:
                key = "F10";
                break;
            case KeyEvent.VK_F11:
                key = "F11";
                break;
            case KeyEvent.VK_F12:
                key = "F12";
                break;
            default:
                break;
        }
        return key.toUpperCase();
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
                Engine.getInstance().input(activeScene,
                        mouseNumToString(e.getButton()), EInputType.TYPED, getMouseMods(e));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                Engine.getInstance().input(activeScene,
                        mouseNumToString(e.getButton()), EInputType.PRESS, getMouseMods(e));
                keys.put(mouseNumToString(e.getButton()), true);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Engine.getInstance().input(activeScene,
                        mouseNumToString(e.getButton()), EInputType.RELEASE, getMouseMods(e));
                keys.put(mouseNumToString(e.getButton()), false);
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    private int getMods(KeyEvent event) {
        return getModsGeneric(event.isShiftDown(), event.isControlDown(), event.isAltDown());
    }

    private int getMouseMods(MouseEvent mouseEvent) {
        return getModsGeneric(mouseEvent.isShiftDown(), mouseEvent.isControlDown(), mouseEvent.isAltDown());
    }

    private int getModsGeneric(boolean shiftDown, boolean controlDown, boolean altDown) {
        int mods = 0;
        if (shiftDown) {
            mods |= InputMods.SHIFT;
        }
        if (controlDown) {
            mods |= InputMods.CTRL;
        }
        if (altDown) {
            mods |= InputMods.ALT;
        }
        return mods;
    }

    public void gameLoop() {
        while (!shouldClose.get()) {
            if (activeScene == null) {
                continue;
            }

            long now = System.currentTimeMillis();
            delta += (now - lastUpdate) / 1000.0f;
            lastUpdate = now;

            // Only draw at desired frame rate, but update can be changed to a faster time
            // (for physics and other calculations)
            while (delta >= desiredDelta) {
                renderWindow();
                Engine.getInstance().update(activeScene, delta);
                delta -= desiredDelta;
            }

        }
        if (window != null) {
            window.dispose();
        }
    }

    private void renderWindow() {
        Engine.getInstance().render(root, activeScene);
        window.repaint();
    }

    public void closeWindow() {
        unloadActiveScene();
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

    private void unloadActiveScene() {
        if (this.activeScene != null) {
            Engine.getInstance().stopScene(this.activeScene);
        }
        this.activeScene = Scene.NullScene();
    }

    public void setActiveScene(Scene activeScene) {
        if (activeScene == null) {
            throw new RuntimeException(new IllegalArgumentException("Scene cannot be null"));
        }
        unloadActiveScene();

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

    private static String mouseNumToString(int button) {
        String result = "?MB";
        switch (button) {
            case MouseEvent.BUTTON1:
                result = "LMB";
                break;
            case MouseEvent.BUTTON2:
                result = "MMB";
                break;
            case MouseEvent.BUTTON3:
                result = "RMB";
                break;
            default:
                break;
        }
        return result;
    }

    public boolean isKeyPressed(String key) {

        return keys.getOrDefault(key.toUpperCase(), false);
    }
}
