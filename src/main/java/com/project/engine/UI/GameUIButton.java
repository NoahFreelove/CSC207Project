package com.project.engine.UI;

import com.project.engine.IO.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameUIButton extends JButton implements GameUI {
    private String imagePath = "";
    public IOnClick onClickEvent = () -> {};
    public GameUIButton(){
        super();
        setOpaque(true);
        addActionListener(e -> onClick());
    }

    public GameUIButton(String text, int x, int y, int width, int height){
        super(text);
        this.setBounds(x, y, width, height);
        setOpaque(true);
        // set on click listener
        addActionListener(e -> onClick());
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
        imagePath = path;
    }

    public void onClick() {
        onClickEvent.onClick();
    }


}
