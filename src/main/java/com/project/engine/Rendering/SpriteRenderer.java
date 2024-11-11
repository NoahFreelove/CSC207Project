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
    private String imagePath = "";

    private boolean independentOfCamera = false;
    private boolean tile = false;
    private double tileX = 1;
    private double tileY = 1;

    private boolean enabled = true;

    public SpriteRenderer() {}

    public SpriteRenderer(String spritePath) {
        this(spritePath, -1, -1);
    }

    public SpriteRenderer(String spritePath, int width, int height) {
        super();
        setImage(spritePath, width, height);
    }

    public synchronized void setImage(String spritePath, int width, int height) {
        image = ImageLoader.loadImage(spritePath, width, height);
        this.imagePath = spritePath;
        scaledImageCache.clear();
    }

    public Tuple<Integer, Integer> getImageSize() {
        return new Tuple<>(image.getWidth(), image.getHeight());
    }

    private BufferedImage getTransformedImage(int width, int height, float rotation) {
        String key = width + "x" + height + "r" + rotation;
        if (scaledImageCache.containsKey(key)) {
            return scaledImageCache.get(key);
        }

        BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = scaledImage.createGraphics();
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.dispose();

        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(rotation), width / 2.0, height / 2.0);

        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
        BufferedImage rotatedImage = op.filter(scaledImage, null);

        scaledImageCache.put(key, rotatedImage);
        return rotatedImage;
    }

    @Override
    public void render(GameObject attached, Scene scene, Graphics2D g2d) {
        if (image == null || !enabled)
            return;

        Tuple<Double, Double> renderPosition = attached.getTransform().getPosition();
        Camera camera = scene.getCamera();

        double scaleX = attached.getTransform().getScaleX();
        double scaleY = attached.getTransform().getScaleY();
        float rotation = attached.getTransform().getRotation();

        /*
            Why do this check?
            When we tile we don't want to scale our image because that was the whole purpose of tiling the image.
            getWidth() and getHeight() return getDimensions() * scale(). But we don't want scale if we're tiling.
         */
        int imageWidth = 0;
        int imageHeight = 0;
        if (tile){
            Tuple<Integer, Integer> dim = attached.getTransform().getDimensions();
            imageWidth = dim.getFirst();
            imageHeight = dim.getSecond();
        }
        else {
            imageWidth = attached.getTransform().getWidth();
            imageHeight = attached.getTransform().getHeight();
        }

        int finalX = renderPosition.getFirst().intValue();
        int finalY = renderPosition.getSecond().intValue();

        if (!independentOfCamera) {
            finalX = (int) ((renderPosition.getFirst() - camera.getCameraX()));
            finalY = (int) ((renderPosition.getSecond() - camera.getCameraY()));
        }

        if (tile) {
            for (int i = 0; i < tileX; i++) {
                for (int j = 0; j < tileY; j++) {
                    AffineTransform at = new AffineTransform();
                    at.translate(finalX + i * imageWidth, finalY + j * imageHeight);
                    at.scale(Math.min(1, tileX - i), Math.min(1, tileY - j));
                    at.rotate(Math.toRadians(rotation), imageWidth / 2.0, imageHeight / 2.0);
                    g2d.drawImage(image, at, null);
                }
            }
        } else {
            BufferedImage transformedImage = getTransformedImage(Math.abs(imageWidth), Math.abs(imageHeight), rotation);
            AffineTransform at = new AffineTransform();
            at.scale(Math.signum(scaleX), Math.signum(scaleY));
            at.translate(Math.signum(scaleX) * finalX + Boolean.compare(scaleX < 0, false) * imageWidth,
                    Math.signum(scaleY) * finalY + Boolean.compare(scaleY < 0, false) * imageHeight);
            g2d.drawImage(transformedImage, at, null);
        }
    }

    @Override
    public void deserialize(JSONObject data) {
        setImage(data.getString("spritePath"), data.getInt("width"), data.getInt("height"));
        tile = data.optBoolean("tile", false);
        tileX = data.optInt("tileX", 1);
        tileY = data.optInt("tileY", 1);
    }

    @Override
    public JSONObject serialize() {
        JSONObject output = new JSONObject();
        output.put("spritePath", imagePath);
        output.put("tile", tile);
        output.put("tileX", tileX);
        output.put("tileY", tileY);
        return output;
    }

    public boolean isIndependentOfCamera() {
        return independentOfCamera;
    }

    public void setIndependentOfCamera(boolean independentOfCamera) {
        this.independentOfCamera = independentOfCamera;
    }

    public boolean isTile() {
        return tile;
    }

    public void setTile(boolean tile) {
        this.tile = tile;
    }

    public double getTileX() {
        return tileX;
    }

    public void setTileX(double tileX) {
        this.tileX = tileX;
    }

    public double getTileY() {
        return tileY;
    }

    public void setTileY(double tileY) {
        this.tileY = tileY;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}