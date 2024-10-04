package com.project.engine.IO;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;

public class ImageLoader {
    public static ImageIcon loadImage(String relativeResourcePath){
        // get absolute path to the resource
        URL url = ImageLoader.class.getClassLoader().getResource(relativeResourcePath);
        if (url == null) {
            throw new RuntimeException(new IOException("Resource not found: " + relativeResourcePath));
        }
        return new ImageIcon(url.getPath());
    }
}
