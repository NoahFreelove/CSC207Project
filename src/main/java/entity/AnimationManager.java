package entity;

import com.project.engine.Rendering.SpriteRenderer;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class AnimationManager {
    private Map<String, AnimationState> animations = new HashMap<>();
    private AnimationState currentAnimation;
    private String currentAnimationName;
    private boolean isAnimating;
    private SpriteRenderer renderer;
    private Timer animationTimer;// Reference to the renderer
    private double x, y;  // Position coordinates

    public AnimationManager(SpriteRenderer renderer, double startX, double startY) {
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

    public void startMoving(String animationName) {
        if (!isAnimating) {
            isAnimating = true;
            startAnimation(animationName);

            // Start a timer to update the animation manager periodically (e.g., ~30 FPS)
            animationTimer = new Timer();
            animationTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    update();
                }
            }, 0, 1000 / 30);
        }
    }

    public void stopMoving() {
        if (isAnimating) {
            isAnimating = false;
            endAnimation();

            // Cancel the timer to stop the animation updates
            if (animationTimer != null) {
                animationTimer.cancel();
                animationTimer = null;
            }
        }
    }
}