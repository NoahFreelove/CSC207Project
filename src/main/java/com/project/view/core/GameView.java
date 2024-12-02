package com.project.view.core;

import com.project.interface_adapters.core.display.ViewController;
import com.project.interface_adapters.core.display.ViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

// This is for the game rendering, it renders to a special panel,
// while the UI elements are rendered to a separate panel
public class GameView extends JPanel {
    private BufferedImage offScreenBuffer;
    private View viewWindow;
    private final ViewController viewController = new ViewController();

    public GameView(View window, int width, int height) {
        this.viewWindow = window;
        this.setBounds(0, 0, width, height);
        this.setOpaque(true);
        this.setDoubleBuffered(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ViewModel model = viewWindow.getModel();

        int renderWidth = (int) (getWidth() / model.getScaleFactorX());
        int renderHeight = (int) (getHeight() / model.getScaleFactorY());

        // Only recreate the buffer if the size has changed
        if (offScreenBuffer == null || offScreenBuffer.getWidth() != renderWidth || offScreenBuffer.getHeight() != renderHeight) {
            offScreenBuffer = new BufferedImage(renderWidth, renderHeight, BufferedImage.TYPE_INT_ARGB);
        }

        Graphics2D g2d = offScreenBuffer.createGraphics();

        g2d.setComposite(AlphaComposite.Clear);
        g2d.fillRect(0, 0, offScreenBuffer.getWidth(), offScreenBuffer.getHeight());
        g2d.setComposite(AlphaComposite.SrcOver);

        // render scene
        viewController.queueRedraw(g2d);
        g2d.dispose();

        // draw da buffer
        g.drawImage(offScreenBuffer, 0, 0, getWidth(), getHeight(), null);
    }
}
