package com.project.engine.Rendering;

import com.project.engine.Core.Engine;
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
    private Tuple<Integer, Integer> screenSize = new Tuple<>(1, 1);

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

        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        BufferedImage rotatedImage = op.filter(scaledImage, null);

        scaledImageCache.put(key, rotatedImage);
        return rotatedImage;
    }

    @Override
    public void render(GameObject attached, Scene scene, Graphics2D g2d) {
        if (image == null || !enabled)
            return;

        if (!inCameraView(attached, scene))
            return;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);


        Tuple<Double, Double> renderPosition = attached.getTransform().getPosition();
        Camera camera = scene.getCamera();

        double scaleX = attached.getTransform().getScaleX();
        double scaleY = attached.getTransform().getScaleY();
        float rotation = attached.getTransform().getRotation();

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

        double finalX = renderPosition.getFirst();
        double finalY = renderPosition.getSecond();

        if (!independentOfCamera) {
            finalX = renderPosition.getFirst() - camera.getCameraX();
            finalY = renderPosition.getSecond() - camera.getCameraY();
        }

        finalX = Math.round(finalX);
        finalY = Math.round(finalY);

        if (tile) {
            int maxI = (int) Math.ceil(tileX);
            int maxJ = (int) Math.ceil(tileY);
            for (int i = 0; i < maxI; i++) {
                for (int j = 0; j < maxJ; j++) {
                    double scaleXFactor = (i == maxI - 1) ? (tileX - (maxI - 1)) : 1.0;
                    double scaleYFactor = (j == maxJ - 1) ? (tileY - (maxJ - 1)) : 1.0;
                    AffineTransform at = new AffineTransform();
                    at.translate(finalX + i * imageWidth, finalY + j * imageHeight);
                    at.scale(scaleXFactor, scaleYFactor);
                    at.rotate(Math.toRadians(rotation), imageWidth / 2.0, imageHeight / 2.0);
                    g2d.drawImage(image, at, null);
                }
            }
        }
        else {
            BufferedImage transformedImage = getTransformedImage(Math.abs(imageWidth), Math.abs(imageHeight), rotation);
            AffineTransform at = new AffineTransform();
            at.scale(Math.signum(scaleX), Math.signum(scaleY));
            at.translate(Math.signum(scaleX) * finalX + Boolean.compare(scaleX < 0, false) * imageWidth,
                    Math.signum(scaleY) * finalY + Boolean.compare(scaleY < 0, false) * imageHeight);
            g2d.drawImage(transformedImage, at, null);
        }

    }

    private boolean inCameraView(GameObject parent, Scene linked) {
        screenSize = linked.getRealScreenSize(Engine.getInstance().getPrimaryWindow());

        Camera c = linked.getCamera();
        Tuple<Double, Double> pos = parent.getTransform().getPosition(true);
        Tuple<Integer, Integer> dim = parent.getTransform().getDimensions();
        return pos.getFirst() + (image.getWidth() * dim.getFirst()) > c.getCameraX() && pos.getFirst() < c.getCameraX() + screenSize.getFirst() &&
                pos.getSecond() + (image.getHeight() * dim.getSecond()) > c.getCameraY() && pos.getSecond() < c.getCameraY() + screenSize.getSecond();
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