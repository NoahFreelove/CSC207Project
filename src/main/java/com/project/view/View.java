package com.project.view;

import javax.swing.*;
import com.project.entity.windowing.NoFocusTraversalPolicy;
import com.project.interface_adapters.general.ViewManager;
import com.project.interface_adapters.general.ViewModel;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class View implements ViewManager {
    private JLayeredPane layeredPane; // This ensures UI is drawn on top of game
    private GameView gameView;
    private UIView uiView;
    private final ViewModel model;

    private final Map<Component, Rectangle> originalBoundsMap = new HashMap<>();
    private final Map<Component, Float> originalFontSizeMap = new HashMap<>();

    private static View instance;

    private View(ViewModel model, int width, int height, String title) {
        this.model = model;
        configGameWindow(width, height, title);
    }

    public static void create(ViewModel model, int width, int height, String title) {
        instance = new View(model, width, height, title);
    }

    public static View getInstance() {
        return instance;
    }

    public void repaint() {
        gameView.repaint();
    }

    public ViewModel getModel() {
        return model;
    }

    private void configGameWindow(int width, int height, String title) {
        model.setWindow(new JFrame(title));
        model.getWindow().setSize(width,height);
        model.getWindow().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // When you press tab thank this line for not crashing the game!
        model.getWindow().setFocusTraversalPolicy(new NoFocusTraversalPolicy());
        model.getWindow().setVisible(true);
        model.getWindow().setResizable(false);

        layeredPane = new JLayeredPane();
        layeredPane.setSize(width, height);
        model.getWindow().setContentPane(layeredPane);

        // Create and add the GamePanel
        gameView = new GameView(this, width, height);
        layeredPane.add(gameView, JLayeredPane.DEFAULT_LAYER); // Game layer

        // UI Root Panel
        uiView = new UIView(width, height); // Set layout to null, so we can just absolute position everything
        layeredPane.add(uiView, JLayeredPane.PALETTE_LAYER); // UI layer
    }

    public void addUIComponent(JComponent component) {
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

                uiView.addComponent(component);
            });
    }

    public void setWindowSize(int width, int height) {
        SwingUtilities.invokeLater(() -> {
            model.setActualWidth(width);
            model.setActualHeight(height);

            model.setScaleFactorX((float) width / model.getInitialWidth());
            model.setScaleFactorY((float) height / model.getInitialHeight());

            // Adjust game panel and UI root bounds
            model.getWindow().setSize(width, height);
            gameView.setBounds(0, 0, width, height);
            uiView.setBounds(0, 0, width, height);

            // Scale each UI component and its font (if applicable) based on the new scaling factors
            for (Component component : uiView.getComponents()) {
                scaleComponentAndFont(component);
            }

            // Refresh the UI by invoking an update
            uiView.refresh();
        });
    }

    public void removeUIElement(JComponent component) {
        SwingUtilities.invokeLater(() -> {
            if(component == null)
                return;

            uiView.removeComponent(component);
        });
    }

    private void scaleComponentAndFont(Component component) {
        // Scale position and size
        Rectangle originalBounds = originalBoundsMap.get(component);
        if (originalBounds != null) {
            int newX = (int) (originalBounds.x * model.getScaleFactorX());
            int newY = (int) (originalBounds.y * model.getScaleFactorY());
            int newWidth = (int) (originalBounds.width * model.getScaleFactorX());
            int newHeight = (int) (originalBounds.height * model.getScaleFactorY());
            component.setBounds(newX, newY, newWidth, newHeight);
        }

        // Scale font size
        Float originalFontSize = originalFontSizeMap.get(component);
        if (originalFontSize != null) {
            float newFontSize = originalFontSize * Math.min(model.getScaleFactorX(), model.getScaleFactorY());
            component.setFont(component.getFont().deriveFont(newFontSize));
        }
    }

    public JPanel getGameView() {
        return gameView;
    }

    public JPanel getUIView() {
        return uiView;
    }
}
