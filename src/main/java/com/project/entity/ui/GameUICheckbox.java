package com.project.entity.ui;

import com.project.external_interfaces.ImageLoader;

import javax.swing.*;
import java.awt.*;

public class GameUICheckbox extends JCheckBox implements GameUIObject {
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

    public void onChecked() {
        onClickEvent.onCheck(isSelected());
    }

    @Override
    public JComponent getComponent() {
        return null;
    }
}