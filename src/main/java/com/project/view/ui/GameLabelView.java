package com.project.view.ui;

import com.project.external_interfaces.FontCreator;
import com.project.external_interfaces.ImageLoader;
import com.project.interface_adapters.ui.label.LabelViewManager;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static com.project.use_cases.prebuilts.game_ui.UIConstants.TITLE_SIZE;

public class GameLabelView extends JLabel implements LabelViewManager {
    public GameLabelView() {
        super();
        setOpaque(false);
        setFont(FontCreator.createFont(TITLE_SIZE));
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    public GameLabelView(String text) {
        super(text);
        setOpaque(false);
        setFont(FontCreator.createFont(TITLE_SIZE));
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    public GameLabelView(String text, int x, int y, int width, int height) {
        super(text);
        this.setBounds(x, y, width, height);
        setOpaque(false);
        setFont(FontCreator.createFont(TITLE_SIZE));
        setHorizontalAlignment(SwingConstants.CENTER);
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


    public void setFontSize(int size) {
        Font f = getFont();
        setFont(new Font(f.getFontName(), f.getStyle(), size));
    }
}
