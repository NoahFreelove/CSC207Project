package com.project.engine.Rendering;

import com.project.engine.Core.GameObject;
import com.project.engine.Core.Scene;
import com.project.engine.Core.Tuple;
import com.project.engine.IO.ImageLoader;
import org.json.JSONObject;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class SpriteRenderer extends RenderBase {
    private BufferedImage image = null;
    private final Map<String, BufferedImage> scaledImageCache = new HashMap<>();
    private int width;
    private int height;
    private String imagePath = "";

    public SpriteRenderer() {

    }

    public SpriteRenderer(String spritePath) {
        this(spritePath, -1, -1);
    }

    public SpriteRenderer(String spritePath, int width, int height) {
        super();
        setImage(spritePath, width, height);
    }

    public synchronized void setImage(String spritePath, int width, int height) {
        image = ImageLoader.loadImage(spritePath, width, height);
        this.width = width;
        this.height = height;
        this.imagePath = spritePath;
        scaledImageCache.clear();
    }

    private BufferedImage getTransformedImage(int width, int height, float rotation) {
        String key = width + "x" + height + "r" + rotation;
        if (scaledImageCache.containsKey(key)) {
            return scaledImageCache.get(key);
        }

        // Scale the image to the desired dimensions
        BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = scaledImage.createGraphics();
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.dispose();

        // Rotate the scaled image
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(rotation), width / 2.0, height / 2.0);

        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
        BufferedImage rotatedImage = op.filter(scaledImage, null);

        scaledImageCache.put(key, rotatedImage);
        return rotatedImage;
    }

    @Override
    public void render(GameObject attached, Scene scene, Graphics2D g2d) {
        if (image == null)
            return;

        Tuple<Double, Double> renderPosition = attached.getTransform().getPosition();
        Camera camera = scene.getCamera();

        double scaleX = attached.getTransform().getScaleX();
        double scaleY = attached.getTransform().getScaleY();
        float rotation = attached.getTransform().getRotation();

        // Use absolute values for dimensions
        int imageWidth = (int) (Math.abs(scaleX) * image.getWidth());
        int imageHeight = (int) (Math.abs(scaleY) * image.getHeight());

        int finalX = (int) ((renderPosition.getFirst() - camera.getCameraX() + camera.getOffsetX()));
        int finalY = (int) ((renderPosition.getSecond() - camera.getCameraY() + camera.getOffsetY()));

        // Get the transformed image (scaled and rotated)
        BufferedImage transformedImage = getTransformedImage(imageWidth, imageHeight, rotation);

        AffineTransform at = new AffineTransform();
        at.translate(finalX, finalY);

        // Apply flipping if scaleX or scaleY is negative
        if (scaleX < 0 || scaleY < 0) {
            at.translate(imageWidth / 2.0, imageHeight / 2.0);
            at.scale(scaleX < 0 ? -1 : 1, scaleY < 0 ? -1 : 1);
            at.translate(-imageWidth / 2.0, -imageHeight / 2.0);
        }

        // Draw the image with the applied transformations
        g2d.drawImage(transformedImage, at, null);
    }

    @Override
    public void deserialize(JSONObject data) {
        setImage(data.getString("spritePath"), data.getInt("width"), data.getInt("height"));
    }

    @Override
    public JSONObject serialize() {
        JSONObject output = new JSONObject();
        output.put("spritePath", imagePath);
        output.put("width", width);
        output.put("height", height);
        return output;
    }
}
