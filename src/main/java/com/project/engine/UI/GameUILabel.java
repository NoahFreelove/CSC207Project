package com.project.engine.UI;

import com.project.engine.IO.ImageLoader;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameUILabel extends JLabel implements GameUI{
    private String imagePath = "";

    public GameUILabel() {
        super();
        setOpaque(false);
    }

    public GameUILabel(String text, int x, int y, int width, int height) {
        super(text);
        this.setBounds(x, y, width, height);
        setOpaque(false);
    }

    public void setImage(String path) {
        BufferedImage i = ImageLoader.loadImage(path);
        if (i != null) {
            setIcon(new ImageIcon(i));
        }
        imagePath = path;
    }


    public void setFontSize(int size) {
        Font f = getFont();
        setFont(new Font(f.getFontName(), f.getStyle(), size));
    }

    @Override
    public void deserialize(JSONObject data) {
        int x = data.getInt("x");
        int y = data.getInt("y");
        int width = data.getInt("width");
        int height = data.getInt("height");
        this.setBounds(x, y, width, height);
        this.setText(data.getString("text"));
        String color = data.getString("foreground_color_hex");
        this.setForeground(Color.decode(color));

        color = data.getString("background_color_hex");
        this.setBackground(Color.decode(color));
        String imagePath = data.getString("image_path");
        if (!imagePath.isEmpty()) {
            setImage(imagePath);
        }
        String fontName = data.getString("font");
        int fontSize = data.getInt("font_size");
        this.setFont(new Font(fontName, Font.PLAIN, fontSize));
        this.setHorizontalAlignment(data.getInt("alignment"));

    }

    @Override
    public JSONObject serialize() {
        JSONObject obj = new JSONObject();
        obj.put("class", getClass().getCanonicalName());
        obj.put("x", getX());
        obj.put("y", getY());
        obj.put("width", getWidth());
        obj.put("height", getHeight());
        obj.put("text", getText());
        String hexColor = "#";
        Color color = getForeground();
        hexColor += Integer.toHexString(color.getRed());
        hexColor += Integer.toHexString(color.getGreen());
        hexColor += Integer.toHexString(color.getBlue());
        hexColor += Integer.toHexString(color.getAlpha());
        obj.put("foreground_color_hex", hexColor);

        hexColor = "#";
        color = getBackground();
        hexColor += Integer.toHexString(color.getRed());
        hexColor += Integer.toHexString(color.getGreen());
        hexColor += Integer.toHexString(color.getBlue());
        hexColor += Integer.toHexString(color.getAlpha());
        obj.put("background_color_hex", hexColor);

        obj.put("image_path", imagePath);

        obj.put("font", getFont().getFontName());
        obj.put("font_size", getFont().getSize());
        obj.put("alignment", getHorizontalAlignment());


        return obj;
    }
}
