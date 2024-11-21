package com.project.engine.Core.Window;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import com.project.engine.Core.Engine;
import com.project.engine.Core.Scene;
import com.project.engine.Core.Tuple;
import com.project.engine.Rendering.GamePanel;

import static com.project.engine.Core.Window.Input.*;

public final class GameWindow {

    // region UI and Scaling
    private JLayeredPane layeredPane;
    private JPanel uiRoot = new JPanel();
    private GamePanel gamePanel;
    private final Map<Component, Rectangle> originalBoundsMap = new HashMap<>();
    private final Map<Component, Float> originalFontSizeMap = new HashMap<>();
    float scaleFactorX = 1.0f;
    float scaleFactorY = 1.0f;
    // endregion

    // region Window
    private int initialWidth;
    private int initialHeight;

    private int targetWidth;
    private int targetHeight;

    private int actualWidth;
    private int actualHeight;

    private String name;
    private JFrame window;
    private volatile boolean shouldClose = false;
    private AtomicBoolean ready = new AtomicBoolean(false);
    private volatile Scene activeScene;

    // endregion

    // region FPS and Delta Attributes
    public static final int BASE_FPS = 60; // Default FPS for all new windows
    private float desiredFPS = BASE_FPS;
    private float desiredDelta = 1.0f / desiredFPS;
    private float delta = 0;
    private float actualFPS = 0;
    private boolean changingScene = true;

    // endregion

    // region Input
    private int mouseX;
    private int mouseY;
    private HashMap<String, Boolean> keys = new HashMap<>();
    // endregion

    private GameWindow(int width, int height, String title) {
        this.targetWidth = (actualWidth = (initialWidth = width));
        this.targetHeight = (actualHeight = (initialHeight = height));
        this.name = title;
        setActiveScene(Scene.NullScene());

        SwingUtilities.invokeLater(() -> configGameWindow(width, height, title));
    }

    public static GameWindow createGameWindow(int width, int height, String title) {
        return new GameWindow(width, height, title);
    }

    private void configGameWindow(int width, int height, String title) {
        window = new JFrame(title);
        window.setSize(width, height);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // When you press tab thank this line for not crashing the game!
        window.setFocusTraversalPolicy(new NoFocusTraversalPolicy());
        window.setVisible(true);
        window.setResizable(false);

        layeredPane = new JLayeredPane();
        layeredPane.setSize(width, height);
        window.setContentPane(layeredPane);

        // Create and add the GamePanel
        gamePanel = new GamePanel(this);
        gamePanel.setBounds(0, 0, width, height);
        layeredPane.add(gamePanel, JLayeredPane.DEFAULT_LAYER); // Game layer

        // UI Root Panel
        uiRoot = new JPanel();
        uiRoot.setBounds(0, 0, width, height);
        uiRoot.setOpaque(false);
        uiRoot.setLayout(null); // Set layout to null, so we can just absolute position everything
        layeredPane.add(uiRoot, JLayeredPane.PALETTE_LAYER); // UI layer

        addKeyboardListeners(window, () -> activeScene, keys);
        addMouseListeners(gamePanel, () -> activeScene, keys);
        addMouseMotionListeners(gamePanel, this::setMousePosition, this::setMousePosition);

        this.ready.set(true);

        new Thread(this::gameLoop).start();
    }

    public boolean isReady() {
        return ready.get();
    }

    private float physicsUpdateRatio = 4f;
    private float physicsUpdateInterval = 1.0f / (desiredFPS * physicsUpdateRatio);

    public void gameLoop() {
        final int TARGET_FPS = (int) desiredFPS;
        final long OPTIMAL_TIME = 1_000_000_000 / TARGET_FPS;
        long lastLoopTime = System.nanoTime();
        double physicsAccumulator = 0.0;

        while (!shouldClose) {
            if (activeScene == null) {
                continue;
            }

            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            delta = updateLength / 1_000_000_000.0f;

            physicsAccumulator += delta;

            Engine.getInstance().update(activeScene, delta);

            // Physics updates at fixed intervals
            while (physicsAccumulator >= physicsUpdateInterval) {
                Engine.getInstance().physicsUpdate(activeScene);
                physicsAccumulator -= physicsUpdateInterval;
            }

            SwingUtilities.invokeLater(() -> {
                if (!changingScene) {
                    gamePanel.repaint();
                }
            });

            actualFPS = 1_000_000_000.0f / updateLength;

            try {
                long sleepTime = (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1_000_000;
                if (sleepTime > 0) {
                    Thread.sleep(sleepTime);
                } else {
                    Thread.yield();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        if (window != null) {
            window.dispose();
        }
    }


    public float getPhysicsUpdateRatio() {
        return physicsUpdateRatio;
    }

    public void addUIElement(JComponent component) {
        SwingUtilities.invokeLater(() -> {
            // Store original bounds for components
            Rectangle originalBounds = component.getBounds();
            originalBoundsMap.put(component, new Rectangle(originalBounds));

            // Store original font sizes
            Font font = component.getFont();
            if (font != null) {
                originalFontSizeMap.put(component, font.getSize2D());
            }

            // Scale the component positions, sizes, and fonts based on new scaling factors
            scaleComponentAndFont(component);

            // Force a repaint of the UI root, so we don't have to wait for whenever swing feels like it
            uiRoot.add(component);
            uiRoot.revalidate();
            uiRoot.repaint();
        });
    }

    private void scaleComponentAndFont(Component component) {
        // Scale position and size
        Rectangle originalBounds = originalBoundsMap.get(component);
        if (originalBounds != null) {
            int newX = (int) (originalBounds.x * scaleFactorX);
            int newY = (int) (originalBounds.y * scaleFactorY);
            int newWidth = (int) (originalBounds.width * scaleFactorX);
            int newHeight = (int) (originalBounds.height * scaleFactorY);
            component.setBounds(newX, newY, newWidth, newHeight);
        }

        // Scale font size
        Float originalFontSize = originalFontSizeMap.get(component);
        if (originalFontSize != null) {
            float newFontSize = originalFontSize * Math.min(scaleFactorX, scaleFactorY);
            component.setFont(component.getFont().deriveFont(newFontSize));
        }
    }

    public void setWindowSizeForce(int width, int height) {
        initialWidth = width;
        initialHeight = height;
        setWindowSize(width, height);
    }

    public void setWindowSize(int width, int height) {
        this.targetWidth = width;
        this.targetHeight = height;
        SwingUtilities.invokeLater(() -> {
            window.setSize(width, height);
            this.actualWidth = width;
            this.actualHeight = height;

            scaleFactorX = (float) width / initialWidth;
            scaleFactorY = (float) height / initialHeight;

            // Adjust game panel and UI root bounds
            gamePanel.setBounds(0, 0, width, height);
            uiRoot.setBounds(0, 0, width, height);

            // Scale each UI component and its font (if applicable) based on the new scaling factors
            for (Component component : uiRoot.getComponents()) {
                scaleComponentAndFont(component);
            }

            // Refresh the UI by invoking an update
            uiRoot.revalidate();
            uiRoot.repaint();
        });
    }

    public void refocusInWindow() {
        window.requestFocusInWindow();
    }

    public Tuple<Integer, Integer> getWindowSize() {
        return new Tuple<>(targetWidth, targetHeight);
    }

    public Tuple<Integer, Integer> getActualWindowSize() {
        return new Tuple<>(actualWidth, actualHeight);
    }

    public void closeWindow() {
        unloadActiveScene();
        shouldClose = true;
    }

    public void setName(String newName) {
        if (!isReady())
            return;

        this.name = newName;
        window.setTitle(newName);
    }

    public String getName() {
        return name;
    }

    public Scene getActiveScene() {
        return activeScene;
    }

    public int getMouseX() {
        return (int) (mouseX / scaleFactorX);
    }

    public int getMouseY() {
        return (int) (mouseY / scaleFactorY);
    }

    private void setMousePosition(int x, int y) {
        mouseX = x;
        mouseY = y;
    }

    private void unloadActiveScene() {
        if (this.activeScene != null) {
            Engine.getInstance().stopScene(this.activeScene);

            CopyOnWriteArrayList<JComponent> sceneUI = this.activeScene.getUIElements();
            for (JComponent jComponent : sceneUI) {
                uiRoot.remove(jComponent);
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
        Engine.getInstance().start(activeScene);
        Engine.getInstance().update(activeScene, 0);

        if (window != null) {
            window.requestFocusInWindow();
        }

        changingScene = false;
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
        this.physicsUpdateInterval = 1.0f / (desiredFPS * physicsUpdateRatio);
    }

    public void setPhysicsUpdateRatio(float physicsUpdateRatio) {
        this.physicsUpdateRatio = physicsUpdateRatio;
        this.physicsUpdateInterval = 1.0f / (desiredFPS * physicsUpdateRatio);
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

    public void removeUIElement(JComponent component) {
        SwingUtilities.invokeLater(() -> {
            if(component == null)
                return;
            uiRoot.remove(component);
            uiRoot.revalidate();
            uiRoot.repaint();
        });
    }

    public JComponent getUIElement(int index) {
        return (JComponent) uiRoot.getComponent(index);
    }

    public float getScaleFactorX() {
        return scaleFactorX;
    }

    public float getScaleFactorY() {
        return scaleFactorY;
    }

    public void resetInput() {
        keys.forEach((k, v) -> keys.put(k, false));
    }

    public Component getRootPane() {
        return uiRoot;
    }
}
