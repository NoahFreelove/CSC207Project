package com.project.view.core.ui;

import com.project.external_interfaces.core.ui.FontCreator;
import com.project.external_interfaces.core.ImageLoader;
import com.project.interface_adapters.core.display.ui.button.ButtonController;
import com.project.interface_adapters.core.display.ui.button.ButtonViewManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class UIButtonView extends JButton implements ButtonViewManager {
    ButtonController controller;

    private static float BUTTON_SIZE = 40f;

    public UIButtonView(ButtonController controller) {
        super();
        setOpaque(true);
        this.controller = controller;
        setFont(FontCreator.createFont(BUTTON_SIZE));
        setHorizontalAlignment(SwingConstants.CENTER);

        // set on click listener
        addActionListener(e -> onClick());
    }

    public UIButtonView(ButtonController controller, String text, int x, int y, int width, int height){
        super(text);
        this.setBounds(x, y, width, height);
        setOpaque(true);
        this.controller = controller;
        setFont(FontCreator.createFont(BUTTON_SIZE));
        setHorizontalAlignment(SwingConstants.CENTER);

        // set on click listener
        addActionListener(e -> onClick());
    }

    public void setTransparent(boolean transparent){
        setOpaque(!transparent);
        setContentAreaFilled(!transparent);
        setBorderPainted(!transparent);
    }

    public void setFontSize(int size) {
        Font f = getFont();
        setFont(new Font(f.getFontName(), f.getStyle(), size));
    }

    public void setImage(String path) {
        BufferedImage i = ImageLoader.loadImage(path);
        if (i != null) {
            setIcon(new ImageIcon(i));
        }
    }

    public void setImage(String path, int width, int height) {
        BufferedImage i = ImageLoader.loadImage(path);
        if (i != null) {
            setIcon(new ImageIcon(i.getScaledInstance(width, height, BufferedImage.TYPE_INT_ARGB)));
        }
    }

    public String getText() {
        return super.getText();
    }
    public void onClick() {
        controller.callback();
    }
}
