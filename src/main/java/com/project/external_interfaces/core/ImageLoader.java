package com.project.external_interfaces.core;

import com.project.entity.core.Tuple;
import com.project.interface_adapters.core.display.ResourceManager;

import java.awt.image.BufferedImage;
import java.net.URL;

public class ImageLoader {

    public static BufferedImage loadImage(String relativeResourcePath) {
        return loadImage(relativeResourcePath, -1, -1);
    }

    /**
     * This method is somewhat complicated,
     * If the filepath has been loaded already:
     * It will return a clone of the cached image at the desired <width> and <height>. It achieves this by doing a
     * scale operation. Is this expensive? Yes, but its only done once and is still far faster than
     * loading the image from disk and then transforming it.
     *
     * If the image has not been loaded:
     * It will load the image from the resource path and then cache it. If the filepath is invalid, it will
     * return the null texture. This is not null in the literal sense, but a checkerboard pattern that
     * is 64x64 pixels (scaled to width, height).
     *
     * Note: Every path this function takes creates a CLONE, that means you can do whatever you
     * like to the buffered image (i.e tint, modify pixels) and it will not affect the cached image.
     *
     * Note: Width and height are only used if they are greater than 0. If they are less than 0, the image
     * will not be scaled. This means the output width and height are that of the original image.
     *
     * @param relativeResourcePath The path to the image resource
     * @param width The desired width of the image (if less than 0, the image will not be scaled)
     * @param height The desired height of the image (if less than 0, the image will not be scaled)
     * @return The image at the desired width and height
     */
    public static BufferedImage loadImage(String relativeResourcePath, int width, int height){
        Tuple<BufferedImage, Boolean> result = ResourceManager.FetchImage(relativeResourcePath);
        if (result.getSecond()) {
            BufferedImage out = result.getFirst();

            // copy the image to avoid modifying the original
            BufferedImage newImage = new BufferedImage(out.getWidth(), out.getHeight(), BufferedImage.TYPE_INT_ARGB);
            newImage.getGraphics().drawImage(out, 0, 0, null);

            // scale image if necessary
            if (width > 0 && height > 0) {
                BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                scaledImage.getGraphics().drawImage(newImage, 0, 0, width, height, null);
                newImage = scaledImage;
            }
            return newImage;
        }

        BufferedImage newImage = null;
        BufferedImage registerImage = null;

        // get absolute path to the resource
        URL url = ImageLoader.class.getClassLoader().getResource(relativeResourcePath);
        if (url == null) {
            System.err.println("IO Exception: Resource not found: " + relativeResourcePath +
                    "\nResolution: Using Null Texture");

            BufferedImage cachedError = result.getFirst();
            newImage = new BufferedImage(cachedError.getWidth(), cachedError.getHeight(), BufferedImage.TYPE_INT_ARGB);
            newImage.getGraphics().drawImage(cachedError, 0, 0, null);
            registerImage = cachedError;
        }
        else {
            try {
                registerImage = javax.imageio.ImageIO.read(url);
                newImage = new BufferedImage(registerImage.getWidth(), registerImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
                newImage.getGraphics().drawImage(registerImage, 0, 0, null);

            } catch (Exception e) {
                System.err.println("IO Exception: Failed to load image: " + relativeResourcePath +
                        "\nResolution: Using Null Texture");

                BufferedImage cachedError = result.getFirst();
                newImage = new BufferedImage(cachedError.getWidth(), cachedError.getHeight(), BufferedImage.TYPE_INT_ARGB);
                newImage.getGraphics().drawImage(cachedError, 0, 0, null);
                registerImage = cachedError;
            }
        }

        // scale to the desired size if necessary
        // NOTE: We don't scale registerImage because we want to keep the original image
        if (width > 0 && height > 0) {
            BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            scaledImage.getGraphics().drawImage(newImage, 0, 0, width, height, null);
            newImage = scaledImage;
        }

        // dont register if its just the error texture
        if (result.getSecond()) {
            ResourceManager.RegisterImage(relativeResourcePath, registerImage);
        }
        return newImage;
    }
}