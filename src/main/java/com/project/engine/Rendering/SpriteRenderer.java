package com.project.engine.Rendering;

import com.project.engine.Core.GameObject;
import com.project.engine.Core.Scene;
import com.project.engine.IO.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class SpriteRenderer extends RenderBase {
    private ImageIcon image;
    private final JLabel container;
    private float spriteRotation = 0;

    private int originalXSize;
    private int originalYSize;

    public SpriteRenderer(String spritePath){
        super();
        System.out.println(spritePath);
        image = ImageLoader.loadImage(spritePath);

        container = new JLabel(image);

        setBaseComponent(container);

        this.originalXSize = image.getIconWidth();
        this.originalYSize = image.getIconWidth();

        this.setSize(1, 1);

    }

    @Override
    protected void setSize(int xSizeFactor, int ySizeFactor) {
        super.setSize(xSizeFactor*originalXSize, ySizeFactor*originalYSize);
        image = getScaledImage(xSizeFactor*originalXSize, ySizeFactor*originalYSize);
        container.setIcon(image);
    }

    private ImageIcon getScaledImage(int x, int y) {
        // Ensure dimensions are positive
        int width = Math.abs(x);
        int height = Math.abs(y);

        ImageIcon scaledImage = new ImageIcon(image.getImage().
                getScaledInstance(width, height, Image.SCALE_DEFAULT));

        BufferedImage unflippedImage = new BufferedImage(
                scaledImage.getIconWidth(),
                scaledImage.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = unflippedImage.createGraphics();
        scaledImage.paintIcon(null, g2d, 0, 0);
        g2d.dispose();

        // Create an AffineTransform for reflection
        AffineTransform transform = new AffineTransform();

        // Reflect horizontally (mirror left to right)
        transform.scale(Math.signum(x), Math.signum(y));

        transform.rotate(Math.toRadians(spriteRotation),
                (double) scaledImage.getIconWidth() / 2, (double) scaledImage.getIconHeight() / 2);

        if (x < 0) {
            transform.translate(-scaledImage.getIconWidth(), 0);
        }
        if (y < 0) {
            transform.translate(0, scaledImage.getIconHeight());
        }

        // Apply the transformation
        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        BufferedImage reflectedImage = op.filter(unflippedImage, null);

        return new ImageIcon(reflectedImage);
    }

    @Override
    public JComponent renderComponent(GameObject attached, Scene scene) {
        setPosition(attached.getTransform().getPositionX(), attached.getTransform().getPositionY());
        return super.renderComponent(attached, scene);
    }
}
