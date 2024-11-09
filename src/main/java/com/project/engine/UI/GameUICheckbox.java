package com.project.engine.UI;

import com.project.engine.IO.ImageLoader;

import javax.swing.*;
import java.awt.*;

public class GameUICheckbox extends JCheckBox implements GameUI {
    public IOnCheck onClickEvent = (checked) -> {};

    public GameUICheckbox() {
        super();
        setOpaque(false);
        addActionListener(e -> onChecked());
        setIcons();
    }

    private void setIcons() {
        setIcon(new ImageIcon(ImageLoader.loadImage("ui/checkboxUnchecked.jpg", 32, 32)));
        setSelectedIcon(new ImageIcon(ImageLoader.loadImage("ui/checkboxChecked.jpg", 32, 32)));
    }

    public GameUICheckbox(String text) {
        super(text);
        setOpaque(false);
        addActionListener(e -> onChecked());
        setIcons();
    }

    public GameUICheckbox(String text, int x, int y, int width, int height) {
        super(text);
        this.setBounds(x, y, width, height);
        setOpaque(false);
        addActionListener(e -> onChecked());
        setIcons();
    }

    public void setFontSize(int size) {
        Font f = getFont();
        setFont(new Font(f.getFontName(), f.getStyle(), size));
    }

    public void onChecked() {
        onClickEvent.onCheck(isSelected());
    }
}