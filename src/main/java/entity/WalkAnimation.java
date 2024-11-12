package entity;

import com.project.engine.Rendering.SpriteRenderer;

public class WalkAnimation implements AnimationState {
    private static final String[] frames = {"assets/character.png", "assets/character_right_02.png"};
    private int frameIndex = 0;
    private int frameDelay = 20;
    private int frameCounter = 0;

    @Override
    public void onStart(SpriteRenderer renderer, int x, int y) {
        System.out.println("Starting walk animation.");
        frameIndex = 0;
        frameCounter = 0;

        // Set the initial image and position
        renderer.setImage(frames[frameIndex], x, y);
    }

    @Override
    public void onContinue(SpriteRenderer renderer, int x, int y) {
        frameCounter++;
        if (frameCounter >= frameDelay) {
            frameIndex = (frameIndex + 1) % frames.length;  // Toggle between frames
            frameCounter = 0;

            // Update the sprite image and position
            renderer.setImage(frames[frameIndex], x, y);
        }
    }

    @Override
    public void onEnd(SpriteRenderer renderer, int x, int y) {
        System.out.println("Ending walk animation.");
        // Optionally reset or set to an idle image
    }
}
