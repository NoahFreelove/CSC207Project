package com.project.engine.Animation;

import com.project.engine.Rendering.SpriteRenderer;

import java.util.HashMap;
import java.util.Map;

public class SpriteAnimatorEngine {
    private Map<String, String[]> animations = new HashMap<>();
    private String currentAnimation;
    private int currentFrame;
    private double accumulatedTime;

    public SpriteAnimatorEngine() {
        this.accumulatedTime = 0;
    }

    // Add animation by its name and frames (file paths)
    public void addAnimation(String name, String[] frames) {
        animations.put(name, frames);
    }

    // Start a specific animation by its name
    public void startAnimation(String name) {
        if (!animations.containsKey(name)) {
            throw new IllegalArgumentException("Animation " + name + " does not exist.");
        }
        currentAnimation = name;
        currentFrame = 0;
        accumulatedTime = 0;
    }

    // Update current sprite in the renderer based on elapsed time
    public void updateSprite(SpriteRenderer spriteRenderer, double deltaTime) {
        if (currentAnimation == null || !animations.containsKey(currentAnimation)) {
            return;
        }

        String[] frames = animations.get(currentAnimation);
        double frameTime = 1.0 / frames.length; // assuming animation should complete in 1 second

        accumulatedTime += deltaTime;
        while (accumulatedTime >= frameTime) {
            accumulatedTime -= frameTime;
            currentFrame = (currentFrame + 1) % frames.length;
        }

        spriteRenderer.setImage(frames[currentFrame], 20, 20);
    }
}