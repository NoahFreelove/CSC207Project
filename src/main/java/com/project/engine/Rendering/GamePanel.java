package com.project.engine.Rendering;

import com.project.engine.Core.Engine;
import com.project.engine.Core.Scene;
import com.project.engine.Core.Window.GameWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

// This is for the game rendering, it renders to a special panel,
// while the UI elements are rendered to a separate panel
public class GamePanel extends JPanel {
    private BufferedImage offScreenBuffer;
    private final GameWindow gameWindow;

    public GamePanel(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        this.setOpaque(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int renderWidth = (int) (getWidth() / gameWindow.getScaleFactorX());
        int renderHeight = (int) (getHeight() / gameWindow.getScaleFactorY());

        if (offScreenBuffer == null || offScreenBuffer.getWidth() != renderWidth || offScreenBuffer.getHeight() != renderHeight) {
            offScreenBuffer = new BufferedImage(renderWidth, renderHeight, BufferedImage.TYPE_INT_ARGB);
        }

        Graphics2D g2d = offScreenBuffer.createGraphics();

        // Clear the buffer, render the scene to the buffer, then draw it on the panel
        g2d.clearRect(0, 0, offScreenBuffer.getWidth(), offScreenBuffer.getHeight());

        Engine.getInstance().render(gameWindow.getActiveScene(), g2d);
        g2d.dispose();
        g.drawImage(offScreenBuffer, 0, 0, getWidth(), getHeight(), null);
    }
}