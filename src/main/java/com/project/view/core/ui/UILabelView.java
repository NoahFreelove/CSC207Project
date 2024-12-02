package com.project.view.core.ui;

import com.project.external_interfaces.core.ui.FontCreator;
import com.project.external_interfaces.core.ImageLoader;
import com.project.interface_adapters.core.display.ui.label.LabelViewManager;
import com.project.interface_adapters.core.editor.EditorTileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class UILabelView extends JLabel implements LabelViewManager {
    private static float TITLE_SIZE = 80f;

    public UILabelView() {
        super();
        setOpaque(false);
        setFont(FontCreator.createFont(TITLE_SIZE));
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    public UILabelView(String text) {
        super(text);
        setOpaque(false);
        setFont(FontCreator.createFont(TITLE_SIZE));
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    public UILabelView(String text, int x, int y, int width, int height) {
        super(text);
        this.setBounds(x, y, width, height);
        setOpaque(false);
        setFont(FontCreator.createFont(TITLE_SIZE));
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    public void setEditorIcon(int index) {
        this.setIcon(new ImageIcon(ImageLoader.loadImage(EditorTileManager.spriteCache.get(index), EditorTileManager.BASE_TILE_SIZE, EditorTileManager.BASE_TILE_SIZE)));
    }

    public void setImage(String path) {
        BufferedImage i = ImageLoader.loadImage(path);
        if (i != null) {
            setIcon(new ImageIcon(i));
        }
    }

    public void setText(String text) {
        super.setText(text);
    }


    public void setFancyFontSize(int size) {
        this.setFont(FontCreator.createFont(size));
    }
    public void setFontSize(int size) {
        Font f = getFont();
        setFont(new Font(f.getFontName(), f.getStyle(), size));
    }
}
