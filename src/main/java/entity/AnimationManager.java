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
        this.currentAnimation = null;
        this.x = startX;
        this.y = startY;
    }

    public void addAnimation(String name, AnimationState animation) {
        animations.put(name, animation);
    }

    private void startAnimation(String name) {
        if (!animations.containsKey(name)) {
            throw new IllegalArgumentException("Animation " + name + " does not exist.");
        }


        currentAnimation = animations.get(name);
        currentAnimationName = name;
        isAnimating = true;
        currentAnimation.onStart(renderer, x, y);  // Pass coordinates to onStart
    }

    private void update() {
        if (isAnimating && currentAnimation != null) {
            currentAnimation.onContinue(renderer, x, y);  // Pass coordinates to onContinue
        }
    }

    private void endAnimation() {
        System.out.println(isAnimating);
        System.out.println(currentAnimation);

        if (isAnimating && currentAnimation != null) {
            System.out.println("EndAnimation");
            currentAnimation.onEnd(renderer, x, y);  // Pass coordinates to onEnd
            isAnimating = false;
            currentAnimation = null;
            currentAnimationName = null;
        }
    }

    // Method to update position if needed, kind useless but can get rid of if you guys want
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
                    System.out.println("running walking animation");
                }
            }, 0, 1000 / 30);
        }
    }

    public void stopMoving() {
        System.out.println("not working");
        endAnimation();  // End the current animation and set to idle

        // Cancel the timer to stop animation updates
        if (animationTimer != null) {
            animationTimer.cancel();
            animationTimer = null;
        }
    }
}
