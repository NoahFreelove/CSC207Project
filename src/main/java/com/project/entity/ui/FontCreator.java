package com.project.entity.ui;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class FontCreator {

    public static Font createFont(float size) {
        Font font = null;
        try {
            InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("WestEnd.ttf");
            font = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(size);
        } catch (IOException | FontFormatException e) {
            //Handle exception
            e.printStackTrace();
        }
        return font;
    }
}
