package com.project.engine.Core.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import com.project.engine.Core.Engine;
import com.project.engine.Core.Scene;
import com.project.engine.Input.EInputType;
import com.project.engine.Input.InputMods;

public final class GameWindow {
    public static final int BASE_FPS = 120;

    private final AtomicBoolean shouldClose = new AtomicBoolean(false);
    private Thread windowThread = null;
    private JFrame window;
    private final String name;
    private volatile Scene activeScene;
    private long lastUpdate = System.currentTimeMillis();
    private float desiredFPS = BASE_FPS;
    private float desiredDelta = 1.0f / desiredFPS;
    private float delta = 0;
    private long lastFrame = System.currentTimeMillis();
    private float actualFPS = 0;
    private BufferedImage offScreenBuffer;
    private int mouseX;
    private int mouseY;
    private HashMap<String, Boolean> keys = new HashMap<>();
    private float scaleFactorX = 1.0f;
    private float scaleFactorY = 1.0f;
    private int initialWidth;
    private int initialHeight;

    private AtomicBoolean ready = new AtomicBoolean(false);

    private GameWindow(int width, int height, String title) {
        this.initialHeight = height;
        this.initialWidth = width;
        this.name = title;
        setActiveScene(Scene.NullScene());
        Thread t = new Thread(() -> configGameWindow(width, height, title));
        this.windowThread = t;
        t.start();
    }

    public static GameWindow createGameWindow(int width, int height, String title) {
        return new GameWindow(width, height, title);
    }

    private void configGameWindow(int width, int height, String title) {
        window = new JFrame(title);
        window.setSize(width, height);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setIgnoreRepaint(true); // Disable default repaint so we can control it
        window.setVisible(true); // Make the window visible
        window.setResizable(false); // Disable resizing
        window.createBufferStrategy(2); // Enable double buffering for smoother rendering
        addKeyboardListeners();
        addMouseListeners();
        addMouseMotionListeners();
        this.ready.set(true);
        gameLoop();
    }

    public boolean isReady() {
        return ready.get();
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

    private static String extractKeyName(KeyEvent e) {
        String key = String.valueOf(e.getKeyChar());
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
                Engine.getInstance().input(activeScene, mouseNumToString(e.getButton()), EInputType.TYPED, getMouseMods(e));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                Engine.getInstance().input(activeScene, mouseNumToString(e.getButton()), EInputType.PRESS, getMouseMods(e));
                keys.put(mouseNumToString(e.getButton()), true);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Engine.getInstance().input(activeScene, mouseNumToString(e.getButton()), EInputType.RELEASE, getMouseMods(e));
                keys.put(mouseNumToString(e.getButton()), false);
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
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

            if (delta >= desiredDelta) {
                renderWindow();
                Engine.getInstance().update(activeScene, delta);
                delta = 0;
                actualFPS = 1000.0f / (System.currentTimeMillis() - lastFrame);
                lastFrame = System.currentTimeMillis();
            }
        }
        if (window != null) {
            window.dispose();
        }
    }

    private void renderWindow() {
        int renderWidth = (int) (window.getWidth() / scaleFactorX);
        int renderHeight = (int) (window.getHeight() / scaleFactorY);

        if (offScreenBuffer == null || offScreenBuffer.getWidth() != renderWidth || offScreenBuffer.getHeight() != renderHeight) {
            offScreenBuffer = new BufferedImage(renderWidth, renderHeight, BufferedImage.TYPE_INT_ARGB);
        }

        Graphics2D g2d = offScreenBuffer.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        // Clear the entire buffer before rendering
        g2d.clearRect(0, 0, offScreenBuffer.getWidth(), offScreenBuffer.getHeight());

        // Render the scene at a lower resolution
        Engine.getInstance().render(activeScene, g2d);
        g2d.dispose();

        // Scale the rendered image to fit the window size
        Graphics g = window.getGraphics();
        g.drawImage(offScreenBuffer, 0, 0, window.getWidth(), window.getHeight(), null);
        g.dispose();
    }

    public void setWindowSize(int width, int height) {
        // set on swing thread
        SwingUtilities.invokeLater(() -> {
            window.setSize(width, height);
            scaleFactorX = (float) width / initialWidth;
            scaleFactorY = (float) height / initialHeight;
        });
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
        return (int) (mouseX / scaleFactorX); // Adjust mouseX by scaleFactor
    }

    public int getMouseY() {
        return (int) (mouseY / scaleFactorY); // Adjust mouseY by scaleFactor
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

    public float FPS() {
        return actualFPS;
    }

    public void fancyStatsPrint() {
        System.out.println("-----WIN STATS-----");
        System.out.println("Desired FPS: " + desiredFPS);
        System.out.println("Actual FPS: " + actualFPS);
        System.out.println("Delta Time: " + delta);
        System.out.println("Mouse Position: (" + getMouseX() + ", " + getMouseY() + ")");
        System.out.println("Keys Pressed: ");
        keys.forEach((k, v) -> {
            if (v) {
                System.out.println(k);
            }
        });
        System.out.println("---END WIN STATS---");
    }
}