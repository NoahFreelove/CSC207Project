package com.project.engine.Rendering;

import com.project.engine.Core.GameObject;
import com.project.engine.Core.Scene;
import com.project.engine.IO.ImageLoader;

import javax.swing.*;

public class SpriteRenderer extends RenderBase {
    private final ImageIcon image;
    private final JLabel container;
    public SpriteRenderer(String spritePath){
        super();
        image = ImageLoader.loadImage(spritePath);
        setWidth(image.getIconWidth());
        setHeight(image.getIconHeight());
        container = new JLabel(image);

        setBaseComponent(container);
    }

    @Override
    public JComponent renderComponent(GameObject attached, Scene scene) {
        setPosition(attached.getXPosition(), attached.getYPosition());
        return super.renderComponent(attached, scene);
    }
}
