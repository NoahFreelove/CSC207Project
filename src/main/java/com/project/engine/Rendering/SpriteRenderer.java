package com.project.engine.Rendering;

import com.project.engine.Core.GameObject;
import com.project.engine.Core.Scene;
import com.project.engine.Core.Tuple;
import com.project.engine.IO.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class SpriteRenderer extends RenderBase {
    private final ImageIcon image;
    private final JLabel container;
    private float spriteRotation = 0;

    public SpriteRenderer(String spritePath){
        super();
        image = ImageLoader.loadImage(spritePath);
        setWidth(image.getIconWidth());
        setHeight(image.getIconHeight());
        container = new JLabel(image);

        setBaseComponent(container);
    }

    private ImageIcon getScaledImage(int x, int y){
        ImageIcon scaledImage = new ImageIcon(image.getImage().
                getScaledInstance(x, y, Image.SCALE_DEFAULT));

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
                scaledImage.getIconWidth() / 2, scaledImage.getIconHeight() / 2);

        if(x < 0) {
            transform.translate(-scaledImage.getIconWidth(), 0);
        }
        if(y < 0) {
            transform.translate(0, scaledImage.getIconHeight());
        }

        // Apply the transformation
        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        BufferedImage reflectedImage = op.filter(unflippedImage, null);

        return new ImageIcon(reflectedImage);
    }

    @Override
    public JComponent renderComponent(GameObject attached, Scene scene) {
        Tuple<Double, Double> renderPosition = attached.getTransform().getPosition(false);

        int trueScaleX = (int)(attached.getTransform().getScaleX() * image.getIconWidth());
        int trueScaleY = (int)(attached.getTransform().getScaleY() * image.getIconHeight());
        float rotation = attached.getTransform().getRotation();

        setPosition(renderPosition.getFirst() - (Math.abs(trueScaleX)  / 2),
                renderPosition.getSecond() - (Math.abs(trueScaleY)  / 2));

        if (getWidth() != trueScaleX || getHeight() != trueScaleY || spriteRotation != rotation) {
            setWidth(trueScaleX);
            setHeight(trueScaleY);
            spriteRotation = rotation;

            container.setIcon(getScaledImage(trueScaleX, trueScaleY));
        }

        return super.renderComponent(attached, scene);
    }
}