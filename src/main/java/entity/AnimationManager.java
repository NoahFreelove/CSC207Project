package entity;

import com.project.engine.Rendering.SpriteRenderer;
import java.util.HashMap;
import java.util.Map;

public class AnimationManager {
    private Map<String, AnimationState> animations = new HashMap<>();
    private AnimationState currentAnimation;
    private String currentAnimationName;
    private boolean isAnimating;
    private SpriteRenderer renderer;  // Reference to the renderer
    private int x, y;  // Position coordinates

    public AnimationManager(SpriteRenderer renderer, int startX, int startY) {
        this.renderer = renderer;
        this.isAnimating = false;
        this.x = startX;
        this.y = startY;
    }

    public void addAnimation(String name, AnimationState animation) {
        animations.put(name, animation);
    }

    public void startAnimation(String name) {
        if (!animations.containsKey(name)) {
            throw new IllegalArgumentException("Animation " + name + " does not exist.");
        }

        if (isAnimating && currentAnimation != null) {
            currentAnimation.onEnd(renderer, x, y);
        }

        currentAnimation = animations.get(name);
        currentAnimationName = name;
        isAnimating = true;
        currentAnimation.onStart(renderer, x, y);  // Pass coordinates to onStart
    }

    public void update() {
        if (isAnimating && currentAnimation != null) {
            currentAnimation.onContinue(renderer, x, y);  // Pass coordinates to onContinue
        }
    }

    public void endAnimation() {
        if (isAnimating && currentAnimation != null) {
            currentAnimation.onEnd(renderer, x, y);  // Pass coordinates to onEnd
            isAnimating = false;
            currentAnimation = null;
            currentAnimationName = null;
        }
    }

    // Method to update position if needed
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
