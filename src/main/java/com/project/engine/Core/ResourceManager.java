package com.project.engine.Core;

import org.jetbrains.annotations.Nullable;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class ResourceManager {
    private static final HashMap<String, BufferedImage> loadedImages = new HashMap<>();
    private static final BufferedImage nullTexture = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);

    // static blocks are so cool it's a shame they're not taught in most courses...
    // Or perhaps they are, but I don't pay attention!
    static {
        // Create black and pink 64x64 buffered texture for when a texture is not found
        for (int x = 0; x < 64; x++) {
            for (int y = 0; y < 64; y++) {
               // we want 8x8 squares, so we do some funky math to get that
                if ((x / 8) % 2 == (y / 8) % 2) {
                    nullTexture.setRGB(x, y, 0xFF000000);
                } else {
                    nullTexture.setRGB(x, y, 0xFFFF00FF);
                }
            }
        }
        loadedImages.put("null_texture", nullTexture);
    }

    public static BufferedImage RegisterImage(String relativePath, BufferedImage i) {
        return RegisterImage(relativePath, i, true);
    }
    public static BufferedImage RegisterImage(String relativePath, BufferedImage i, boolean overwrite) {
        final BufferedImage result = FetchImage(relativePath).getFirst();
        if (result != null && !overwrite) {
            return result;
        }
        loadedImages.put(relativePath, i);
        return i;
    }

    public static Tuple<BufferedImage, Boolean> FetchImage(String relativePath) {
        if (relativePath == null) {
            return new Tuple<>(nullTexture, false);
        }

        final BufferedImage result = loadedImages.get(relativePath);
        if (result == null) {
            return new Tuple<>(nullTexture, false);
        }
        return new Tuple<>(result, true);
    }
}
